package com.customized.tools.smartanalyser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.log4j.Logger;

import com.customized.tools.cli.InputConsole;
import com.customized.tools.po.Analyser;
import com.customized.tools.smartanalyser.Constants;
import com.customized.tools.smartanalyser.GCLogAnalyser;
import com.customized.tools.smartanalyser.HeapDumpAnalyser;
import com.customized.tools.smartanalyser.IAnalyser;
import com.customized.tools.smartanalyser.JBossLogConfAnalyser;
import com.customized.tools.smartanalyser.SmartAnalyserException;
import com.customized.tools.smartanalyser.ThreadDumpAnalyser;
import com.customized.tools.smartanalyser.status.SmartAnalyserStatusException;

public class SmartAnalyser {
	
	private static final Logger logger = Logger.getLogger(SmartAnalyser.class);
	
	private Analyser analyser;
	
	private InputConsole console;

	public SmartAnalyser(Analyser analyser, InputConsole console) {
		this.analyser = analyser;
		this.console = console;
	}
	

	public void execute()  {
		
		logger.info("Start SmartAnalyser...");
		
		try {
			
			int status = getAnalyerStatus();
			
			IAnalyser analyserImpl = null;
			
			switch (status) {
			case 1:
				analyserImpl = new JBossLogConfAnalyser(1, analyser, console, imgSet);
				break;
			case 2:
				analyserImpl = new GCLogAnalyser(2, analyser, console, imgSet);
				break;
			case 3:
				analyserImpl = new ThreadDumpAnalyser(3, analyser, console, imgSet);
				break;
			case 4:
				analyserImpl = new HeapDumpAnalyser(4, analyser, console, imgSet);
				break;
			default:
				break;
			}
			
			if(analyserImpl != null) {
				analyserImpl.analyser();
			} else {
				throw new SmartAnalyserStatusException("get Analyer Status return a error status code");
			}
			
		} catch (Exception e) {

			SmartAnalyserException ex = new SmartAnalyserException("Analyser returned a unexpected Exception ", e);
			
			console.prompt(ex.getMessage());
			
			throw ex;
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getAnalyerStatus() throws Exception {
		
		validateImg(analyser.getPath());
		
		for(Iterator iterator = imgSet.iterator(); iterator.hasNext();) {
			String path =  (String) iterator.next();
			
			if(isJBossLogConf(path)) {
				return Constants.STATUS_JBOSS_LOG_CONF;
			} else if(isGClog(path)) {
				return Constants.STATUS_GC_LOG;
			} else if(isThreadDump(path)) {
				return Constants.STATUS_THREAD_DUMP;
			} else if(isHeapDump(path)) {
				return Constants.STATUS_HEAP_DUMP;
			}
		}
		
		throw new SmartAnalyserStatusException("Get analyser code error");
	}


	private boolean isJBossLogConf(String path) {
		return path.contains("boot.log") || path.contains("server.log");
	}


	private boolean isGClog(String path) {
		// TODO Auto-generated method stub
		return false;
	}


	private boolean isThreadDump(String path) {
		// TODO Auto-generated method stub
		return false;
	}


	private boolean isHeapDump(String path) {
		// TODO Auto-generated method stub
		return false;
	}


	private void validateImg(String imgPath) throws Exception {
		
		File file = new File(imgPath);

		if(!(file.exists())) {
			throw new SmartAnalyserStatusException("image folder does not exist");
		}
		
		if(!(file.isDirectory())) {
			throw new SmartAnalyserStatusException("image folder is not a folder");
		}
		
		unzipFile(file);
		
		addToSet(file);
	}

	Set<String> imgSet = new HashSet<String>();

	private void addToSet(File file) {
		
		for(File f : file.listFiles()) {
			if(invalidImg(f)) {
				imgSet.add(f.getAbsolutePath());
			}
		}
	}


	private boolean invalidImg(File f) {
		
		String path = f.getAbsolutePath();
		return !path.endsWith(".zip");
	}


	private void unzipFile(File file) throws Exception {
		for(File f : file.listFiles()){
			if(f.isDirectory()) {
				unzipFile(f);
			} else if(f.getAbsolutePath().endsWith(".zip")) {
				unzip(f);
			}
		}
	}


	private void unzip(File f) throws Exception {
		
		console.prompt("\n  unzip file " + f + "\n");
		
		byte[] buf = new byte[1024];
	    ZipInputStream zinstream = new ZipInputStream(new FileInputStream(f));
	    ZipEntry zentry = zinstream.getNextEntry();
	   logger.debug("Name of current Zip Entry : " + zentry);
	    while (zentry != null) {
	      String entryName = zentry.getName();
	      logger.debug("Name of  Zip Entry : " + entryName);
	      FileOutputStream outstream = new FileOutputStream(new File(f.getParentFile(), entryName));
	      int n;

	      while ((n = zinstream.read(buf, 0, 1024)) > -1) {
	        outstream.write(buf, 0, n);

	      }
	      logger.debug("Successfully Extracted File Name : " + entryName);
	      outstream.close();

	      zinstream.closeEntry();
	      zentry = zinstream.getNextEntry();
	    }
	    zinstream.close();
	}

}
