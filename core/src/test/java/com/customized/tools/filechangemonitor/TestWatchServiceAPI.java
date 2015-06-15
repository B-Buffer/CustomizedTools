package com.customized.tools.filechangemonitor;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.ArrayList;
import java.util.List;

import org.junit.Ignore;

@Ignore
public class TestWatchServiceAPI {

	public static void main(String[] args) throws IOException, InterruptedException {
		
		List<WatchKey> list = new ArrayList<>();
		
		register(new File("/home/kylin/server/jboss-eap-6.3"), list);

		
		while(true) {
			for(WatchKey watchKey : list) {
				List<WatchEvent<?>> events = watchKey.pollEvents();
				for (WatchEvent<?> event : events) {
			    	System.out.println("---");
			    	if (event.kind() == ENTRY_CREATE){
			    		System.out.println("Created: " + event.context().toString() + " " + event.count());
			    	}
			    	
			    	if (event.kind() == ENTRY_DELETE){
			    		System.out.println("Deleted: " + event.context().toString());
			    	}
			    	
			    	if (event.kind() == ENTRY_MODIFY){
			    		System.out.println("Modified: " + event.context().toString());
			    	}
			    }
			}
			Thread.sleep(100);
		}
		

	}

	private static void register(File root, List<WatchKey> list) throws IOException {
		
		if(root.isDirectory()){
			list.add(getKey(root.toURI()));
			for(File sub : root.listFiles()){
				register(sub, list);
			}
		}
	}

	private static WatchKey getKey(URI uri) throws IOException {
		Path path = Paths.get(uri);
		WatchService watchService = path.getFileSystem().newWatchService();
		return path.register(watchService,  ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
	}

}
