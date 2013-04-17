package com.customized.tools.persist;


import bitronix.tm.resource.jdbc.PoolingDataSource;

public class DataSourceUtil {
	
	public static PoolingDataSource setupDataSource(String dataSourceName) {
        // create data source
		PoolingDataSource pds = new PoolingDataSource();
        pds.setUniqueName(dataSourceName);
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(5);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", "sa");
        pds.getDriverProperties().put("password", "");
        pds.getDriverProperties().put("url", "jdbc:h2:tcp://localhost/~/customized-db");
        pds.getDriverProperties().put("driverClassName", "org.h2.Driver");
        pds.init();
        return pds;
	}
	
	public static PoolingDataSource setupDataSource(String dataSourceName, String user, String password, String url, String driver) {
		PoolingDataSource pds = new PoolingDataSource();
        pds.setUniqueName(dataSourceName);
        pds.setClassName("bitronix.tm.resource.jdbc.lrc.LrcXADataSource");
        pds.setMaxPoolSize(5);
        pds.setAllowLocalTransactions(true);
        pds.getDriverProperties().put("user", user);
        pds.getDriverProperties().put("password", password);
        pds.getDriverProperties().put("url", url);
        pds.getDriverProperties().put("driverClassName", driver);
        pds.init();
        return pds;
	}

}
