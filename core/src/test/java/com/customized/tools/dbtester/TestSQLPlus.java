package com.customized.tools.dbtester;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

import org.h2.tools.RunScript;
import org.junit.Test;

public class TestSQLPlus {
	
	private static final String DRIVER = "org.h2.Driver";
	private static final String URL = "jdbc:h2:mem://localhost/~/test";
	private static final String USER = "sa";
	private static final String PASSWORD = "sa";
	
	private static String SQL = "CREATE TABLE TEST_TABLE (COL1 INTEGER NOT NULL, COL2 CHAR(25), PRIMARY KEY (COL1));"
							  + "INSERT INTO TEST_TABLE VALUES(1, '1');"
							  + "INSERT INTO TEST_TABLE VALUES(2, '1');"
							  + "INSERT INTO TEST_TABLE VALUES(3, '3');"
							  + "UPDATE TEST_TABLE SET COL2 = 'update' WHERE COL1 = 1;"
							  + "SELECT * FROM TEST_TABLE;"
							  + "DELETE FROM TEST_TABLE WHERE COL2 = '1';"
							  +	"DROP TABLE TEST_TABLE;";
	
	@Test
	public void testSQLPlus() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException {
		SQLSession session = new SQLSession(DRIVER, URL, USER, PASSWORD);
		SQLPlus plus = new SQLPlus(session, System.out);
		plus.executeLines(SQL);
		plus.shutdown();
	}
	
	@Test
	public void testSelect() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException {
		SQLSession session = new SQLSession(DRIVER, URL, USER, PASSWORD);
		RunScript.execute(session.getConn(), new FileReader("src/test/resources/customer-schema-h2.sql"));
		SQLPlus plus = new SQLPlus(session, System.out);
		plus.executeLines("SELECT * FROM ACCOUNT;");
		plus.shutdown();
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
	public void testSQLSessionNoPrintStream() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		SQLSession session = new SQLSession(DRIVER, URL, USER, PASSWORD);
		assertNotNull(session.getConn());
		assertNotNull(session.createStatement());
		assertNotNull(session.getDatabaseInfo());
		session.close();
		
		SQLSessionManager manager = SQLSessionManager.Factory.create(new SQLSession(DRIVER, URL, USER, PASSWORD));
		assertNotNull(manager.getCurrentSession().getConn());
		assertNotNull(manager.getCurrentSession().createStatement());
		assertNotNull(manager.getCurrentSession().getDatabaseInfo());
		manager.getCurrentSession().close();
	}
	
	@Test
	public void testSQLParser(){
		SQLCommandParser parser = new SQLCommandParser();
		parser.append(SQL);
		
		String expectCreate = "CREATE TABLE TEST_TABLE (COL1 INTEGER NOT NULL, COL2 CHAR(25), PRIMARY KEY (COL1))";
		String expectInsert = "INSERT INTO TEST_TABLE VALUES(1, '1')";
		String expectUpdate = "UPDATE TEST_TABLE SET COL2 = 'update' WHERE COL1 = 1";
		String expectDelete = "DELETE FROM TEST_TABLE WHERE COL2 = '1'";
		String expectSelect = "SELECT * FROM TEST_TABLE";
		String expectDrop = "DROP TABLE TEST_TABLE";
		
		Set<String> set = new HashSet<>();
		
		while(parser.hasNext()){
			set.add(parser.next());
		}
		
		assertEquals(8, set.size());
		assertTrue(set.contains(expectCreate));
		assertTrue(set.contains(expectInsert));
		assertTrue(set.contains(expectDelete));
		assertTrue(set.contains(expectUpdate));
		assertTrue(set.contains(expectSelect));
		assertTrue(set.contains(expectDrop));
	}
	
	@Test
	public void testSQLParser_0(){
		SQLCommandParser parser = new SQLCommandParser();
		parser.append("   \n \n   \t \t  / ");
		assertFalse(parser.hasNext());
	}
	
	@Test
	public void testSQLParser_1() {
		SQLCommandParser parser = new SQLCommandParser();
		parser.append(" ;\n\t");
		assertFalse(parser.hasNext());
	}
	
	@Test
	public void testSQLParser_2() {
		SQLCommandParser parser = new SQLCommandParser();
		parser.append(" foo;");
		
		String expected = "foo";
		String result = null;
		while(parser.hasNext()){
			result = parser.next();
		}
		assertEquals(expected, result);
	}
	
	@Test
	public void testSQLParser_3() {
		SQLCommandParser parser = new SQLCommandParser();
		parser.append(";foo;");
		
		String expected = "foo";
		Set<String> set = new HashSet<>();
		while(parser.hasNext()){
			set.add(parser.next());
		}
		assertEquals(1, set.size());
		assertTrue(set.contains(expected));
	}
	
	@Test
	public void testSQLParserStringBuffer(){
		
		StringBuffer sb = new StringBuffer();
		sb.append("12345");
		sb.delete(0, 1);
		assertEquals("2345", sb.toString());
		
		sb = new StringBuffer();
		sb.append("12345");
		sb.delete(2, 4);
		assertEquals("125", sb.toString());
		
		sb = new StringBuffer();
		sb.append("12345");
		sb.delete(3, 4);
		assertEquals("1235", sb.toString());
		
		sb = new StringBuffer();
		sb.append("12345");
		sb.deleteCharAt(0);
		assertEquals("2345", sb.toString());
		
		sb = new StringBuffer();
		sb.append("12345");
		sb.deleteCharAt(4);
		assertEquals("1234", sb.toString());
		
		sb = new StringBuffer();
		sb.append("12345");
		sb.delete(0, sb.length());
		assertEquals(0, sb.length());
		assertEquals("", sb.toString());
		
		sb = new StringBuffer();
		sb.append("12345;   \n\t");
		for(int i = sb.length() - 1; i >= 0 ; i--){
			char c = sb.charAt(i);
			if (c == ';' || Character.isWhitespace(c)){
				sb.deleteCharAt(i);
			}
		}
		assertEquals("12345", sb.toString());
		
	}
	
	@Test
	public void testSQLParserRough() {
		
		SQLCommandParser parser = new SQLCommandParser();
		
		String lines = "select * from foo   ; select * from bar \n\n  "
				     + "select * from zoo;\n"
				     + " ;\n\t";
		
		parser.append(lines);
		
		Set<String> set = new HashSet<>();
		while(parser.hasNext()){
			set.add(parser.next());
		}

		System.out.println(set);
		
		assertEquals(3, set.size());
		assertTrue(set.contains("select * from foo"));
		assertTrue(set.contains("select * from bar"));
		assertTrue(set.contains("select * from zoo"));
	}
	
}
