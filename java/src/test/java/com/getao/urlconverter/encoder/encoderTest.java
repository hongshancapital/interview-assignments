package com.getao.urlconverter.encoder;

import com.getao.urlconverter.dto.generator.NumGeneratorPool;
import com.getao.urlconverter.util.ConverterUtil;
import org.junit.jupiter.api.Test;

public class encoderTest {

    @Test
    public void testEncode10to64() {
       long num = NumGeneratorPool.generatorMaxSize;
        System.out.println(ConverterUtil.encode(62, 1));
    }

    @Test
    public void testDecode64To10() {
        String str = "9999999";
        System.out.println(ConverterUtil.decode(str));
    }
}
