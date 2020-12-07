package com.bruce.shorturl.storage.impl;

import com.bruce.shorturl.data.TestData;
import com.bruce.shorturl.storage.IShortUrlStorage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//import org.junit.Assert;
//import org.junit.runner.RunWith;

/**
 * storage testcase
 */
//@RunWith(SpringRunner.class)
@SpringBootTest
public class ShortUrlRedisStorageImplTest {

	@Autowired
	private IShortUrlStorage shortUrlStorage;


	/**
	 * setup
	 */
	@BeforeEach void setup() {
		shortUrlStorage.clearAll();
	}

	/**
	 * 测试是否存在指定的hash
	 */
	@Test void remove1() {
		shortUrlStorage.remove(TestData.DEFAULT_HASH_KEY);
		Assertions.assertTrue(true);
	}


	/**
	 * 测试是否存在指定的hash
	 */
	@Test void notExists0() {
		boolean notExists = shortUrlStorage.notExists(null);
		Assertions.assertTrue(notExists);
	}

	/**
	 * 测试是否存在指定的hash
	 */
	@Test void notExists1() {
		shortUrlStorage.remove(TestData.DEFAULT_HASH_KEY);

		boolean notExists = shortUrlStorage.notExists(TestData.DEFAULT_HASH_KEY);
		Assertions.assertTrue(notExists);
	}

	/**
	 * 添加完，在测试是否存在指定的hash
	 */
	@Test void notExists2() {
		shortUrlStorage.remove(TestData.DEFAULT_HASH_KEY);

		boolean notExists = shortUrlStorage.notExists(TestData.DEFAULT_HASH_KEY);
		Assertions.assertTrue(notExists, "hash已经存在");

		shortUrlStorage.putValueByHash(TestData.DEFAULT_HASH_KEY, TestData.DEFAULT_HASH_VALUE);
		boolean notExists2 = shortUrlStorage.notExists(TestData.DEFAULT_HASH_KEY);
		Assertions.assertTrue(!notExists2, "hash不存在");
	}

	/**
	 * 测试空串的情况
	 */
	@Test void loadValueByHash1() {

		String fullUrl = shortUrlStorage.loadValueByHash(null);
		Assertions.assertTrue(fullUrl==null);

		fullUrl = shortUrlStorage.loadValueByHash("");
		Assertions.assertTrue(fullUrl==null);

		fullUrl = shortUrlStorage.loadValueByHash(" ");
		Assertions.assertTrue(fullUrl==null);
	}


	@Test void loadValueByHash2() {

		shortUrlStorage.remove(TestData.DEFAULT_HASH_KEY);


		shortUrlStorage.putValueByHash(TestData.DEFAULT_HASH_KEY, TestData.DEFAULT_HASH_VALUE);

		String fullUrl = shortUrlStorage.loadValueByHash(TestData.DEFAULT_HASH_KEY);
		Assertions.assertEquals(TestData.DEFAULT_HASH_VALUE, fullUrl);

		shortUrlStorage.remove(TestData.DEFAULT_HASH_KEY);
	}


	@Test
	public void putValueByHash1() {
		shortUrlStorage.putValueByHash(TestData.DEFAULT_HASH_KEY, TestData.DEFAULT_HASH_VALUE);
		boolean notExists = shortUrlStorage.notExists(TestData.DEFAULT_HASH_KEY);
		Assertions.assertFalse(notExists);
	}

	@Test public void putValueByHash2() {
		String result = shortUrlStorage.putValueByHash(null, TestData.DEFAULT_HASH_VALUE);
		Assertions.assertNull(result);
	}

	@Test public void putValueByHash3() {
		String result = shortUrlStorage.putValueByHash(TestData.DEFAULT_HASH_KEY, null);
		Assertions.assertNull(result);
	}

	/**
	 * 先后两次进行put
	 */
	@Test
	public  void putValueByHash4() {
		shortUrlStorage.putValueByHash(TestData.DEFAULT_HASH_KEY, TestData.DEFAULT_HASH_VALUE);
		String fullUrl = shortUrlStorage.loadValueByHash(TestData.DEFAULT_HASH_KEY);
		Assertions.assertEquals(TestData.DEFAULT_HASH_VALUE, fullUrl);

		shortUrlStorage.putValueByHash(TestData.DEFAULT_HASH_KEY, TestData.DEFAULT_HASH_VALUE_2);
		fullUrl = shortUrlStorage.loadValueByHash(TestData.DEFAULT_HASH_KEY);
		Assertions.assertEquals(TestData.DEFAULT_HASH_VALUE_2, fullUrl);

		shortUrlStorage.remove(TestData.DEFAULT_HASH_KEY);

	}


}