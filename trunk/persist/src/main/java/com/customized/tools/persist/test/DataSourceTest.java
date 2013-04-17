package com.customized.tools.persist.test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import com.customized.tools.persist.DataSourceUtil;
import com.customized.tools.persist.H2Helper;

public class DataSourceTest extends TestBase{
	
	private static Logger log = Logger.getLogger(DataSourceTest.class);

	public static void main(String[] args) throws Exception {

		testH2();
		
		testOracle();
	}

	private static void testH2() throws SQLException {
		
		log.info("Test Datasouce via H2, Test Procedure: Start H2 -> Set up DataSource -> get Connection -> execute DDL -> Stop H2");
		
		H2Helper.startH2Server();
		
		DataSource ds = DataSourceUtil.setupDataSource("jdbc/test-ds");
		
		Connection conn = ds.getConnection();
		
		Statement stmt = conn.createStatement();
		
		try {
			stmt.execute("DROP TABLE TEST");
		} catch (Exception e) {
			// ignore
		}
		
		stmt.execute("CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))");
		
		stmt.execute("INSERT INTO TEST VALUES(1, 'Hello')");
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM TEST");
		
		while(rs.next()) {
			System.out.println(rs.getInt(1) + ", " + rs.getString(2));
		}
		
		rs.close();
		
		stmt.close();
		
		conn.close();
		
		H2Helper.stopH2Server();
	}

	private static void executeDDL(Connection conn) throws SQLException {
		
		Statement stmt = conn.createStatement();

		stmt.execute("CREATE TABLE TEST(ID INT PRIMARY KEY, NAME VARCHAR(255))");
		
		stmt.execute("INSERT INTO TEST VALUES(1, 'Hello')");
		System.out.println();
	}

	private static void testOracle() {
		
	}

}
