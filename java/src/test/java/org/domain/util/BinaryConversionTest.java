package org.domain.util;

import org.domain.SpringbootUnitTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BinaryConversionTest extends SpringbootUnitTest {
    @Test
    public void testTenToSixtyTwo() {
        Assert.assertSame("转换错误", 'a', BinaryConversion.tenToSixtyTwo(1));
    }
}
