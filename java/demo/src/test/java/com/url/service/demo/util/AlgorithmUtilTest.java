package com.url.service.demo.util;

import com.url.service.demo.constant.UrlServiceConstant;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import static org.junit.Assert.*;

public class AlgorithmUtilTest {

	private final String FULL_URL = "https://www.baidu.com";

	@Test
	public void md5Url() {
		Assert.assertEquals(4, AlgorithmUtil.md5Url(FULL_URL).length);
		for (String s : AlgorithmUtil.md5Url("")) {
			System.out.println(s);
		}
	}

	@Test
	public void randomUrl() {
		Assert.assertNotEquals(AlgorithmUtil.randomUrl(), AlgorithmUtil.randomUrl());
	}
}