package com.getao.urlconverter.util;

import com.getao.urlconverter.dto.generator.NumGeneratorPool;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ConverterUtilTest {

    @Test
    public void testEncode10to64() {
        long num = NumGeneratorPool.generatorMaxSize;
        assertThat(num).isNotNull();
        assertThat(num).isEqualTo(3521614606207L);
        assertThat(ConverterUtil.encode(62, 1)).isEqualTo("ba");
    }

    @Test
    public void testDecode64To10() {
        String str = "9999999";
        assertThat(ConverterUtil.decode(str)).isNotNull();
        assertThat(ConverterUtil.decode(str)).isEqualTo(3521614606207L);
        System.out.println(ConverterUtil.decode(str));
    }
}
