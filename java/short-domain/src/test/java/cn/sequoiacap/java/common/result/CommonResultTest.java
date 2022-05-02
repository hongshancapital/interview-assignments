package cn.sequoiacap.java.common.result;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CommonResultTest extends TestCase {
	@Test
	public void testCommonResult() {
		CommonResult result = new CommonResult(404, "NOT_FOUND", null);
		Assert.assertEquals(result.getMessage(), "NOT_FOUND");
		result.setData("abcde");
		Assert.assertEquals((String) result.getData(), "abcde");
		result.setTimestamp(1111111111);
		Assert.assertEquals(result.getTimestamp(), 1111111111L);
	}

	@Test
	public void testFailed() {
		CommonResult result = CommonResult.failed();
		Assert.assertEquals(result.getCode(), 500);
	}

	@Test
	public void testFailed02() {
		CommonResult result = CommonResult.failed("SYSTEM_ERROR");
		Assert.assertEquals(result.getMessage(), "SYSTEM_ERROR");
	}

}


