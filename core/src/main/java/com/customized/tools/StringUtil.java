package com.customized.tools;

public class StringUtil {
	
	public static String hex(long val, int hexLength) {
		long hi = 1L << (Math.min(63, hexLength * 4));
		return Long.toHexString(hi | (val & (hi - 1))).substring(1);
    }
	
	public static String replaceAll(String source, String search, String replace) {
	    if (source == null || search == null || search.length() == 0 || replace == null) {
	    	return source;
	    }
        int start = source.indexOf(search);
        if (start > -1) {
	        StringBuffer newString = new StringBuffer(source);
	        while (start > -1) {
	            int end = start + search.length();
	            newString.replace(start, end, replace);
	            start = newString.indexOf(search, start + replace.length());
	        }
	        return newString.toString();
        }
	    return source;    
	}

}
