package com.customized.tools.dbtester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;

import org.h2.tools.RunScript;
import org.junit.Test;

import com.customized.tools.dbtester.view.PrintStreamOutputDevice;
import com.customized.tools.dbtester.view.ResultSetRenderer;

public class TestSQLPlus {
	
	private static final String DRIVER = "org.h2.Driver";
	private static final String URL = "jdbc:h2:mem://localhost/~/test";
	private static final String USER = "sa";
	private static final String PASSWORD = "sa";
	
	@Test
	public void testSelect() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException {
		SQLSession session = new SQLSession(DRIVER, URL, USER, PASSWORD);
		RunScript.execute(session.getConn(), new FileReader("src/test/resources/customer-schema-h2.sql"));
		SQLPlus plus = new SQLPlus(session, System.out);
		plus.executeLines("SELECT * FROM ACCOUNT; SELECT * FROM PRODUCT");
		plus.shutdown();
	}
	
	@Test
	public void testResultSetRenderer() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException {
		Connection conn = JDBCUtil.getConnection(DRIVER, URL, USER, PASSWORD);
		RunScript.execute(conn, new FileReader("src/test/resources/customer-schema-h2.sql"));
		
		String columnDelimiter = "|";
    	int rowLimit = 1000;
    	boolean showHeader = true;
    	boolean showFooter = true;
    	
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt .executeQuery("SELECT * FROM ACCOUNT");
    	
    	ResultSetRenderer render = new ResultSetRenderer(rs, columnDelimiter, showHeader, showFooter, rowLimit, new PrintStreamOutputDevice(System.out));
		
    	int rows = render.execute();
    	
    	System.out.println(rows + " rows in set (0.00 sec)");
    	
    	JDBCUtil.close(rs, stmt);
    	
		JDBCUtil.close(conn);
	}
	
	@Test
	public void testResultSetRendererElapsedTime() throws InterruptedException {
		double elapsedTimeSec = 235345/1000D;
		elapsedTimeSec = Double.parseDouble(new DecimalFormat("##.##").format(elapsedTimeSec));
		assertEquals(elapsedTimeSec, 235.34, 2);
	}
	
	@Test
	public void testSQLSession() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		SQLSession session = new SQLSession(DRIVER, URL, USER, PASSWORD, System.out);
		assertNotNull(session.getConn());
		assertNotNull(session.createStatement());
		assertNotNull(session.getDatabaseInfo());
		session.close();
		
		SQLSessionManager manager = SQLSessionManager.Factory.create(new SQLSession(DRIVER, URL, USER, PASSWORD, System.out));
		assertNotNull(manager.getCurrentSession().getConn());
		assertNotNull(manager.getCurrentSession().createStatement());
		assertNotNull(manager.getCurrentSession().getDatabaseInfo());
		manager.getCurrentSession().close();
		
	}
	
	@Test
	public void testSQLParser() {
		
		SQLCommandParser parser = new SQLCommandParser();
		
		parser.append("select * from foo   ; select * from bar \n\n  ");
		parser.append("select * from zoo;");
		
		while(parser.hasNext()){
			String sql = parser.next();
			System.out.println(sql);
		}
	}

}
