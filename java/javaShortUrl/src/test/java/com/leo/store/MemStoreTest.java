package com.leo.store;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class MemStoreTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		String url1= "http://www.baidu.com";
		String url2 = "http://www.163.com";
		String hash1 = MemStore.store("1234567", url1);
		String hash2 = MemStore.store("1234567", url2);
		
		Assert.assertEquals(url1, MemStore.queryByShort(hash1));
		Assert.assertEquals(url2, MemStore.queryByShort(hash2));

		
		String hash63 = null;
		for (int i = 0; i < 62; i++) {
			hash63 = MemStore.store("1234567", url2+i);
		}
		Assert.assertNull(hash63);
	}

//	public static void main2(String[] args) {
//		 ConcurrentMap<String, Integer> testMap = new MapMaker().initialCapacity(80*10000).weakKeys().weakValues().makeMap();
//		 testMap.put("1", 11);
//		 
//		 System.out.println(testMap.size());
//		 System.out.println(testMap.get("1"));
//		 System.out.println(testMap.getOrDefault("1",2));
//	}
//	public static void main(String[] args) {
////		 ConcurrentMap<String, List<String>> testMap = new MapMaker().initialCapacity(80*10000).weakKeys().makeMap();
//		ConcurrentMap<String, SoftReference<List<String>>> testMap = new ConcurrentHashMap<>();
//		 for (int i = 0; i < 10000 * 10000 * 3; i ++) {
//			 testMap.put("" + i, new SoftReference( new ArrayList<String>()));
//			 if (i % 1000 == 0) {
//				 System.out.println(testMap.size());
//				 List<String> removeList = new ArrayList<>(1000);
//				 for (Entry<String, SoftReference<List<String>>> e : testMap.entrySet()) {
//					 if (e.getValue() == null || e.getValue().get() == null) {
//						 removeList.add(e.getKey());
//					 }
//				 }
//				 if (!removeList.isEmpty()) {
//				 System.out.println("remove: " + removeList.size());
//				 for (String del : removeList) {
//					 testMap.remove(del);
//				 }
//				 }
//			 }
//		 }
//	}
}
