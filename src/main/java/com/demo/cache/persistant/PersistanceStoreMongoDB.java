package com.demo.cache.persistant;

public class PersistanceStoreMongoDB<K,V>  implements PersistanceStore<K,V>{

	public PersistanceStoreMongoDB() {
		
	}
	@Override
	public boolean writeToStore(K k, V v) {
		System.out.println("Writing to store " + k + " " + v);
		return true;
		
	}

	@Override
	public V readFromStore(K k) {
		System.out.println("Reading cache from store " + k );
		return null;
	} 

}
