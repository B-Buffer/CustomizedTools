package com.customized.tools.dbtester;

import java.sql.Connection;

import org.junit.Ignore;
import org.junit.Test;

import com.customized.tools.dbtester.metdata.Metdata;
import com.customized.tools.dbtester.metdata.Table;

public class TestJDBCMetdataProcessor {
	
	@Test
	@Ignore
	public void testMysqlMetdata() throws Exception {
		
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/test";
		String user = "jdv_user";
		String password = "jdv_pass";
		
		Connection conn = JDBCUtil.getConnection(driver, url, user, password);
		
		JDBCMetdataProcessor processor = new JDBCMetdataProcessor();
		
		Metdata metadata = new Metdata();
		processor.process(metadata, conn);
		
		for(Table table : metadata.getTables()){
			System.out.println(table.getName());
		}
		
		JDBCUtil.close(conn);
	}
	
	public static void main(String[] args) throws Exception {
		
		TestJDBCMetdataProcessor test = new TestJDBCMetdataProcessor();
		
		test.testMysqlMetdata();
	}

}
