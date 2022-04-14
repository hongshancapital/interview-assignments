package com.sequoiacap.util;

import org.junit.Assert;
import org.junit.Test;

public class NumericConvertUtilTest {

	@Test
	public void testToOtherNumberSystem() {
		Assert.assertEquals("3d", NumericConvertUtil.toOtherNumberSystem(199, 62));
		Assert.assertEquals("1D0", NumericConvertUtil.toOtherNumberSystem(6262, 62));
		Assert.assertEquals("2lkCB1", NumericConvertUtil.toOtherNumberSystem(Integer.MAX_VALUE, 62));
		Assert.assertEquals("aZl8N0y58M7", NumericConvertUtil.toOtherNumberSystem(Long.MAX_VALUE, 62));
		Assert.assertEquals("4GFfc3", NumericConvertUtil.toOtherNumberSystem(-1, 62));
		Assert.assertEquals("4GFfc2", NumericConvertUtil.toOtherNumberSystem(-2, 62));
	}

	@Test
	public void testToDecimalNumber() {
		Assert.assertEquals(Long.MAX_VALUE, NumericConvertUtil.toDecimalNumber("aZl8N0y58M7", 62));
		Assert.assertEquals(Integer.MAX_VALUE, NumericConvertUtil.toDecimalNumber("2lkCB1", 62));
		Assert.assertEquals(6262, NumericConvertUtil.toDecimalNumber("1D0", 62));
		Assert.assertEquals(199, NumericConvertUtil.toDecimalNumber("3d", 62));
		Assert.assertEquals(11123132, NumericConvertUtil.toDecimalNumber("11123132", 10));
	}

}
