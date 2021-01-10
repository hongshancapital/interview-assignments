package com.bruce.shorturl.util;

//import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

//import org.junit.runner.RunWith;

//@RunWith(SpringRunner.class)
@SpringBootTest
class MathUtilsTest {

	/**
	 * 测试输入值0
	 */
	@Test void _10_to_spec_1() {

		long num = 0l;
		String result = MathUtils._10_to_spec(num);
		Assertions.assertEquals(result, "");
	}

	/**
	 * 测试输入值0
	 */
	@Test void _10_to_spec_2() {

		long num = 1l;
		String result = MathUtils._10_to_spec(num);
		Assertions.assertEquals(result, "1");
	}

	/**
	 * 测试绝对值相同的正负数
	 */
	@Test void _10_to_spec_3() {
		long num = Integer.MAX_VALUE;
		long num2= 0-Integer.MAX_VALUE;
		String result1 = MathUtils._10_to_spec(num);
		String result2 = MathUtils._10_to_spec(num2);
		Assertions.assertEquals(result1, result2);
	}

//	/**
//	 * 测试绝对值相同的正负数
//	 */
//	@Test void _10_to_spec_4() {
//		long num = Long.MAX_VALUE;
//		String result = MathUtils._10_to_spec(num);
//		Assert.assertEquals(result, "");
//	}



}