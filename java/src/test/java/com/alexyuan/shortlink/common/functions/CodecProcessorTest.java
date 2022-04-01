package com.alexyuan.shortlink.common.functions;

import com.alexyuan.shortlink.exceptions.ImproperValueException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CodecProcessorTest {

    @Test
    public void testNormalFunctionWorking() {
        Long origin_val = 10L;
        int target_length = 6;
        int scale = 62;
        String generate_code = CodecProcessor.encodeFromDecimal(origin_val, target_length, scale);
        assertThat(generate_code).isEqualTo("00000A");
    }

    @Test(expected = ImproperValueException.class)
    public void  testNormalFunctionNotWorking(){
        Long origin_val = 10L;
        int target_length = 6;
        int scale = 1;
        CodecProcessor.encodeFromDecimal(origin_val, target_length, scale);
    }

    @Test(expected = ImproperValueException.class)
    public void  testNormalFunctionNotWorkingV2(){
        Long origin_val = -1L;
        int target_length = -1;
        int scale = 6;
        CodecProcessor.encodeFromDecimal(origin_val, target_length, scale);
    }
}
