package com.customized.tools.dbtester;

import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.customized.tools.renderer.Column;
import com.customized.tools.renderer.ColumnMetaData;
import com.customized.tools.renderer.OutputDevice;
import com.customized.tools.renderer.PrintStreamOutputDevice;
import com.customized.tools.renderer.ResultSetRenderer;
import com.customized.tools.renderer.TableRenderer;
import com.customized.tools.renderer.TerminalOutputDevice;
import com.customized.tools.renderer.TreeNode;
import com.customized.tools.renderer.TreeRenderer;

public class TestRenderer {
	
	private static final String DRIVER = "org.h2.Driver";
	private static final String URL = "jdbc:h2:mem://localhost/~/test";
	private static final String USER = "sa";
	private static final String PASSWORD = "sa";
	
	private static String SQL_CREATE = "CREATE TABLE TEST_TABLE (COL1 INTEGER NOT NULL, COL2 CHAR(25), PRIMARY KEY (COL1));"
			  + "INSERT INTO TEST_TABLE VALUES(1, '1');"
			  + "INSERT INTO TEST_TABLE VALUES(2, '1');"
			  + "INSERT INTO TEST_TABLE VALUES(3, '3');";
	
	private static String SQL_DROP = "DROP TABLE TEST_TABLE;";
	
	static SQLSession session;
	static SQLPlus plus;
	
	@BeforeClass
	public static void init() throws Exception {
		session = new SQLSession(DRIVER, URL, USER, PASSWORD);
		plus = new SQLPlus(session, System.out);
		plus.executeLines(SQL_CREATE);
	}
	
	@Test
	public void testTableRenderer_0() {
		ColumnMetaData[] metadata = new ColumnMetaData [3];
		for(int i = 0 ; i < 3 ; i ++) {
			metadata[i] = new ColumnMetaData("COL"+ i, ColumnMetaData.ALIGN_CENTER);
		}
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		TableRenderer renderer = new TableRenderer(metadata,  out, "|", true, true);
		renderer.renderer();
	}
	
	@Test
	public void testTableRenderer_1() {
		ColumnMetaData[] metadata = new ColumnMetaData [3];
		for(int i = 0 ; i < 3 ; i ++) {
			metadata[i] = new ColumnMetaData("COL"+ i, ColumnMetaData.ALIGN_CENTER);
		}
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		TableRenderer renderer = new TableRenderer(metadata,  out, "|", true, true);
		
		for(int i = 0 ; i < 3 ; i ++){
			Column[] row = new Column[3];
			for(int j = 0 ; j < 3 ; j ++){
				row[j] = new Column(j);
			}
			renderer.addRow(row);
		}
		renderer.renderer();
	}
	
	@Test
	public void testTableRenderer_2() {
		ColumnMetaData[] metadata = new ColumnMetaData [3];
		for(int i = 0 ; i < 3 ; i ++) {
			metadata[i] = new ColumnMetaData("COL"+ i, ColumnMetaData.ALIGN_CENTER);
		}
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		TableRenderer renderer = new TableRenderer(metadata,  out, "|", true, true);
		
		for(int i = 0 ; i < 3 ; i ++){
			Column[] row = new Column[3];
			for(int j = 0 ; j < 3 ; j ++){
				row[j] = new Column(j);
			}
			renderer.addRow(row);
		}
		
		Column[] row = new Column[3];
		row[0] = new Column(0);
		row[1] = new Column(1);
		row[2] = new Column("1234567890");
		
		renderer.addRow(row);
		
		renderer.renderer();
	}
	
	@Test
	public void testTableRenderer_3() {
		ColumnMetaData[] metadata = new ColumnMetaData [3];
		for(int i = 0 ; i < 3 ; i ++) {
			metadata[i] = new ColumnMetaData("COL"+ i, ColumnMetaData.ALIGN_LEFT);
		}
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		TableRenderer renderer = new TableRenderer(metadata,  out, "|", true, true);
		
		for(int i = 0 ; i < 3 ; i ++){
			Column[] row = new Column[3];
			for(int j = 0 ; j < 3 ; j ++){
				row[j] = new Column(j);
			}
			renderer.addRow(row);
		}
		
		Column[] row = new Column[3];
		row[0] = new Column(0);
		row[1] = new Column(1);
		row[2] = new Column("1234567890");
		
		renderer.addRow(row);
		
		renderer.renderer();
	}
	
	@Test
	public void testTableRenderer_4() {
		ColumnMetaData[] metadata = new ColumnMetaData [3];
		for(int i = 0 ; i < 3 ; i ++) {
			metadata[i] = new ColumnMetaData("COL"+ i, ColumnMetaData.ALIGN_RIGHT);
		}
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		TableRenderer renderer = new TableRenderer(metadata,  out, "|", true, true);
		
		for(int i = 0 ; i < 3 ; i ++){
			Column[] row = new Column[3];
			for(int j = 0 ; j < 3 ; j ++){
				row[j] = new Column(j);
			}
			renderer.addRow(row);
		}
		
		Column[] row = new Column[3];
		row[0] = new Column(0);
		row[1] = new Column(1);
		row[2] = new Column("1234567890");
		
		renderer.addRow(row);
		
		renderer.renderer();
	}
	
	@Test
	public void testTableRenderer_5() {
		ColumnMetaData[] metadata = new ColumnMetaData [3];
		for(int i = 0 ; i < 3 ; i ++) {
			metadata[i] = new ColumnMetaData("COL"+ i, ColumnMetaData.ALIGN_RIGHT);
		}
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		TableRenderer renderer = new TableRenderer(metadata,  out, "|", true, true);
		
		for(int i = 0 ; i < 3 ; i ++){
			Column[] row = new Column[3];
			for(int j = 0 ; j < 3 ; j ++){
				row[j] = null;
			}
			renderer.addRow(row);
		}
				
		renderer.renderer();
	}
	
	@Test
	public void testTableRenderer_6() {
		ColumnMetaData[] metadata = new ColumnMetaData [3];
		for(int i = 0 ; i < 3 ; i ++) {
			metadata[i] = new ColumnMetaData("COL"+ i, ColumnMetaData.ALIGN_RIGHT);
		}
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		TableRenderer renderer = new TableRenderer(metadata,  out, "|", true, true);
		
		for(int i = 0 ; i < 3 ; i ++){
			Column[] row = new Column[3];
			for(int j = 0 ; j < 3 ; j ++){
				row[j] = new Column(null);
			}
			renderer.addRow(row);
		}
				
		renderer.renderer();
	}
	
	@Test
	public void testTableRenderer_7() {
		ColumnMetaData[] metadata = new ColumnMetaData [3];
		for(int i = 0 ; i < 3 ; i ++) {
			metadata[i] = new ColumnMetaData("COL"+ i, ColumnMetaData.ALIGN_RIGHT);
		}
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		TableRenderer renderer = new TableRenderer(metadata,  out, "|", true, true);
		
		for(int i = 0 ; i < 1 ; i ++){
			Column[] row = new Column[3];
			for(int j = 0 ; j < 3 ; j ++){
				row[j] = new Column(j);
			}
			renderer.addRow(row);
		}
		
		Column[] row = new Column[3];
		row[0] = new Column(0);
		row[1] = new Column(1);
		row[2] = new Column("a\nb\nc\n");
		
		renderer.addRow(row);
		
		for(int i = 0 ; i < 1 ; i ++){
			Column[] rows = new Column[3];
			for(int j = 0 ; j < 3 ; j ++){
				rows[j] = new Column(j);
			}
			renderer.addRow(rows);
		}
		
		renderer.renderer();
	}
	
	@Test
	public void testTableRenderer_8() {
		ColumnMetaData[] metadata = new ColumnMetaData [2];
		metadata[0] = new ColumnMetaData("COL0", ColumnMetaData.ALIGN_RIGHT);
		metadata[1] = new ColumnMetaData("COL1", ColumnMetaData.ALIGN_RIGHT);
		TableRenderer renderer = new TableRenderer(metadata,  new PrintStreamOutputDevice(System.out), "|", true, true);
		Column[] row = new Column[2];
		row[0] = new Column(0);
		row[1] = new Column(1);
		renderer.addRow(row);
		renderer.renderer();
	}
	
	@Test
	public void testTableRenderer_9(){
		ColumnMetaData[] metadata = ColumnMetaData.Factory.create("COL1", "COL2", "COL3");
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		TableRenderer renderer = new TableRenderer(metadata,  out, "|", true, true);
		renderer.addRow(Column.Factory.create(0, 1, 2));
		renderer.addRow(Column.Factory.create(0, 1, 2));
		renderer.renderer();
	}
	
	@Test
	public void testResultSetRenderer_0() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException {
		
		Connection conn = session.getConn();
		
		String columnDelimiter = "|";
    	int rowLimit = 1000;
    	boolean showHeader = true;
    	boolean showFooter = true;
    	
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt .executeQuery("SELECT * FROM TEST_TABLE");
    	
    	ResultSetRenderer render = new ResultSetRenderer(rs, columnDelimiter, showHeader, showFooter, rowLimit, new PrintStreamOutputDevice(System.out));
		
    	render.execute();
 
    	JDBCUtil.close(rs, stmt);
    	
	}
	
	@Test
	public void testResultSetRenderer_1() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException {
		
		Connection conn = session.getConn();
		
		String columnDelimiter = "|";
    	int rowLimit = 1000;
    	boolean showHeader = true;
    	boolean showFooter = true;
    	
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt .executeQuery("SELECT * FROM TEST_TABLE");
    	
    	ResultSetRenderer render = new ResultSetRenderer(rs, columnDelimiter, showHeader, showFooter, rowLimit, new TerminalOutputDevice(System.out));
		
    	render.execute();
 
    	JDBCUtil.close(rs, stmt);
    	
	}
	
	@Test
	public void testResultSetRenderer_2() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException, FileNotFoundException {
		
		Connection conn = session.getConn();
		
		String columnDelimiter = "|";
    	int rowLimit = 1000;
    	boolean showHeader = true;
    	boolean showFooter = true;
    	
    	Statement stmt = conn.createStatement();
    	ResultSet rs = stmt .executeQuery("SELECT H2VERSION(), CURRENT_USER(), CURRENT_DATE(), CURRENT_TIME()");
    	
    	ResultSetRenderer render = new ResultSetRenderer(rs, columnDelimiter, showHeader, showFooter, rowLimit, new PrintStreamOutputDevice(System.out));
		
    	render.execute();
 
    	JDBCUtil.close(rs, stmt);
    	
	}
	
	@Test
	public void testTreeRenderer_0() {
		
		TreeNode<String> a = new TreeNode<>("a");
		TreeNode<String> b = new TreeNode<>("b");
		TreeNode<String> c = new TreeNode<>("c");
		TreeNode<String> d = new TreeNode<>("d");
		TreeNode<String> e = new TreeNode<>("e");
		a.addChild(b).addChild(c).addChild(d);
		a.addChild(e);
		
		OutputDevice out = new PrintStreamOutputDevice(System.out);
		
		TreeRenderer renderer = new TreeRenderer(a, out);
		renderer.renderer();
	}
	
	@Test
	public void testTreeRenderer_1() {
		
		TreeNode<String> a = new TreeNode<>("a");
		a.addChild("b").addChild("c").addChild("d").addChild("e");
		new TreeRenderer(a, new PrintStreamOutputDevice(System.out)).renderer();
	}
	
	@Test
	public void testTreeRenderer_2() {
		
		TreeNode<String> a = new TreeNode<>("a");
		TreeNode<String> b = new TreeNode<>("b");
		TreeNode<String> c = new TreeNode<>("c");
		TreeNode<String> d = new TreeNode<>("d");
		TreeNode<String> e = new TreeNode<>("e");
		a.addChild(b).addChild(c).addChild(d);
		b.addChild(e);
		
		new TreeRenderer(a, new PrintStreamOutputDevice(System.out)).renderer();
	}
	
	@Test
	public void testTreeRenderer_3() {
		
		TreeNode<String> a = new TreeNode<>("a");
		TreeNode<String> b = new TreeNode<>("b123456789");
		TreeNode<String> c = new TreeNode<>("c");
		TreeNode<String> d = new TreeNode<>("d");
		TreeNode<String> e = new TreeNode<>("e");
		a.addChild(b).addChild(c).addChild(d);
		b.addChild(e);
		
		new TreeRenderer(a, new PrintStreamOutputDevice(System.out)).renderer();
	}
	
	@Test
	public void testTreeRenderer_4() {
		
		TreeNode<String> a = new TreeNode<>("a");
		TreeNode<String> b = new TreeNode<>("b");
		TreeNode<String> c = new TreeNode<>("c");
		TreeNode<String> d = new TreeNode<>("d");
		TreeNode<String> e = new TreeNode<>("e");
		TreeNode<String> f = new TreeNode<>("f");
		TreeNode<String> g = new TreeNode<>("g");
		a.addChild(b).addChild(c).addChild(d);
		a.addChild(e);
		b.addChild(f);
		b.addChild(g);
		
		new TreeRenderer(a, new PrintStreamOutputDevice(System.out)).renderer();
	}
	
	@Test
	public void testTreeRenderer_5() {
		
		TreeNode<String> root = new TreeNode<>(".");
		TreeNode<String> a = new TreeNode<>("a");
		TreeNode<String> b = new TreeNode<>("b");
		TreeNode<String> c = new TreeNode<>("c");
		TreeNode<String> d = new TreeNode<>("d");
		TreeNode<String> e = new TreeNode<>("e");
		root.addChild(a);
		a.addChild(b).addChild(c);
		root.addChild(d);
		root.addChild(e);
		
		new TreeRenderer(root, new TerminalOutputDevice(System.out)).renderer();
	}
	
	@AfterClass
	public static void close() {
		plus.executeLines(SQL_DROP);
		plus.shutdown();
	} 
}
