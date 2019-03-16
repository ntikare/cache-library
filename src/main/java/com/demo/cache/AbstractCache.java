package com.demo.cache;

public abstract class AbstractCache<K,V> implements Cache<K,V> {
	protected int capacity;
	AbstractCache(int capacity) {
		this.capacity = capacity;

		
	}

	public abstract boolean set(K key, V value) ;
	public abstract V get(K key);


}
