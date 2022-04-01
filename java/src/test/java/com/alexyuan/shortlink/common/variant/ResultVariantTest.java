package com.alexyuan.shortlink.common.variant;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ResultVariantTest {

    @Test
    public void testGeneration() {
        String cur_time = String.valueOf(System.currentTimeMillis());
        ResultVariant resultVariant = new ResultVariant("000001"
                , "http://alexkxyuan.test", cur_time, "No extra Message");
        assertThat(resultVariant.getResponse_code()).isEqualTo("200");
        assertThat(resultVariant.getShort_url()).isEqualTo("000001");
        assertThat(resultVariant.getLong_url()).isEqualTo("http://alexkxyuan.test");
        assertThat(resultVariant.getGenerate_time()).isEqualTo(cur_time);
        assertThat(resultVariant.getExtra_message()).isEqualTo("No extra Message");
        assertThat(resultVariant.getStatus()).isEqualTo("Success");
    }

    @Test
    public void testGenerationStatus() {
        String cur_time = String.valueOf(System.currentTimeMillis());
        ResultVariant resultVariant = new ResultVariant("500", "000001"
                , "http://alexkxyuan.test", cur_time, "Error Message: xxx", "Failed");
        assertThat(resultVariant.getResponse_code()).isEqualTo("500");
        assertThat(resultVariant.getShort_url()).isEqualTo("000001");
        assertThat(resultVariant.getLong_url()).isEqualTo("http://alexkxyuan.test");
        assertThat(resultVariant.getGenerate_time()).isEqualTo(cur_time);
        assertThat(resultVariant.getExtra_message()).isEqualTo("Error Message: xxx");
        assertThat(resultVariant.getStatus()).isEqualTo("Failed");
    }
}
