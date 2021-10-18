package com.jerry.domainservice.api.service.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.jerry.domainservice.api.service.util.NumberUtils.InvalidRadixException;

class NumberUtilsTest {

	@Test
	void testConvertWithInvalidRaidxException() {
		Assertions.assertThrows(InvalidRadixException.class, ()->NumberUtils.convert(1238, NumberUtils.MIN_RADIX-1));
		Assertions.assertThrows(InvalidRadixException.class, ()->NumberUtils.convert(1238, NumberUtils.MAX_RADIX+1));
	}
	
	@Test
	void testConvert() {
		Assertions.assertEquals(NumberUtils.convert(128, 2), "10000000");
		Assertions.assertEquals(NumberUtils.convert(124, 62), "20");
		Assertions.assertEquals(NumberUtils.convert(123, 61), "21");
		Assertions.assertEquals(NumberUtils.convert(3843,62), "ZZ");
	}

}
