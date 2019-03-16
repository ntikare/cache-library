package com.demo.cache.persistant.strategy;

public interface PersistenceStrategy<K,V> {

	public boolean persist(K key, V val);
	

}
