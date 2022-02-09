package flypig.url.mapping.util;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.fail;

public class Long2StringUtilTest {

    @Test
    public void long2String() {
        try {
            Long2StringUtil.long2String(-1000L);
            fail("should throw exception but not");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
        }

        //normal
        long number = 1234656L;
        String s = Long2StringUtil.long2String(number);
        Assert.assertTrue(Long2StringUtil.string2long(s) == number);
    }

    @Test
    public void string2long() {

        String s = "6BBo";
        long number = Long2StringUtil.string2long(s);
        Assert.assertTrue(Long2StringUtil.long2String(number).equals(s));


    }
}