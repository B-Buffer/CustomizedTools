package com.customized.tools.persist.test;

import java.sql.SQLException;

import org.h2.tools.Server;

import com.customized.tools.persist.H2Helper;

public class H2HelperTest extends TestBase {

	

	public static void main(String[] args) throws Exception {

		// testgetServer();

		testMethods();
	}

	private static void testMethods() throws SQLException {
		
		H2Helper.startH2Server();
		
		H2Helper.stopH2Server();

		H2Helper.shutdownH2Server();

	}

	private static void testgetServer() throws SQLException {

		Server server = H2Helper.getServer();
		server.start();
		server.start();
		System.out.println(server.getStatus());
		System.out.println(server.isRunning(false));
	}

}
