package com.demo.cache;

import java.util.Deque;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;

public class LRUCache<K,V> extends AbstractCache<K,V> {
	
	private Map<K,V> map ;
	private Deque<K> queue;
	
	 LRUCache(int capacity) {
		super(capacity);
		map = new ConcurrentHashMap<K, V>();
		queue = new ConcurrentLinkedDeque<K>();
	}

	public boolean set(K key, V value) {
		boolean hasKey = map.containsKey(key);
		if (!hasKey && this.capacity <= queue.size()) {
			this.evict();
		}
		map.put(key, value);
		this.promote(key);
		return true;
	
	}

	private void evict() {
		map.remove(queue.removeLast());
	}
	
	private void promote(K key) {
		queue.remove(key);
		queue.addFirst(key);
		
	}
	public V get(K key) {
		this.promote(key);
		return map.get(key);
	}

}
