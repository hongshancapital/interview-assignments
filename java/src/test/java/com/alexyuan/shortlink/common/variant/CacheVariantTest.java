package com.alexyuan.shortlink.common.variant;

import com.sun.source.tree.AssertTree;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class CacheVariantTest {

    @Test
    public void testGeneration() {
        String cur_time = String.valueOf(System.currentTimeMillis());
        CacheVariant cacheVariant = new CacheVariant("000001", "000001"
                , "http://alexkxyuan.test", cur_time);
        assertThat(cacheVariant.getUniq_code()).isEqualTo("000001");
        assertThat(cacheVariant.getShort_url()).isEqualTo("000001");
        assertThat(cacheVariant.getLong_url()).isEqualTo("http://alexkxyuan.test");
        assertThat(cacheVariant.getGenerate_time()).isEqualTo(cur_time);
    }

    @Test
    public void testGenerationEquals() {
        String cur_time = String.valueOf(System.currentTimeMillis());
        CacheVariant cacheVariant = new CacheVariant("000001", "000001"
                , "http://alexkxyuan.test", cur_time);
        CacheVariant diffVariant = new CacheVariant("000001", "000001"
                , "http://alexkxyuan.test", cur_time);
        Assert.assertEquals(cacheVariant, diffVariant);
        Assert.assertEquals(cacheVariant.hashCode(), diffVariant.hashCode());
        Assert.assertEquals(cacheVariant.toString(), diffVariant.toString());
        diffVariant.setShort_url("test2");
        Assert.assertNotEquals(cacheVariant, diffVariant);
        Assert.assertNotEquals(cacheVariant.hashCode(), diffVariant.hashCode());
        Assert.assertNotEquals(cacheVariant.toString(), diffVariant.toString());
    }
}
