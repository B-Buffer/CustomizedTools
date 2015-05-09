package com.customized.tools.dbtester;

import java.io.PrintStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import com.customized.tools.renderer.ResultSetRenderer;
import com.customized.tools.renderer.TerminalOutputDevice;

/**
 *  A SQLPlus can execute one line SQL or multiple lines SQL.
 *  
 *  Support SQL including:
 *    * CREATE TABLE
 *    * INSERT TABLE
 *    * UPDATE TABLE
 *    * DELETE TABLE
 *    * SELECT TABLE
 *    * DROP   TABLE
 * 
 *
 */
public class SQLPlus {
	
	public static final byte LINE_EXECUTED   = 1;
    public static final byte LINE_EMPTY      = 2;
    public static final byte LINE_INCOMPLETE = 3;
    
    private String columnDelimiter;
    private int rowLimit;
    private boolean showHeader;
    private boolean showFooter;
    
    private final SQLCommandParser parser;
    
    private SQLSessionManager sessionManager;
    private PrintStream out;
    
    public SQLPlus(SQLSession session, PrintStream out) {
    	this("|", Integer.MAX_VALUE, true, true, session, out);
    }
    
    public SQLPlus(String columnDelimiter, int rowLimit, boolean showHeader, boolean showFooter, SQLSession session, PrintStream out){
    	
    	this.columnDelimiter = columnDelimiter;
    	this.rowLimit = rowLimit;
    	this.showHeader = showHeader;
    	this.showFooter = showFooter;
    	this.out = out;
    	
    	parser = new SQLCommandParser();
    	sessionManager = SQLSessionManager.Factory.create(session);
    	
    }
    
    /**
     * Execute rough SQL Statements either from command line or read from SQL file.
     * 
     * @param line
     * @return
     */
    public byte executeLines(String line){
    	
    	byte result = LINE_EMPTY;
    	
    	/*
    	 * Special oracle comment 'rem'ark; should be in the comment parser.
    	 * ONLY if it is on the beginning of the line, no whitespace.
    	 * 
    	 */
    	int startWhite = 0;
    	if(line.length() >= (3 + startWhite) && (line.substring(startWhite,startWhite+3).toUpperCase().equals("REM")) && (line.length() == 3 || Character.isWhitespace(line.charAt(3)))){
    		return LINE_EMPTY;
    	}
    	
    	StringBuffer lineBuf = new StringBuffer(line);
    	lineBuf.append('\n');
    	
    	parser.append(lineBuf.toString());
    	result = LINE_INCOMPLETE;
    	while(parser.hasNext()){
    		String completeCommand = parser.next();
    		execute(sessionManager.getCurrentSession(), completeCommand);
    		result = LINE_EXECUTED;
    	}
    	
    	return result;
    }

	private void execute(SQLSession session, String command) {

		if(command == null) {
			return;
		}
		
		final long startTime = System.currentTimeMillis();
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			if (command.startsWith("commit")){
				session.getConn().commit();
				session.propmpt(".done.");
			} else if (command.startsWith("rollback")) {
				session.getConn().rollback();
				session.propmpt(".done.");
			} else {
				stmt = session.createStatement();
				stmt.setFetchSize(200);
				
				final boolean hasResultSet = stmt.execute(command);
				
				if (hasResultSet){
					rs = stmt.getResultSet();
					ResultSetRenderer renderer = new ResultSetRenderer(rs, getColumnDelimiter(), isShowHeader(), isShowFooter(), getRowLimit(), new TerminalOutputDevice(out));
					int rows = renderer.execute();
					out.println(rows + " rows in set " + renderer.elapsedTime());
					
				} else {
					int updateCount = stmt.getUpdateCount();
					if (updateCount >= 0){
						out.println(updateCount + " rows affected " + elapsedTime(startTime) );
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String elapsedTime(long startTime){
		return elapsedTime(startTime, System.currentTimeMillis());
	}
	
	private String elapsedTime(long startTime, long endTime){
    	double elapsedTimeSec = (endTime - startTime)/1000D;
    	return "("+ Double.parseDouble(new DecimalFormat("##.##").format(elapsedTimeSec)) + " sec)";
    }

	public String getColumnDelimiter() {
		return columnDelimiter;
	}

	public void setColumnDelimiter(String columnDelimiter) {
		this.columnDelimiter = columnDelimiter;
	}

	public SQLSessionManager getSessionManager() {
		return sessionManager;
	}

	public void setSessionManager(SQLSessionManager sessionManager) {
		this.sessionManager = sessionManager;
	}

	public SQLCommandParser getParser() {
		return parser;
	}

	public int getRowLimit() {
		return rowLimit;
	}

	public void setRowLimit(int rowLimit) {
		this.rowLimit = rowLimit;
	}

	public boolean isShowHeader() {
		return showHeader;
	}

	public void setShowHeader(boolean showHeader) {
		this.showHeader = showHeader;
	}

	public boolean isShowFooter() {
		return showFooter;
	}

	public void setShowFooter(boolean showFooter) {
		this.showFooter = showFooter;
	}
	
	public void shutdown() {
		sessionManager.closeAll();
		parser.discard();
	}

}
