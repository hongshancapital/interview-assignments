/**
 * 
 */
package com.leo.utils;



import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author LeoZhang
 *
 */

public class ShortHashTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link com.leo.utils.ShortHash#toShortHash(java.lang.String)}.
	 */
	@Test
	public void testToShortHash() {
		Assert.assertEquals(ShortHash.toShortHash("").length(), 7);
		Assert.assertEquals(ShortHash.toShortHash("a").length(), 7);
		Assert.assertEquals(ShortHash.toShortHash("abc").length(), 7);
		Assert.assertEquals(ShortHash.toShortHash("http://www.baidu.com").length(), 7);
		Assert.assertEquals(ShortHash.toShortHash("https://github.com/zdcin/short_url/blob/master/shorturl.py").length(), 7);
	}

	
//	
//	public static void main(String[] args) {
//		HashCode hashCode = Hashing.murmur3_128().hashString("hello world", StandardCharsets.UTF_8);
//		// BigInteger
//		System.out.println(hashCode.toString());
//
////		System.out.print(toShortHash(bytes));
//		
//		long now = System.currentTimeMillis();
//		int total =8000 * 10000;
//		
////		hit=869
////				use time of ms: 227833
//		//md5
////		hit=920hit=920
////				use time of ms: 244582
//		int hit = 0;
//		Map<String, Integer> set = new HashMap<>(total);
//		for (int i = 0; i < total; i++) {
//			String s = "http://www.baidu.com/abc/"+i+".jpg";
//			String hash = ShortHash.toShortHash(s);
//			if (set.containsKey(hash)) {
//				hit ++;
//				set.put(hash, set.get(hash)  + 1);
//			} else {
//				set.put(hash, 1);
//			}
//			
//			if (i % 1000 == 0) {
//				System.out.println("hit="+hit + ", index = " + i);
//			}
//		}
//		System.out.println("hit="+hit);
//		System.out.println("use time of ms: " +( System.currentTimeMillis() - now) );
//		
//		for (Entry<String, Integer> e : set.entrySet()) {
//			if (e.getValue() > 1) {
//				if (e.getValue() > 20) {
//					System.out.print("===============");
//				}
//				System.out.println(e.getValue());
//				
//			}
//		}
//	}
}
