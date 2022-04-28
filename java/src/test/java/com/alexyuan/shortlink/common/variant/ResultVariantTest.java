package com.alexyuan.shortlink.common.variant;

import org.junit.Assert;
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

    @Test
    public void testGenerationStatusV2() {
        String cur_time = String.valueOf(System.currentTimeMillis());
        ResultVariant resultVariant = new ResultVariant("400", "000001"
                , "http://alexkxyuan.test", cur_time, "Error Message: xxx", "Failed");
        Assert.assertEquals(resultVariant, new ResultVariant("400", "000001"
                , "http://alexkxyuan.test", cur_time, "Error Message: xxx", "Failed"));
        Assert.assertNotEquals(resultVariant, new ResultVariant("500", "000001"
                , "http://alexkxyuan.test", cur_time, "Error Message: xxx", "Failed"));
        Assert.assertEquals(resultVariant.getResponse_code(), "400");
        Assert.assertEquals(resultVariant.getShort_url(),"000001");
        Assert.assertEquals(resultVariant.getLong_url(),"http://alexkxyuan.test");
        Assert.assertEquals(resultVariant.getGenerate_time(),cur_time);
        Assert.assertEquals(resultVariant.getExtra_message(),"Error Message: xxx");
        Assert.assertEquals(resultVariant.getStatus(),"Failed");
    }

    @Test
    public void testGenerationEquals() {
        String cur_time = String.valueOf(System.currentTimeMillis());
        ResultVariant resultVariant = new ResultVariant("400", "000001"
                , "http://alexkxyuan.test", cur_time, "Error Message: xxx", "Failed");
        ResultVariant diffVariant = new ResultVariant("400", "000001"
                , "http://alexkxyuan.test", cur_time, "Error Message: xxx", "Failed");
        Assert.assertEquals(resultVariant.hashCode(), diffVariant.hashCode());
        Assert.assertEquals(resultVariant, diffVariant);
        Assert.assertEquals(resultVariant.toString(), diffVariant.toString());
        diffVariant.setStatus("500");
        Assert.assertNotEquals(resultVariant.hashCode(), diffVariant.hashCode());
        Assert.assertNotEquals(resultVariant, diffVariant);
        Assert.assertNotEquals(resultVariant.toString(), diffVariant.toString());
    }
}
