package com.customized.tools.dbtester;

public class SQLPlus {
	
	public static final byte LINE_EXECUTED   = 1;
    public static final byte LINE_EMPTY      = 2;
    public static final byte LINE_INCOMPLETE = 3;
    
    public byte executeLine(String line){
    	
    	byte result = LINE_EMPTY;
    	
    	/*
    	 * Special oracle comment 'rem'ark; should be in the comment parser.
    	 * ONLY if it is on the beginning of the line, no whitespace.
    	 * 
    	 */
    	int startWhite = 0;
    	if(line.length() >= (3 + startWhite) && (line.substring(startWhite,startWhite+3).toUpperCase().equals("REM")) && (line.length() == 3 || Character.isWhitespace(line.charAt(3)))){
    		return LINE_EMPTY;
    	}
    	
    	StringBuffer lineBuf = new StringBuffer(line);
    	lineBuf.append('\n');
    	
    	return result;
    }

}
