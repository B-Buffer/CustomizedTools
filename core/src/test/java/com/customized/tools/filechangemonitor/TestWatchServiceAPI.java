package com.customized.tools.filechangemonitor;

import static java.nio.file.StandardWatchEventKinds.OVERFLOW;
import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;

import org.junit.Ignore;

@Ignore
public class TestWatchServiceAPI {

	public static void main(String[] args) throws IOException {

		Path path = Paths.get("/home/kylin/tmp");
		WatchService watchService = path.getFileSystem().newWatchService();
		WatchKey watchKey = path.register(watchService, OVERFLOW, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
		
		while(true) {
			List<WatchEvent<?>> events = watchKey.pollEvents();
		    for (WatchEvent<?> event : events) {
		    	System.out.println("-> " + event.count() + " event(s):");
		        Object context = event.context();
		        if(context instanceof Path){
		            Path path2 = (Path) context;
		            System.out.print("\tPath: " + path2);
		        }
		        System.out.println("\tKind: " + event.kind());
		    }
		    try {
				Thread.sleep(500);
			} catch (InterruptedException e) {

			}

		}
	}

}
