package scdt.interview.test.common.algorithm;

import org.junit.Assert;
import org.junit.Test;

import scdt.interview.java.common.algorithm.Numberic;

public class NumbericTest {

	@Test
	public void Rad10_16Test() {
		Assert.assertEquals("进制转换", "a", Numberic.getRadStr(10, 16, false));
	}

	@Test
	public void Rad17_16Test() {
		Assert.assertEquals("进制转换", "11", Numberic.getRadStr(17, 16, false));
	}

	@Test
	public void Rad124_62Test() {
		Assert.assertEquals("进制转换", "20", Numberic.getRadStr(124, 62, false));
	}

	@Test
	public void Rad8_2Test() {
		Assert.assertEquals("进制转换", "1000", Numberic.getRadStr(8, 2, false));
	}
}