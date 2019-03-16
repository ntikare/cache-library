package com.demo.cache;

public class TestCache {

	public static void main(String [] args) {
		try {
			Cache<String, Integer> cache = CacheFactory.getCacheInstance(1, CacheEvictionStragegy.LRU, CachePersistanceStrategy.WRITE_BACK);
			cache.set("test" , 1);
			cache.set("nawa" , 2);

			System.out.println(cache.get("nawa"));
			System.out.println(cache.get("test"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
