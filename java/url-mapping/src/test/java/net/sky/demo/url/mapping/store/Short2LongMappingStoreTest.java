package net.sky.demo.url.mapping.store;

import org.junit.Assert;
import org.junit.Test;

public class Short2LongMappingStoreTest {

    @Test
    public void getSourceUrlByNumber() {
        Short2LongMappingStore short2LongMappingStore = new Short2LongMappingStore();
        long maxSize = 10L;
        short2LongMappingStore.setMaxStoreSize(maxSize);
        short2LongMappingStore.setMaxSingleListSize((int) (maxSize / 2));
        short2LongMappingStore.setDefaultSingleListInitSize(2);
        short2LongMappingStore.setShortStringMaxLength(8);


        Assert.assertNull(short2LongMappingStore.getSourceUrlByNumber(5L));


        for (int i = 0; i < maxSize; i++) {
            String url = String.valueOf(System.currentTimeMillis());
            long number = short2LongMappingStore.generateNumber(url);
            Assert.assertTrue(short2LongMappingStore.getSourceUrlByNumber(number).equals(url));
        }

        try {
            short2LongMappingStore.getSourceUrlByNumber(-1L);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertTrue(e.getMessage().equals("number can not be null and must be positive"));
        }

        try {
            short2LongMappingStore.getSourceUrlByNumber(maxSize + 1L);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertTrue(e.getMessage().equals("number bigger than maxStoreSize:" + maxSize));
        }
    }

    @Test
    public void generateNumber() {
        Short2LongMappingStore short2LongMappingStore = new Short2LongMappingStore();
        long maxSize = 10L;
        short2LongMappingStore.setMaxStoreSize(maxSize);
        short2LongMappingStore.setMaxSingleListSize((int) (maxSize / 2));
        short2LongMappingStore.setDefaultSingleListInitSize(2);
        short2LongMappingStore.setShortStringMaxLength(8);


        for (int i = 0; i < maxSize; i++) {
            String url = String.valueOf(System.currentTimeMillis());
            long number = short2LongMappingStore.generateNumber(url);
            Assert.assertTrue(short2LongMappingStore.getSourceUrlByNumber(number).equals(url));
        }

        String url = String.valueOf(Long.MAX_VALUE);
        try {
            short2LongMappingStore.generateNumber(url);
        } catch (Exception e) {
            Assert.assertTrue(e instanceof IllegalArgumentException);
            Assert.assertTrue(e.getMessage().equals("store has full"));
        }

    }
}