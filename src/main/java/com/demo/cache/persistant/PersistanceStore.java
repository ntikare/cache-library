package com.demo.cache.persistant;

public interface PersistanceStore<K, V> {
	
	public boolean writeToStore(K k, V v);
	public V readFromStore(K k);

}
