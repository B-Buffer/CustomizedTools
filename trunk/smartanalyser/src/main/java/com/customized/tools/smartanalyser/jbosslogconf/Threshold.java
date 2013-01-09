package com.customized.tools.smartanalyser.jbosslogconf;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

public class Threshold {
	
	private static final int DEFAULT_QUEUE_SIZE = 50 ;
	
	private static final Logger logger = Logger.getLogger(Threshold.class);
	
	private BlockingQueue<FileEntity> fileQueue;
	
	private boolean isFinitish = false;

	public boolean isFinitish() {
		return isFinitish && fileQueue.size() == 0;
	}

	public void setFinitish(boolean isFinitish) {
		this.isFinitish = isFinitish;
	}

	public Threshold(Integer capacity) {

		initQueue(capacity);
	}

	public Threshold() {
		initQueue(null);
	}

	private void initQueue(Integer capacity) {

		if (capacity == null) {
			capacity = DEFAULT_QUEUE_SIZE;
		}
		
		fileQueue = new ArrayBlockingQueue<FileEntity>(capacity); 
		
		if (logger.isDebugEnabled()) {
			logger.debug("Created JBossLogConfAnalyser ArrayBlockingQueue with capacity: " + capacity);
		}

	}
	
	 public void addToThresholdQueue(String threadName, FileEntity entity) {

		try {
			fileQueue.put(entity);

			if (logger.isDebugEnabled()){
				logger.debug(threadName + " add a Entity to fileQueue, Queue size: " + fileQueue.size());
			}
         } catch (InterruptedException e) {
                 throw new JBossLogConfAnalyserException("add to fileQueue Exception[" + threadName + "]", e );
         }
	 }
		
	public FileEntity getFromThresholdQueue(String threadName){
          
		FileEntity entity;
		try {
			entity = fileQueue.take();

			if (logger.isDebugEnabled()) {
				logger.debug(threadName + " Consumed from fileQueue, Queue Size:" + fileQueue.size());
			}
			return entity;
		} catch (InterruptedException e) {
			throw new JBossLogConfAnalyserException("Take FileEntity from threshold Exception", e);
		}

	}
		
}
