package com.customized.tools.dbtester;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.customized.tools.AbstractTools;
import com.customized.tools.cli.InputConsole;
import com.customized.tools.model.DBTester;
import com.customized.tools.model.Entity;

public class DBConnectionTester extends AbstractTools {
	
	private static final Logger logger = Logger.getLogger(DBConnectionTester.class);

	private static final String TABLE_CREATE = "create table tool_dbconn_kylin_test(id int)";
	private static final String TABLE_INSERT = "insert into tool_dbconn_kylin_test values(100)";
	private static final String TABLE_DROP = "drop table tool_dbconn_kylin_test";
	
	private DBTester dbTester;
	
	public DBConnectionTester(Entity entity, InputConsole console) {
		super(entity, console);
	}

	public void execute() {
		
		dbTester = (DBTester) entity;
		
		logger.info("DB Connection Test Start");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {	
			console.prompt("DBConnectionTester Properties: " +  dbTester);
						
			conn = JDBCUtil.getConnection(dbTester.getDriver(), dbTester.getUrl(), dbTester.getUsername(), dbTester.getPassword());
			
			promptConnectionResult(conn);
			
			stmt = conn.createStatement();
			stmt.executeUpdate(TABLE_CREATE);
			console.prompt("Create Table Success");
			
			stmt.execute(TABLE_INSERT);
			console.prompt("Insert Success");
			
			stmt.execute(TABLE_DROP);
			console.prompt("Drop Table Success");
			
		} catch (Throwable e) {
			DBConnectionTesterException ex = new DBConnectionTesterException("DBConnectionTester met exception", e);
			console.prompt("Test Failed, due to " + ex.getMessage());
			logger.error("", ex);
//			throw ex;
		}finally {
			try {
				if(rs != null) {
					rs.close();
				}
				if(stmt != null) {
					stmt.close();
				}
				if(conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				throw new DBConnectionTesterException("close DB Connection session Error", e);
			}
		}
	}

	private void promptConnectionResult(Connection conn) throws SQLException {

		DatabaseMetaData meta = conn.getMetaData();
		
		StringBuffer sb = new StringBuffer();
		sb.append("Create Databse Connection [" + dbTester.getUrl() + " - " + dbTester.getUsername() + "/******]" + console.ln(2));
		sb.append(console.tab() + "Databse Connection Test Success" + console.ln(2));
		sb.append(console.tab() + meta.getDatabaseProductVersion());
		
		console.prompt(sb.toString());
		
		logger.info(sb.toString());
		
	}
	
}
