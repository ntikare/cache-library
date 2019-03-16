package com.demo.cache;

import com.demo.cache.persistant.PersistanceStore;
import com.demo.cache.persistant.strategy.PersistenceStrategy;

public class PersistentCache<K, V> implements Cache<K, V> {

	private Cache<K, V> memStore;
	private PersistenceStrategy<K, V> strategy;
	private PersistanceStore<K, V> store;

	PersistentCache(Cache<K, V> memStore, PersistenceStrategy<K, V> strategy, PersistanceStore<K, V> store) {
		this.memStore = memStore;
		this.store = store;
		this.strategy = strategy;
	}

	public boolean set(K key, V value) {
		memStore.set(key, value);
		return strategy.persist(key, value);

	}

	public V get(K key) {
		V val = memStore.get(key);
		if (val == null) {
			val = store.readFromStore(key);
			if (val != null) {
				memStore.set(key, val);
			}
		}
		return val;
	}

}
