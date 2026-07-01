package cn.sequoiacap.java.common.utils;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class Base62UtilsTest extends TestCase {
	@Test
	public void testBase62Utils() {
		Base62Utils base62 = new Base62Utils();
		Assert.assertNotNull(base62);
	}

	@Test
	public void testEncodeStr01() {
		String base62 = Base62Utils.encodeStr(62);
		Assert.assertEquals(base62, "j0");
	}

	@Test
	public void testEncodeStr02() {
		String base62 = Base62Utils.encodeStr(0);
		Assert.assertEquals(base62, "0");
	}

	@Test
	public void testEncodeStr03() {
		String base62 = Base62Utils.encodeStr(3845);
		Assert.assertEquals(base62, "j0j");
	}

	@Test
	public void testDecodeNum01() {
		long base10 = Base62Utils.decodeNum("zzzzzzzz");
		Assert.assertEquals(base10, 218340105584895L);
	}

	@Test
	public void testDecodeNum02() {
		long base10 = Base62Utils.decodeNum("a-5");
		Assert.assertEquals(base10, -1L);
	}

	@Test
	public void testDecodeNum03() {
		long base10 = Base62Utils.decodeNum("");
		Assert.assertEquals(base10, -1L);
	}

	@Test
	public void testDecodeNum04() {
		long base10 = Base62Utils.decodeNum(null);
		Assert.assertEquals(base10, -1L);
	}

}


