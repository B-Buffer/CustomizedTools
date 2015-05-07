package com.customized.tools.dbtester;

import java.io.FileReader;
import java.sql.Connection;

import org.h2.tools.RunScript;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.customized.tools.dbtester.metdata.Metadata;
import com.customized.tools.dbtester.metdata.Table;

public class TestJDBCMetdataProcessor {
	
	private static final String DRIVER = "org.h2.Driver";
	private static final String URL = "jdbc:h2:mem://localhost/~/test";
	private static final String USER = "sa";
	private static final String PASSWORD = "sa";
	
	static Metadata metadata = new Metadata();
	
	@BeforeClass
	public static void init() throws Exception{
		
		SQLSession session = new SQLSession(DRIVER, URL, USER, PASSWORD);
		RunScript.execute(session.getConn(), new FileReader("src/test/resources/customer-schema-h2.sql"));
		JDBCMetdataProcessor processor = new JDBCMetdataProcessor();
		processor.process(metadata, session.getConn());
	}
	
	@Test
	public void testTables(){
		for(Table table : metadata.getTables()){
			System.out.println(table.getName());
		}
	}
	
	@Test
	@Ignore
	public void testMysqlMetdata() throws Exception {
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "jdv_user";
		String password = "jdv_pass";
		
		Connection conn = JDBCUtil.getConnection(driver, url, user, password);
		
		JDBCMetdataProcessor processor = new JDBCMetdataProcessor();
		
		Metadata metadata = new Metadata();
		processor.process(metadata, conn);
		
		for(Table table : metadata.getTables()){
			System.out.println(table.getName());
		}
		
		JDBCUtil.close(conn);
	}

}
