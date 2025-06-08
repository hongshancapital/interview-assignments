package com.liuhuachao.shorturl.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * ConvertUtil 单元测试
 * @author <liuhuachao>
 * @version 1.0
 * @since <pre>12/20/2021</pre>
 */
public class ConvertUtilTest {

	@Before
	public void before() throws Exception {
	}

	@After
	public void after() throws Exception {
	}

	/**
	 * Method: convertTo62String(long num)
	 */
	@Test
	public void testConvertTo62String() throws Exception {
		long num = 64L;
		String expectedStr = "12";
		String actualStr = ConvertUtil.convertTo62String(num);
		Assert.assertEquals(expectedStr,actualStr);
	}

	/**
	 * Method: convertToDecimalNumber(String str)
	 */
	@Test
	public void testConvertToDecimalNumber() throws Exception {
		long expectedNum = 64L;
		String actualSir = ConvertUtil.convertTo62String(64);
		long actualNum = ConvertUtil.convertToDecimalNumber(actualSir);
		Assert.assertEquals(expectedNum,actualNum);
	}

} 
