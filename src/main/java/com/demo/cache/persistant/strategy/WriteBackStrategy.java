package com.demo.cache.persistant.strategy;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.demo.cache.KeyValuePair;
import com.demo.cache.persistant.PersistanceStore;

public class WriteBackStrategy<K,V> implements  PersistenceStrategy<K,V> {

	private BlockingQueue<KeyValuePair<K,V>> queue = new LinkedBlockingQueue<>();
	private Thread writeBackThread;
	private volatile boolean STOP_FLAG = false;
	
	private final KeyValuePair<K,V >  POISON = new KeyValuePair<>(null, null);
	
	
	private PersistanceStore<K, V> store;
	
	public WriteBackStrategy(PersistanceStore<K, V> store) {
		this.store = store;
		this.startThread();
		this.handleShutDown();
	}
	

	
	public boolean persist(K key, V val) {
		queue.add(new KeyValuePair<K,V>(key, val));
		return true;
	}

	private void startThread() {
		writeBackThread = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(!STOP_FLAG || !queue.isEmpty()) {
					try {
						KeyValuePair<K,V > pair = queue.take();
						if (pair != POISON) {
							store.writeToStore(pair.getKey(), pair.getValue());
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
				
			}
		});
		writeBackThread.setDaemon(false);
		writeBackThread.start();
	}
	
	private void handleShutDown() {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				STOP_FLAG = true;
				//provision to break infinite loop in the thread
				queue.offer(POISON);
				try {
					writeBackThread.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}));
	}
}
