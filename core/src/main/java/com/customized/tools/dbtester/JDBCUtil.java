package com.customized.tools.dbtester;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCUtil {
	
	public static Connection getConnection(String driver, String url, String user, String pass) throws InstantiationException, IllegalAccessException, SQLException, ClassNotFoundException  {
		Class<?> c = Class.forName(driver);
		Driver d = (Driver) c.newInstance();
		DriverManager.registerDriver(d);
		return DriverManager.getConnection(url, user, pass); 
	}
	
	public static Connection getConnection(String driver, String url) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class<?> c = Class.forName(driver);
		Driver d = (Driver) c.newInstance();
		DriverManager.registerDriver(d);
		return DriverManager.getConnection(url); 
	}

	public static void close(Connection conn) {

		if(null != conn) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				
			}
		}
	}
	
	public static void close(Statement stmt) {

		if(null != stmt) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
				
			}
		}
	}
	
	public static void close(ResultSet rs) {

		if (null != rs) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
			}
		}
		
	}
	
	public static void close(ResultSet rs, Statement stmt) {

		if (null != rs) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
			}
		}
		
		if(null != stmt) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
			}
		}
	}
	
	
	public static void close(ResultSet rs, Statement stmt, Connection conn) {

		if (null != rs) {
			try {
				rs.close();
				rs = null;
			} catch (SQLException e) {
			}
		}
		
		if(null != stmt) {
			try {
				stmt.close();
				stmt = null;
			} catch (SQLException e) {
			}
		}
		
		if(null != conn) {
			try {
				conn.close();
				conn = null;
			} catch (SQLException e) {
				
			}
		}
	}
	
	public static void printTableColumn(Connection conn, String sql) throws Exception {
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData metadata = rs.getMetaData();
			int columns = metadata.getColumnCount();
			for(int i = 1 ; i <= columns ; i ++) {
				System.out.print(metadata.getColumnName(i) + "/" + metadata.getColumnTypeName(i) + "  ");
			}
		} catch (Exception e) {
			throw e ;
		} finally {
			close(rs, stmt);
		}
	}
	
	public static int countResults(Connection conn, String sql) throws Exception{
		
		int count = 0;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				count ++ ;
			}
		} catch (Exception e) {
			throw e ;
		} finally {
			close(rs, stmt);
		}
		
		return count ;
		
	}

	public static Object query(Connection conn, String sql) throws Exception {
		
		System.out.println("Query SQL: " + sql);
		
		Object result = null;
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			rs.next();
			result = rs.getObject(1);
		} catch (Exception e) {
			throw e ;
		} finally {
			close(rs, stmt);
		}
		
		return result ;
	}
	
	public static void executeQuery(Connection conn, String sql) throws Exception {
		
		System.out.println("Query SQL: " + sql);
		
		Statement stmt = null;
		ResultSet rs = null;
		
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			ResultSetMetaData metadata = rs.getMetaData();
			int columns = metadata.getColumnCount();
			for (int row = 1; rs.next(); row++) {
				System.out.print(row + ": ");
				for (int i = 0; i < columns; i++) {
					if (i > 0) {
						System.out.print(", ");
					}
					System.out.print(rs.getObject(i + 1));
				}
				System.out.println();
			}
		} catch (Exception e) {
			throw e ;
		} finally {
			close(rs, stmt);
		}
				
	}

	public static boolean executeUpdate(Connection conn, String sql) throws Exception {
		
		System.out.println("Update SQL: " + sql);
		
		
		Statement stmt = null;
		
		try {
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
		} catch (Exception e) {
			throw e;
		} finally {
			close(stmt);
		}
		
		return true ;
	}

}
