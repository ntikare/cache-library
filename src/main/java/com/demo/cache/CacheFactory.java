package com.demo.cache;

import com.demo.cache.persistant.PersistanceStore;
import com.demo.cache.persistant.PersistanceStoreMongoDB;
import com.demo.cache.persistant.strategy.PersistenceStrategy;
import com.demo.cache.persistant.strategy.WriteAroundStrategy;
import com.demo.cache.persistant.strategy.WriteBackStrategy;

public class CacheFactory {

	 public static <K,V> Cache<K, V>  getCacheInstance(int capacity, CacheEvictionStragegy cacheEvictionStragegy,
			CachePersistanceStrategy cachePersistanceStrategy) throws Exception {
		 
		 Cache<K, V> memstore = getMemStore(capacity, cacheEvictionStragegy);
		 PersistanceStore<K, V> dbStore = new PersistanceStoreMongoDB<K, V>();
		 PersistenceStrategy<K, V> strategy = getPersistenceStrategy(cachePersistanceStrategy, dbStore);
		 
		 
		 if (strategy == null || memstore == null) {
			 throw new Exception("Cache not supported");
		 }
		 return new PersistentCache<K, V>(memstore, strategy, dbStore);

	}
	 
	private static <K,V>  Cache<K, V>  getMemStore(int capacity,CacheEvictionStragegy strategyType ){
		 Cache<K, V> memstore;
		 if (strategyType == CacheEvictionStragegy.LRU) {
			 memstore = new LRUCache<K, V>(capacity);
		 } else {
			 memstore = null;
		 }
		 return memstore;
	}
	 
	private static <K,V>  PersistenceStrategy<K, V> getPersistenceStrategy(CachePersistanceStrategy strategyType, PersistanceStore<K, V> dbStore) {
		PersistenceStrategy<K,V> strategy ;
		 if (strategyType == CachePersistanceStrategy.WRITE_AROUND) {
			 strategy = new WriteAroundStrategy<>(dbStore);
		 }  else if (strategyType == CachePersistanceStrategy.WRITE_BACK) {
			 strategy = new WriteBackStrategy<>(dbStore);
		 } else {
			 strategy = null;
		 }
		 return strategy;
		
	}

}
