package com.customized.tools;

import java.util.ResourceBundle;

import com.customized.tools.util.BundleUtil;

public class CorePlugin {
	
	public static final String PLUGIN_ID = CorePlugin.class.getPackage().getName();
	
	public static final BundleUtil Util = new BundleUtil(PLUGIN_ID, PLUGIN_ID + ".i18n", ResourceBundle.getBundle(PLUGIN_ID + ".i18n"));
	
	public static enum Event implements BundleUtil.Event {
		CSTCORE1000,
		CSTCORE1001,
		CSTCORE1002,
		CSTCORE1003,
		CSTCORE1004,
		CSTCORE1005,
		CSTCORE1006,
		CSTCORE1007,
		CSTCORE1008,
		CSTCORE1009,
	}

}
