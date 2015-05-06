package com.customized.tools.dbtester;

import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import static com.customized.tools.common.Constants.LN;
import static com.customized.tools.common.Constants.TAB;


public class SQLSession {
	
	private long connectTime;
    private long statementCount;
    
    private String driver;
    private String url;
    private String username;
    private String password;
    
    private String databaseInfo;
    private Connection conn;
    
    private PrintStream out = null;

	public SQLSession(String driver, String url, String username, String password) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		this(driver, url, username, password, null);
	}
	
	public SQLSession(String driver, String url, String username, String password, PrintStream out) throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		super();
		this.driver = driver;
		this.url = url;
		this.username = username;
		this.password = password;
		this.out = out;
				
		connect();
	}

	public void connect() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {

		JDBCUtil.close(conn);
		
		if (username == null || password == null){
			conn = JDBCUtil.getConnection(driver, url);
		}
		
		if(null == conn){
			conn = JDBCUtil.getConnection(driver, url, username, password);
		}
		
		if(conn != null) {
			
			connectTime = System.currentTimeMillis();
			
			DatabaseMetaData meta = conn.getMetaData();
			if(username == null){
				username = meta.getUserName();
			}
			StringBuffer sb = new StringBuffer();
			sb.append("Databse Connection [ " + url + "/" + username + "/******] Created at " + connectTime);
			sb.append(LN).append(TAB);
			sb.append(meta.getDatabaseProductName() + " - " + meta.getDatabaseProductVersion());
			
			int currentIsolation = Connection.TRANSACTION_NONE;
			if (meta.supportsTransactions()) {
				currentIsolation = conn.getTransactionIsolation();
			}
			
			
			sb.append(LN).append("Connection transaction isolation level");
			
			appendIsolation(meta, Connection.TRANSACTION_NONE, "No Transaction", currentIsolation, sb);
			appendIsolation(meta, Connection.TRANSACTION_READ_UNCOMMITTED, "Read Uncommitted", currentIsolation, sb);
			appendIsolation(meta, Connection.TRANSACTION_READ_COMMITTED, "Read Committed", currentIsolation, sb);
			appendIsolation(meta, Connection.TRANSACTION_REPEATABLE_READ, "Repeatable Read", currentIsolation, sb);
			appendIsolation(meta, Connection.TRANSACTION_SERIALIZABLE, "Serializable", currentIsolation, sb);
			
			databaseInfo = sb.toString();
		}
		
		propmpt(databaseInfo);
		
		
	}

	private void appendIsolation(DatabaseMetaData meta, int level, String msg, int currentIsolation, StringBuffer sb) throws SQLException {
		if (meta.supportsTransactionIsolationLevel(level)) {
			sb.append(LN).append(TAB).append(msg);
			sb.append(currentIsolation == level ? " -> Current Isolation Level" : "");
		}
		
	}

	public long getUptime() {
		return connectTime;
	}

	public long getStatementCount() {
		return statementCount;
	}

	public String getUrl() {
		return url;
	}

	public String getUsername() {
		return username;
	}

	public String getDatabaseInfo() {
		return databaseInfo;
	}

	public Connection getConn() {
		return conn;
	}
	
	public void close(){
		JDBCUtil.close(conn);
		this.connectTime = -1;
		this.statementCount = -1;
		this.databaseInfo = null;
	}
	
	public Statement createStatement()  {
		
		Statement stmt = null;
		int retries = 3;
		
		try {
			if(conn.isClosed()) {
				propmpt("connection is closed; reconnect.");
				connect();
			}
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			propmpt(e.getMessage());
		}
		
		while (retries > 0) {
			try {
				stmt = conn.createStatement();
				statementCount ++;
				break;
			} catch (Exception e) {
				propmpt("connection failure. Try to reconnect.");
				try {
					connect();
				} catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e1) {
					propmpt(e1.getMessage());
				}
			}
			retries -- ;
		}
		return stmt;
	}

	public void propmpt(String databaseInfo) {
		
		if(null == out) {
			return ;
		}
		
		out.println(databaseInfo);
	}
    
    

}
