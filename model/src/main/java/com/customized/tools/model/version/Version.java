package com.customized.tools.model.version;

public class Version {

	public static final String V_1 = "1.0";
	
	public static final String V_2 = "2.0";
	
	public static final String V_3 = "3.0";
	
	public static final String V_CURRENT = V_3 ;
	
	public static final String NAME = "CustomizedTools";
	
	public static final String VERSION_STRING = NAME + " 'KylinSoong' " + V_CURRENT ;
	
	public static String version() {
		return V_CURRENT ;
	}
	
	public static String name() {
		return NAME ;
	}
	
	public static String versionString() {
		return VERSION_STRING ;
	}
}
