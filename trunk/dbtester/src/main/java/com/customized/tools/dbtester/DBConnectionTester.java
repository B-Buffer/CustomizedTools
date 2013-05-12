package com.customized.tools.dbtester;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.customized.tools.cli.WizardConsole;
import com.customized.tools.common.ToolsURLClassLoader;
import com.customized.tools.po.DBTester;

public class DBConnectionTester {
	
	private static final Logger logger = Logger.getLogger(DBConnectionTester.class);

	private static final String TABLE_CREATE = "create table tool_dbconn_kylin_test(id int)";
	private static final String TABLE_INSERT = "insert into tool_dbconn_kylin_test values(100)";
	private static final String TABLE_DROP = "drop table tool_dbconn_kylin_test";
	
	private DBTester dbTester;
	
	private WizardConsole console;
	
	public DBConnectionTester(DBTester dbTester, WizardConsole console) {
		this.dbTester = dbTester;
		this.console = console;
	}

	public void execute() {
		
		logger.info("DB Connection Test Start");

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		
		try {	
			if(console.readFromCli("DBConnectionTester")) {
				DBTesterWizard wizard = (DBTesterWizard) console.popWizard(new DBTesterWizard(dbTester));
				dbTester = wizard.getDBTester();
			}
			
			console.prompt("DBConnectionTester Properties: " +  dbTester);
			
			loadDriverLib();
			
			conn = getConnection();
			
			promptConnectionResult(conn);
			
			stmt = conn.createStatement();
			stmt.executeUpdate(TABLE_CREATE);
			console.prompt("Create Table Success");
			
			stmt.execute(TABLE_INSERT);
			console.prompt("Insert Success");
			
			stmt.execute(TABLE_DROP);
			console.prompt("Drop Table Success");
			
		} catch (Exception e) {
			DBConnectionTesterException ex = new DBConnectionTesterException("DBConnectionTester met exception", e);
			console.prompt("Test Failed, due to " + ex.getMessage());
			logger.error("", ex);
			throw ex;
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

	private void loadDriverLib() {
		
		String libPath = dbTester.getDriverlib();
		
		if(libPath.equals("lib")){
			libPath = System.getProperty("cst.home") + File.separator + libPath;
		} 
		
		ToolsURLClassLoader classLoader = new ToolsURLClassLoader(Thread.currentThread().getContextClassLoader());
		classLoader.loadDependencyJars(libPath);
		Thread.currentThread().setContextClassLoader(classLoader);
	}

	private void promptConnectionResult(Connection conn) throws SQLException {

		DatabaseMetaData meta = conn.getMetaData();
		
		StringBuffer sb = new StringBuffer();
		sb.append("Create Databse Connection [" + dbTester.getUrl() + " - " + dbTester.getUsername() + "/******]\n\n");
		sb.append("Databse Connection Test Success\n\n");
		sb.append(meta.getDatabaseProductVersion());
		
		console.prompt(sb.toString());
		
		logger.info(sb.toString());
		
	}

	private Connection getConnection() throws Exception {

		try {
			Class c = Class.forName(dbTester.getDriver());
			Driver d = (Driver) c.newInstance();
			DriverManager.registerDriver(d);
			Connection conn = DriverManager.getConnection(dbTester.getUrl(), dbTester.getUsername(), dbTester.getPassword());
			return conn;
		} catch (Exception e) {
			throw e ;
		}
	}
	
}
