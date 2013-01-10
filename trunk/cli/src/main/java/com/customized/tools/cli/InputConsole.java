package com.customized.tools.cli;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class InputConsole extends Console {
	
	public boolean readFromCli(String prompt) {
		
		String msg = "Run " + prompt + " From Command Line\n" +
				 "  [1]. Yes\n" +
			     "  [2]. No\n" +
				 "Default is [1]";
		int a = '1';
		int b = '2';
		int res = readWithDef(msg, a, a, b);
		
		if(res == a) {
			return true ;
		} else {
			return false ;
		}
	}

	public int read(String prompt, int ...params) {
		
		Set set = new HashSet();
		for(int i : params) {
			set.add(i);
		}
		int result = 0;
		
		while(true) {
			
			result = keyPress(prompt);
			
			if(set.contains(result)) {
				break;
			}
		}
		
		return result ;
	}
	
	public int readWithDef(String prompt, int def, int ...params ) {
		
		Set set = new HashSet();
		for(int i : params) {
			set.add(i);
		}
		int result = 0;
		
		while(true) {
			
			result = keyPress(prompt);
			
			if(set.contains(result)) {
				break;
			} else if (result == 10) {
				result = def ;
				break;
			}
		}
		
		return result ;
	}
	
	public int keyPress(String msg) {
        
		println(msg);

        try {
			int ret = System.in.read();
            System.in.skip(System.in.available());
            return ret;
        }
        catch(IOException e) {
            return 0;
        }
    }
	
	public int keyPress() {
        
        try {
			int ret = System.in.read();
            System.in.skip(System.in.available());
            return ret;
        }
        catch(IOException e) {
            return 0;
        }
    }
	
	public String readFolderPath(String prompt, boolean validation) throws IOException {
		
		String result = "" ;
		
		while(true) {
			
			println(prompt);
			
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String input = bufferRead.readLine();
			
			if(validation && new File(input).exists() && new File(input).isDirectory()) {
				result = input;
				break;
			} else if(input.length() > 0){
				result = input;
				break;
			}
		}
		
		return result ;
	}
	
	public String readFilePath(String prompt, boolean validation) throws IOException {
		
		String result = "" ;
		
		while(true){
			
			println(prompt);
			
			BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
			String input = bufferRead.readLine();
			
			if(validation && new File(input).exists() && new File(input).isFile()) {
				result = input;
				break;
			} else if(input.length() > 0){
				result = input;
				break;
			}
		}
		
		return result;
	} 
	
	/**
	 * 
	 * @param prompt
	 * @param validation if true null value is not allowed
	 * @return
	 * @throws IOException
	 */
	public String readString(String prompt, boolean validation)  {
		
		String result = "" ;
		
		while(true){
			
			println(prompt);
			
			String input = null;
			try {
				BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
				input = bufferRead.readLine();
			} catch (IOException e) {
				throw new RuntimeException("", e);
			}
			
			if(validation && input.length() > 0){
				result = input;
				break;
			}
		}
		
		return result;
	} 
}
