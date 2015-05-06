package com.customized.tools.dbtester;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.customized.tools.StringUtil;
import com.customized.tools.dbtester.metdata.AbstractMetadataRecord;
import com.customized.tools.dbtester.metdata.Column;
import com.customized.tools.dbtester.metdata.ForeignKey;
import com.customized.tools.dbtester.metdata.Column.NullType;
import com.customized.tools.dbtester.metdata.KeyRecord;
import com.customized.tools.dbtester.metdata.Metadata;
import com.customized.tools.dbtester.metdata.Procedure;
import com.customized.tools.dbtester.metdata.ProcedureParameter;
import com.customized.tools.dbtester.metdata.ProcedureParameter.Type;
import com.customized.tools.dbtester.metdata.Table;


public class JDBCMetdataProcessor implements MetadataProcessor<Connection> {
	
	public final static char NAME_DELIM_CHAR = '.';
	
	static class TableInfo {
		private String catalog;
		private String schema;
		private String name;
		private Table table;
		private String type;
		
		public TableInfo(String catalog, String schema, String name, Table table) {
			this.catalog = catalog;
			this.schema = schema;
			this.name = name;
			this.table = table;
		}
	}
	
	private String[] tableTypes;
	private String tableNamePattern;
	private String catalog;
	private String schemaPattern;	
	private String procedureNamePattern;
	
	private boolean widenUnsingedTypes = true;
	protected boolean useFullSchemaName = true;	
	private boolean useQualifiedName = true;
	private boolean useCatalogName = true;
	private boolean importKeys = true;
	private boolean importForeignKeys = true;
	private boolean importIndexes = true;
	private boolean importStatistics = true;
	private boolean importProcedures = true;
	private boolean importApproximateIndexes = true;
	private boolean useAnyIndexCardinality = true;
	private boolean autoCreateUniqueConstraints = true;
	private boolean useProcedureSpecificName = true;
	
	private String columnNamePattern;
	
	private Set<String> unsignedTypes = new HashSet<String>();    
	
	private Map<Integer, String> JdbcTypeNames = null;
	
	private String quoteString;

	@Override
	public void process(Metadata dataHolder, Connection conn) throws Exception {
		
		if(JdbcTypeNames == null) {
			initJdbcTypeNamesMap();
		}
		
		DatabaseMetaData metadata = conn.getMetaData();
		
		quoteString = metadata.getIdentifierQuoteString();
		if (quoteString != null && quoteString.trim().length() == 0) {
			quoteString = null;
		}
		
		if (widenUnsingedTypes){
			ResultSet rs = null;
			try {
				rs = metadata.getTypeInfo();
			} catch (SQLException e) {
			}
			if(rs != null) {
				while(rs.next()) {
					String name = rs.getString(1);
					boolean unsigned = rs.getBoolean(10);
					if (unsigned) {
						unsignedTypes.add(name);
					}
				}
			}
		}
		
		Map<String, TableInfo> tableMap = getTables(dataHolder, metadata, conn);
		HashSet<TableInfo> tables = new LinkedHashSet<TableInfo>(tableMap.values());
		
		if (importKeys) {
			getPrimaryKeys(dataHolder, metadata, tables);
			getIndexes(dataHolder, metadata, tables, !importIndexes);
			if (importForeignKeys) {
				getForeignKeys(dataHolder, metadata, tables, tableMap);
			}
		} else if (importIndexes) {
			getIndexes(dataHolder, metadata, tables, false);
		}
		
		if (importStatistics) {
			for (TableInfo tableInfo : tables) {
				getTableStatistics(conn, tableInfo.catalog, tableInfo.schema, tableInfo.name, tableInfo.table);
			}
		}
		
		if (importProcedures) {
			getProcedures(dataHolder, metadata);
		}
		
		List<Table> tableList = new ArrayList<>();
		for(String key : tableMap.keySet()){
			Table table = tableMap.get(key).table;
			tableList.add(table);
		}
		dataHolder.setTables(tableList);
	}
	
	private void getPrimaryKeys(Object metadataFactory, DatabaseMetaData metadata, HashSet<TableInfo> tables) throws SQLException {
		for (TableInfo tableInfo : tables) {
			ResultSet pks = metadata.getPrimaryKeys(tableInfo.catalog, tableInfo.schema, tableInfo.name);
			TreeMap<Short, String> keyColumns = null;
			String pkName = null;
			while (pks.next()) {
				String columnName = pks.getString(4);
				short seqNum = pks.getShort(5);
				if (keyColumns == null) {
					keyColumns = new TreeMap<Short, String>();
				}
				keyColumns.put(seqNum, columnName);
				if (pkName == null) {
					pkName = pks.getString(6);
					if (pkName == null) {
						pkName = "PK_" + tableInfo.table.getName().toUpperCase(); 
					}
				}
			}
			if (keyColumns != null) {
				addPrimaryKey(pkName, new ArrayList<String>(keyColumns.values()), tableInfo.table);
			}
			pks.close();
		}
		
	}

	private void addPrimaryKey(String name, List<String> columnNames, Table table) {
		KeyRecord primaryKey = new KeyRecord(KeyRecord.Type.Primary);
		primaryKey.setParent(table);
		primaryKey.setColumns(new ArrayList<Column>(columnNames.size()));
		primaryKey.setName(name);
		primaryKey.setUuid(formUUID(primaryKey));
		assignColumns(columnNames, table, primaryKey);
		table.setPrimaryKey(primaryKey);
	}

	private void assignColumns(List<String> columnNames, Table table, KeyRecord primaryKey) {
		for (String columnName : columnNames) {
			assignColumn(table, primaryKey, columnName);
		}
		
	}

	private void assignColumn(Table table, KeyRecord primaryKey, String columnName) {
		Column column = table.getColumnByName(columnName);
		if(column != null) {
			primaryKey.getColumns().add(column);
		}
		
	}

	private void getForeignKeys(Object metadataFactory, DatabaseMetaData metadata, HashSet<TableInfo> tables, Map<String, TableInfo> tableMap) throws SQLException {
		for (TableInfo tableInfo : tables){
			ResultSet fks = metadata.getImportedKeys(tableInfo.catalog, tableInfo.schema, tableInfo.name);
			HashMap<String, FKInfo> allKeys = new HashMap<String, FKInfo>();
			while (fks.next()){
				String columnName = fks.getString(8);
				short seqNum = fks.getShort(9);
				String pkColumnName = fks.getString(4);
				
				String fkName = fks.getString(12);
				if (fkName == null) {
					fkName = "FK_" + tableInfo.table.getName().toUpperCase(); 
				}
				
				FKInfo fkInfo = allKeys.get(fkName);
				
				if (fkInfo == null) {
					fkInfo = new FKInfo();
					allKeys.put(fkName, fkInfo);

					String tableCatalog = fks.getString(1);
					String tableSchema = fks.getString(2);
					String tableName = fks.getString(3);
					String fullTableName = getFullyQualifiedName(tableCatalog, tableSchema, tableName);
					fkInfo.pkTable = tableMap.get(fullTableName);
					if (fkInfo.pkTable == null){
						fkInfo.valid = false;
						continue;
					}
				}
				
				if (!fkInfo.valid) {
					continue;
				}
				
				if (fkInfo.keyColumns.put(seqNum, columnName) != null) {
					fkInfo.valid = false;
				}
				
				fkInfo.referencedKeyColumns.put(seqNum, pkColumnName);
			}
			
			for (Map.Entry<String, FKInfo> entry : allKeys.entrySet()) {
				FKInfo info = entry.getValue();
				if (!info.valid) {
					continue;
				}
				
				KeyRecord record = autoCreateUniqueKeys(autoCreateUniqueConstraints, entry.getKey(), info.referencedKeyColumns, info.pkTable.table);
				ForeignKey fk = addForiegnKey(entry.getKey(), new ArrayList<String>(info.keyColumns.values()), new ArrayList<String>(info.referencedKeyColumns.values()), info.pkTable.table.getName(), tableInfo.table);
				if (record != null) {
					fk.setReferenceKey(record);
				}
			}
			
			fks.close();
		}
	}
	
	private ForeignKey addForiegnKey(String name, List<String> columnNames, List<String> referencedColumnNames, String referenceTable, Table table) {
		ForeignKey foreignKey = new ForeignKey();
		foreignKey.setParent(table);
		foreignKey.setColumns(new ArrayList<Column>(columnNames.size()));
		assignColumns(columnNames, table, foreignKey);
		foreignKey.setReferenceTableName(referenceTable);
		foreignKey.setReferenceColumns(referencedColumnNames);
		foreignKey.setName(name);
		foreignKey.setUuid(this.formUUID(foreignKey));
		table.getForiegnKeys().add(foreignKey);
		return foreignKey;
	}

	private KeyRecord autoCreateUniqueKeys(boolean create, String name, TreeMap<Short, String> referencedKeyColumns, Table pkTable) {
		if (referencedKeyColumns != null && pkTable.getPrimaryKey() == null && pkTable.getUniqueKeys().isEmpty()) {
			addIndex(name + "_unique", false, new ArrayList<String>(referencedKeyColumns.values()), pkTable); 
		}
		
		KeyRecord uniqueKey = null;
		if (referencedKeyColumns == null) {
			uniqueKey = pkTable.getPrimaryKey();
		} else {
			for (KeyRecord record : pkTable.getUniqueKeys()) {
				if (keyMatches(new ArrayList<String>(referencedKeyColumns.values()), record)) {
					uniqueKey = record;
					break;
				}
			}
			if (uniqueKey == null && pkTable.getPrimaryKey() != null && keyMatches(new ArrayList<String>(referencedKeyColumns.values()), pkTable.getPrimaryKey())) {
				uniqueKey = pkTable.getPrimaryKey();
			}
		}
		if (uniqueKey == null && create) {
			uniqueKey = addIndex(name + "_unique", false, new ArrayList<String>(referencedKeyColumns.values()), pkTable); 
		}
		return uniqueKey;
	}
	
	private boolean keyMatches(List<String> names, KeyRecord record) {
		if (names.size() != record.getColumns().size()) {
			return false;
		}
		for (int i = 0; i < names.size(); i++) {
			if (!names.get(i).equals(record.getColumns().get(i).getName())) {
				return false;
			}
		}
		return true;
	}

	private void getIndexes(Object metadataFactory, DatabaseMetaData metadata, HashSet<TableInfo> tables, boolean uniqueOnly) throws SQLException {
		for (TableInfo tableInfo : tables) {
			
			if (!getIndexInfoForTable(tableInfo.catalog, tableInfo.schema, tableInfo.name, uniqueOnly, importApproximateIndexes, tableInfo.type)) {
				continue;
			}
			
			ResultSet indexInfo = metadata.getIndexInfo(tableInfo.catalog, tableInfo.schema, tableInfo.name, uniqueOnly, importApproximateIndexes);
			TreeMap<Short, String> indexColumns = null;
			String indexName = null;
			short savedOrdinalPosition = Short.MAX_VALUE;
			boolean nonUnique = false;
			boolean valid = true;
			boolean cardinalitySet = false;
			while (indexInfo.next()){
				short type = indexInfo.getShort(7);
				if (type == DatabaseMetaData.tableIndexStatistic) {
					tableInfo.table.setCardinality(getCardinality(indexInfo));
					cardinalitySet = true;
					continue;
				}
				
				short ordinalPosition = indexInfo.getShort(8);
				if (useAnyIndexCardinality && !cardinalitySet) {
					long cardinality = getCardinality(indexInfo);
					tableInfo.table.setCardinality(Math.max(cardinality, (long)tableInfo.table.getCardinalityAsFloat()));
				}
				
				if (ordinalPosition <= savedOrdinalPosition){
					if (valid && indexColumns != null && (!uniqueOnly || !nonUnique) && (indexName == null || nonUnique || tableInfo.table.getPrimaryKey() == null || !indexName.equals(tableInfo.table.getPrimaryKey().getName()))) {
						addIndex(indexName, nonUnique, new ArrayList<String>(indexColumns.values()), tableInfo.table);
					} 
					indexColumns = new TreeMap<Short, String>();
					indexName = null;
					valid = true;
				}
				
				savedOrdinalPosition = ordinalPosition;
				String columnName = indexInfo.getString(9);
				if (valid && columnName == null || tableInfo.table.getColumnByName(columnName) == null){
					valid = false;
				}
				
				nonUnique = indexInfo.getBoolean(4);
				indexColumns.put(ordinalPosition, columnName);
				if (indexName == null) {
					indexName = indexInfo.getString(6);
					if (indexName == null) {
						indexName = "NDX_" + tableInfo.table.getName().toUpperCase();
					}
				}
				
				if (valid && indexColumns != null && (!uniqueOnly || !nonUnique) && (indexName == null || nonUnique || tableInfo.table.getPrimaryKey() == null || !indexName.equals(tableInfo.table.getPrimaryKey().getName()))) {
					addIndex(indexName, nonUnique, new ArrayList<String>(indexColumns.values()), tableInfo.table);
				}
			}
			indexInfo.close();
		}
	}
	
	private KeyRecord addIndex(String indexName, boolean nonUnique, ArrayList<String> columnNames, Table table) {
		
		KeyRecord index = new KeyRecord(nonUnique?KeyRecord.Type.Index:KeyRecord.Type.Unique);
		index.setParent(table);
		index.setColumns(new ArrayList<Column>(columnNames.size()));
		index.setName(indexName);
		assignColumns(columnNames, table, index);
		index.setUuid(this.formUUID(index));
		if (nonUnique) {
			table.getIndexes().add(index);
		} else {
			table.getUniqueKeys().add(index);
		}
		return index;
	}

	private long getCardinality(ResultSet indexInfo) throws SQLException {
		long result = Table.UNKNOWN_CARDINALITY;
		try {
			result = indexInfo.getLong(11);
			
		} catch (SQLException e) {
			//can't get as long, try int
			result = indexInfo.getInt(11);
		}
		if (indexInfo.wasNull()) {
			return Table.UNKNOWN_CARDINALITY;
		}
		return result;
	}
	
	protected boolean getIndexInfoForTable(String catalogName, String schemaName, String tableName, boolean uniqueOnly, boolean approximateIndexes, String tableType) {
		return true;
	}

	protected void getTableStatistics(Connection conn, String catalog, String schema, String name, Table table) {
		
	}

	private void getProcedures(Object metadataFactory, DatabaseMetaData metadata) throws SQLException {
		ResultSet procedures = metadata.getProcedures(catalog, schemaPattern, procedureNamePattern);
		int rsColumns = procedures.getMetaData().getColumnCount();
		while (procedures.next()) {
			String procedureCatalog = procedures.getString(1);
			String procedureSchema = procedures.getString(2);
			String procedureName = procedures.getString(3);
			if (useProcedureSpecificName && rsColumns >= 9) {
				procedureName = procedures.getString(9);
			}
			
			String fullProcedureName = getFullyQualifiedName(procedureCatalog, procedureSchema, procedureName);
			Procedure procedure = addProcedure(useFullSchemaName ? fullProcedureName : procedureName);
			ResultSet columns = metadata.getProcedureColumns(catalog, procedureSchema, procedureName, null);
			while (columns.next()) {
				String columnName = columns.getString(4);
				short columnType = columns.getShort(5);
				int sqlType = columns.getInt(6);
				String typeName = columns.getString(7);
				sqlType = checkForUnsigned(sqlType, typeName);
				if (columnType == DatabaseMetaData.procedureColumnUnknown) {
					continue; //there's a good chance this won't work
				}
				
				ProcedureParameter record = null;
				int precision = columns.getInt(8);
				String runtimeType = getRuntimeType(sqlType, typeName, precision);
				switch (columnType) {
				case DatabaseMetaData.procedureColumnResult:
					record = addProcedureResultSetColumn(columnName, runtimeType, procedure);
					break;
				case DatabaseMetaData.procedureColumnIn:
					record = addProcedureParameter(columnName, runtimeType, Type.In, procedure);
					break;
				case DatabaseMetaData.procedureColumnInOut:
					record = addProcedureParameter(columnName, runtimeType, Type.InOut, procedure);
					break;
				case DatabaseMetaData.procedureColumnOut:
					record = addProcedureParameter(columnName, runtimeType, Type.Out, procedure);
					break;
				case DatabaseMetaData.procedureColumnReturn:
					record = addProcedureParameter(columnName, runtimeType, Type.ReturnValue, procedure);
					break;
				default:
					continue; //shouldn't happen
				}
			}
		}
		procedures.close();
	}

	private ProcedureParameter addProcedureParameter(String columnName, String runtimeType, Type in, Procedure procedure) {
		return null;
	}

	private ProcedureParameter addProcedureResultSetColumn(String columnName, String runtimeType, Procedure procedure) {
		return null;
	}

	private Procedure addProcedure(String name) {
		Procedure procedure = new Procedure();
		procedure.setName(name);
		procedure.setUuid(this.formUUID(procedure));
		procedure.setParameters(new LinkedList<ProcedureParameter>());
		return procedure;
	}

	private void initJdbcTypeNamesMap() throws IllegalArgumentException, IllegalAccessException {
		JdbcTypeNames = new HashMap<>();
	    for (Field field : Types.class.getFields()) {
	    	JdbcTypeNames.put((Integer)field.get(null), field.getName());
	    }

	}
	
	private Map<String, TableInfo> getTables(Object metadataFactory, DatabaseMetaData metadata, Connection conn) throws SQLException {
		
		ResultSet tables = metadata.getTables(catalog, schemaPattern, tableNamePattern, tableTypes);
		Map<String, TableInfo> tableMap = new HashMap<String, TableInfo>();
		while (tables.next()){
			String tableCatalog = tables.getString(1);
			String tableSchema = tables.getString(2);
			String tableName = tables.getString(3);
			String remarks = tables.getString(5);
			String fullName = getFullyQualifiedName(tableCatalog, tableSchema, tableName);
			
			Table table = addTable(metadataFactory, tableCatalog, tableSchema, tableName, remarks, fullName);
			if (table == null) {
				continue;
			}
			TableInfo ti = new TableInfo(tableCatalog, tableSchema, tableName, table);
			ti.type = tables.getString(4);
			tableMap.put(fullName, ti);
			tableMap.put(tableName, ti);			
		}
		tables.close();
		getColumns(metadata, tableMap, conn);
		return tableMap;
	}
	
	private void getColumns(DatabaseMetaData metadata, Map<String, TableInfo> tableMap, Connection conn) throws SQLException {
		
		for (TableInfo ti : new LinkedHashSet<TableInfo>(tableMap.values())) {
			ResultSet columns = metadata.getColumns(ti.catalog, ti.schema, ti.name, columnNamePattern);
			processColumns(tableMap, columns, conn);
		}
		
	}

	private void processColumns(Map<String, TableInfo> tableMap, ResultSet columns, Connection conn) throws SQLException {
		
		int rsColumns = columns.getMetaData().getColumnCount();
		while (columns.next()){
			String tableCatalog = columns.getString(1);
			String tableSchema = columns.getString(2);
			String tableName = columns.getString(3);
			String fullTableName = getFullyQualifiedName(tableCatalog, tableSchema, tableName);
			TableInfo tableInfo = tableMap.get(fullTableName);
			if (tableInfo == null) {
				tableInfo = tableMap.get(tableName);
				if (tableInfo == null) {
					continue;
				}
			}
			addColumn(columns, tableInfo.table, rsColumns);
		}
		columns.close();
		
	}

	private Column addColumn(ResultSet columns, Table table, int rsColumns) throws SQLException {
		String columnName = columns.getString(4);
		int type = columns.getInt(5);
		String typeName = columns.getString(6);
		int columnSize = columns.getInt(7);
		String runtimeType = getRuntimeType(type, typeName, columnSize);
		Column column = addColumn(columnName, runtimeType, table);
		column.setPrecision(columnSize);
		column.setLength(columnSize);
		column.setNativeType(typeName);
		column.setRadix(columns.getInt(10));
		column.setNullType(NullType.values()[columns.getInt(11)]);
		column.setUpdatable(true);
		String remarks = columns.getString(12);
		column.setAnnotation(remarks);
		String defaultValue = columns.getString(13);
		column.setDefaultValue(defaultValue);
		column.setCharOctetLength(columns.getInt(16));
		if (rsColumns >= 23) {
			column.setAutoIncremented("YES".equalsIgnoreCase(columns.getString(23))); //$NON-NLS-1$
		}
		return column;
	}

	private Column addColumn(String columnName, String runtimeType, Table table) {
		Column column = new Column();
		column.setName(columnName);
		table.addColumn(column);
		column.setParent(table);
		column.setPosition(table.getColumns().size());
		column.setType(runtimeType);
		column.setUuid(formUUID(column));
		return column;
	}

	private String getRuntimeType(int type, String typeName, int columnSize) {
		if (type == Types.BIT && columnSize > 1) {
			type = Types.BINARY;
		}
		type = checkForUnsigned(type, typeName);
		return JdbcTypeNames.get(type);
	}
	
	private int checkForUnsigned(int sqlType, String typeName) {
		if (widenUnsingedTypes && unsignedTypes.contains(typeName)) {
			switch (sqlType) {
			case Types.TINYINT:
				sqlType = Types.SMALLINT;
				break;
			case Types.SMALLINT:
				sqlType = Types.INTEGER;
				break;
			case Types.INTEGER:
				sqlType = Types.BIGINT;
				break;
			}
		}
		return sqlType;
	}

	protected Table addTable(Object metadataFactory, String tableCatalog, String tableSchema, String tableName, String remarks, String fullName) {
		
		Table table = new Table();
		table.setTableType(Table.Type.Table);
		table.setName(useFullSchemaName ? fullName : tableName);
		table.setUuid(formUUID(table));
		table.setSupportsUpdate(true);
		table.setAnnotation(remarks);
		
		return table;
	}

	private String formUUID(AbstractMetadataRecord table) {
		
		int lsb = table.getUuid().hashCode();
		lsb = 31 * lsb + table.getName().hashCode();
		String uuid = StringUtil.hex(lsb, 8) ;
		return uuid;
	}

	private String getFullyQualifiedName(String catalogName, String schemaName, String objectName) {
		return getFullyQualifiedName(catalogName, schemaName, objectName, false);
	}

	private String getFullyQualifiedName(String catalogName, String schemaName, String objectName, boolean quoted) {
		
		String fullName = (quoted?quoteName(objectName):objectName);
		if (useQualifiedName){
			if (schemaName != null && schemaName.length() > 0) {
				fullName = (quoted?quoteName(schemaName):schemaName) + NAME_DELIM_CHAR + fullName;
			}
			if (useCatalogName && catalogName != null && catalogName.length() > 0) {
				fullName = (quoted?quoteName(catalogName):catalogName) + NAME_DELIM_CHAR + fullName;
			}
		}
		return fullName;
	}
	
	protected String quoteName(String name) {
		if (quoteString != null) {
			return quoteString + StringUtil.replaceAll(name, quoteString, quoteString + quoteString) + quoteString;
		}
		return name;
	}

	public String[] getTableTypes() {
		return tableTypes;
	}

	public void setTableTypes(String[] tableTypes) {
		this.tableTypes = tableTypes;
	}

	public String getTableNamePattern() {
		return tableNamePattern;
	}

	public void setTableNamePattern(String tableNamePattern) {
		this.tableNamePattern = tableNamePattern;
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchemaPattern() {
		return schemaPattern;
	}

	public void setSchemaPattern(String schemaPattern) {
		this.schemaPattern = schemaPattern;
	}

	public boolean isWidenUnsingedTypes() {
		return widenUnsingedTypes;
	}

	public void setWidenUnsingedTypes(boolean widenUnsingedTypes) {
		this.widenUnsingedTypes = widenUnsingedTypes;
	}

	public boolean isUseFullSchemaName() {
		return useFullSchemaName;
	}

	public void setUseFullSchemaName(boolean useFullSchemaName) {
		this.useFullSchemaName = useFullSchemaName;
	}

	public boolean isUseQualifiedName() {
		return useQualifiedName;
	}

	public void setUseQualifiedName(boolean useQualifiedName) {
		this.useQualifiedName = useQualifiedName;
	}

	public boolean isUseCatalogName() {
		return useCatalogName;
	}

	public void setUseCatalogName(boolean useCatalogName) {
		this.useCatalogName = useCatalogName;
	}

	public boolean isImportKeys() {
		return importKeys;
	}

	public void setImportKeys(boolean importKeys) {
		this.importKeys = importKeys;
	}

	public boolean isImportForeignKeys() {
		return importForeignKeys;
	}

	public void setImportForeignKeys(boolean importForeignKeys) {
		this.importForeignKeys = importForeignKeys;
	}

	public boolean isImportIndexes() {
		return importIndexes;
	}

	public void setImportIndexes(boolean importIndexes) {
		this.importIndexes = importIndexes;
	}

	public boolean isImportStatistics() {
		return importStatistics;
	}

	public void setImportStatistics(boolean importStatistics) {
		this.importStatistics = importStatistics;
	}

	public boolean isImportProcedures() {
		return importProcedures;
	}

	public void setImportProcedures(boolean importProcedures) {
		this.importProcedures = importProcedures;
	}

	public String getColumnNamePattern() {
		return columnNamePattern;
	}

	public void setColumnNamePattern(String columnNamePattern) {
		this.columnNamePattern = columnNamePattern;
	}

	public Set<String> getUnsignedTypes() {
		return unsignedTypes;
	}

	public void setUnsignedTypes(Set<String> unsignedTypes) {
		this.unsignedTypes = unsignedTypes;
	}

	public Map<Integer, String> getJdbcTypeNames() {
		return JdbcTypeNames;
	}

	public void setJdbcTypeNames(Map<Integer, String> jdbcTypeNames) {
		JdbcTypeNames = jdbcTypeNames;
	}

	public String getQuoteString() {
		return quoteString;
	}

	public void setQuoteString(String quoteString) {
		this.quoteString = quoteString;
	}

	public boolean isImportApproximateIndexes() {
		return importApproximateIndexes;
	}

	public void setImportApproximateIndexes(boolean importApproximateIndexes) {
		this.importApproximateIndexes = importApproximateIndexes;
	}
	
	public String getProcedureNamePattern() {
		return procedureNamePattern;
	}

	public void setProcedureNamePattern(String procedureNamePattern) {
		this.procedureNamePattern = procedureNamePattern;
	}

	public boolean isUseAnyIndexCardinality() {
		return useAnyIndexCardinality;
	}

	public void setUseAnyIndexCardinality(boolean useAnyIndexCardinality) {
		this.useAnyIndexCardinality = useAnyIndexCardinality;
	}

	public boolean isAutoCreateUniqueConstraints() {
		return autoCreateUniqueConstraints;
	}

	public void setAutoCreateUniqueConstraints(boolean autoCreateUniqueConstraints) {
		this.autoCreateUniqueConstraints = autoCreateUniqueConstraints;
	}

	public boolean isUseProcedureSpecificName() {
		return useProcedureSpecificName;
	}

	public void setUseProcedureSpecificName(boolean useProcedureSpecificName) {
		this.useProcedureSpecificName = useProcedureSpecificName;
	}

	private class FKInfo {
		TableInfo pkTable;
		TreeMap<Short, String> keyColumns = new TreeMap<Short, String>();
		TreeMap<Short, String> referencedKeyColumns = new TreeMap<Short, String>();
		boolean valid = true;
	}

}
