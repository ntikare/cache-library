package com.demo.cache.persistant.strategy;

import com.demo.cache.persistant.PersistanceStore;

public class WriteAroundStrategy<K,V> implements  PersistenceStrategy<K,V> {

	private PersistanceStore<K, V> store;
	
	public WriteAroundStrategy(PersistanceStore<K, V> store) {
		this.store = store;
	}
	
	public boolean persist( K key, V val) {
		store.writeToStore(key, val);
		return true;
	}

}
