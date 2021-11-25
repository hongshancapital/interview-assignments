package net.sky.demo.url.mapping.store;

import org.junit.Assert;
import org.junit.Test;

public class Long2ShortMappingStoreTest {


    @Test
    public void queryBySourceUrl() {
        Long2ShortMappingStore long2ShortMappingStore = new Long2ShortMappingStore();
        try {
            long2ShortMappingStore.queryBySourceUrl(null);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertTrue(e.getMessage().equals("input url can not be null"));
        }
        try {
            long2ShortMappingStore.queryBySourceUrl(" ");
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertTrue(e.getMessage().equals("input url can not be null"));
        }
        String url = "http://www.baidu.com/" + System.currentTimeMillis();
        long number = 1;
        long2ShortMappingStore.insertNewMapping(url, number);
        Assert.assertTrue(long2ShortMappingStore.queryBySourceUrl(url) == number);
    }

    @Test
    public void insertNewMapping() {
        Long2ShortMappingStore long2ShortMappingStore = new Long2ShortMappingStore();


        try {
            long2ShortMappingStore.insertNewMapping("23",-1L);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertTrue(e.getMessage().equals("value can not null or negative"));
        }

        try {
            long2ShortMappingStore.insertNewMapping(" ",100L);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertTrue(e.getMessage().equals("input url can not be null"));
        }

        try {
            String url = "http://www.baidu.com/" + System.currentTimeMillis();
            long number = 1;
            long2ShortMappingStore.insertNewMapping(url, number);
            Assert.assertTrue(long2ShortMappingStore.queryBySourceUrl(url) == number);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }

    }
}