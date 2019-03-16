package com.demo.cache;

public interface Cache<K, V> {
	
	public boolean set(K key, V value);
	public V get(K key);

}
