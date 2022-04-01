package com.zz.generator;

import com.zz.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class AutoIncSeq_8DigitShortUrlGeneratorTest extends BaseTest {

    @Autowired
    private AutoIncSeq_8DigitShortUrlGenerator generator;

    @Test
    public void convertToCode() throws Exception {
        Pattern compile = Pattern.compile("^[a-zA-Z0-9]{8}$");
        String code = generator.convertToCode("http://www.baicom");
        Assert.assertTrue(code != null);
        Matcher matcher = compile.matcher(code);
        Assert.assertTrue(matcher.matches());
    }

    @Test
    public void getIncSeq() throws Exception {
        //测试时间回拨，设置时间位未来时间
        long cur = System.currentTimeMillis();
        ReflectionTestUtils.setField(generator, "lastTime", cur + 100);
        long res = ReflectionTestUtils.invokeMethod(generator, "getIncSeq");
        int workId = (int) ReflectionTestUtils.getField(generator, "workId");
        Assert.assertTrue(workId == 1);
    }

    @Test
    public void getIncSeq_0() throws Exception {
        //测试时间冲突
        long cur = System.currentTimeMillis();
        ReflectionTestUtils.setField(generator, "lastTime", cur);
        long res = ReflectionTestUtils.invokeMethod(generator, "getIncSeq");
        int sameSeq = (int) ReflectionTestUtils.getField(generator, "sameSeq");
        Assert.assertTrue(sameSeq > 0);
    }

    @Test
    public void getIncSeq_1() throws Exception {
        //测试时间冲突达到最大值
        ReflectionTestUtils.setField(generator, "sameSeq", 63);
        long cur = System.currentTimeMillis();
        ReflectionTestUtils.setField(generator, "lastTime", cur);
        long res = ReflectionTestUtils.invokeMethod(generator, "getIncSeq");
        int sameSeq = (int) ReflectionTestUtils.getField(generator, "sameSeq");
        Assert.assertTrue(sameSeq == 0);
    }

    @Test
    public void isValid() {
        boolean valid = generator.isValid("123");
        Assert.assertFalse(valid);
    }

    @Test
    public void isValid_0() {
        boolean valid = generator.isValid("12345678");
        Assert.assertTrue(valid);
    }
}