package com.customized.tools.persist;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.h2.tools.Server;

public class H2Helper {
	
	private static Server server = null;
	
	private static final Logger log = Logger.getLogger(H2Helper.class);
	
	public static Server getServer() throws SQLException {
		if(null == server) {
			server = Server.createTcpServer(new String[0]);
		}
		return server;
	}

	/**
	 * start h2 in memory database
	 * @return
	 */
	public static Server startH2Server() {
		try {
			if(!getServer().isRunning(false)){
				server.start();
			}
			
			log.info("H2 Server is started: " + server.getStatus());
			
	        return server;
		} catch (Throwable t) {
			throw new RuntimeException("Could not start H2 server", t);
		}
	}
	
	public static void stopH2Server() {
		try {
			getServer().stop();
			log.info("H2 Server is stoped: " + server.getStatus());
		} catch (Throwable t) {
			throw new RuntimeException("Could not stop H2 server", t);
		}
	}
	
	public static void shutdownH2Server() {
		try {
			if(getServer().isRunning(false)) {
				getServer().stop();
			}
			getServer().shutdown();
			log.info("H2 Server is shutdown: " + server.getStatus());
		} catch (Throwable t) {
			throw new RuntimeException("Could not shutdown H2 server", t);
		}
	}
}
