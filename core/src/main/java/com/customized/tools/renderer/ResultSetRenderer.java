package com.customized.tools.renderer;


import java.sql.ResultSet;
import java.sql.Clob;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.io.Reader;

import com.customized.tools.Interruptable;

public class ResultSetRenderer implements Interruptable {
	
    private final ResultSet rset;
    private final ResultSetMetaData meta;
    private final TableRenderer table;
    private final int columns;
    private final int[] showColumns;

    private boolean beyondLimit;
    private long firstRowTime;
    private long lastRowTime;
    private final long    clobLimit = 8192;
    private final int     rowLimit;
    private volatile boolean running;
    
    private OutputDevice out;

    public ResultSetRenderer(ResultSet rset, 
    						 String columnDelimiter,
                             boolean enableHeader, 
                             boolean enableFooter,
                             int limit,
                             OutputDevice out, 
                             int[] show) 
		throws SQLException {
		this.rset = rset;
		this.beyondLimit = false;
		this.firstRowTime = -1;
		this.lastRowTime = -1;
		this.showColumns = show;
		this.out = out;
	    this.rowLimit = limit;
	    this.meta = rset.getMetaData();
	    this.columns = (show != null) ? show.length : meta.getColumnCount();
	    this.table = new TableRenderer(getDisplayMeta(meta),  out, columnDelimiter, enableHeader, enableFooter);
    }

    public ResultSetRenderer(ResultSet rset, 
    						 String columnDelimiter,
                             boolean enableHeader, 
                             boolean enableFooter, 
                             int limit,
                             OutputDevice out) throws SQLException {
    	
    	this(rset, columnDelimiter, enableHeader, enableFooter, limit, out, null);
    }
    
    /*
     *  Interruptable interface.(non-Javadoc)
     * @see com.customized.tools.Interruptable#interrupt()
     */
    public synchronized void interrupt() {
    	running = false;
    }

    public ColumnMetaData[] getDisplayMetaData() {
        return table.getMetaData();
    }

    private String readClob(Clob c) throws SQLException {
        if (c == null) return null;
        StringBuffer result = new StringBuffer();
        long restLimit = clobLimit;
        try {
            Reader in = c.getCharacterStream();
            char buf[] = new char [ 4096 ];
            int r;

            while (restLimit > 0 
                   && (r = in.read(buf, 0, (int)Math.min(buf.length,restLimit))) > 0) 
                {
                    result.append(buf, 0, r);
                    restLimit -= r;
                }
        }
        catch (Exception e) {
           out.println(e.toString());
        }
        if (restLimit == 0) {
            result.append("...");
        }
        return result.toString();
    }

    public int execute() throws SQLException {
    	
		int rows = 0;
	
		running = true;
		try {
		    while (running && rset.next()) {
			Column[] currentRow = new Column[ columns ];
			for (int i = 0 ; i < columns ; ++i) {
			    int col = (showColumns != null) ? showColumns[i] : i+1;
	                    String colString;
	                    if (meta.getColumnType( col ) == Types.CLOB) {
	                        colString = readClob(rset.getClob( col ));
	                    }
	                    else {
	                        colString = rset.getString( col );
	                    }
			    Column thisCol = new Column(colString);
			    currentRow[i] = thisCol;
			}
			if (firstRowTime < 0) {
	                    // read first row completely.
			    firstRowTime = System.currentTimeMillis();
			}
			table.addRow(currentRow);
			++rows;
				if (rows >= rowLimit) {
				    beyondLimit = true;
				    break;
				}
		    }
		    
		    if(lastRowTime < 0){
		    	lastRowTime = System.currentTimeMillis();
		    }
		    
		    table.renderer();
	            if (!running) {
	                try {
	                    rset.getStatement().cancel();
	                }
	                catch (Exception e) {
	                    out.println("cancel statement failed: " + e.getMessage());
	                }
	            }
		}
		finally {
		    rset.close();
		}
		return rows;
    }
    
    public boolean limitReached() {
    	return beyondLimit;
    }
    
    public long getFirstRowTime() {
    	return firstRowTime;
    }

    public long getLastRowTime() {
		return lastRowTime;
	}
    
    public String elapsedTime(){
    	double elapsedTimeSec = (lastRowTime - firstRowTime)/1000D;
    	return "("+ Double.parseDouble(new DecimalFormat("##.##").format(elapsedTimeSec)) + " sec)";
    }

	/**
     * determine meta data necesary for display.
     */
    private ColumnMetaData[] getDisplayMeta(ResultSetMetaData m) throws SQLException {
    	
		ColumnMetaData result[] = new ColumnMetaData [ columns ];
	
		for (int i = 0; i < result.length; ++i) {
		    int col = (showColumns != null) ? showColumns[i] : i+1;
		    int alignment  = ColumnMetaData.ALIGN_LEFT;
		    String columnLabel = m.getColumnLabel( col );
		    /*
		    int width = Math.max(m.getColumnDisplaySize(i),
					 columnLabel.length());
		    */
		    switch (m.getColumnType( col )) {
		    case Types.NUMERIC:  
		    case Types.INTEGER: 
		    case Types.REAL:
		    case Types.SMALLINT:
		    case Types.TINYINT:
			alignment = ColumnMetaData.ALIGN_RIGHT;
			break;
		    }
		    result[i] = new ColumnMetaData(columnLabel,alignment);
		}
		return result;
    }
}

