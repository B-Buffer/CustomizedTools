package com.customized.tools.common.console;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class InputConsole extends Console {

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
}
