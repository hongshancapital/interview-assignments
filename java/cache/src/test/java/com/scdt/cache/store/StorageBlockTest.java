package com.scdt.cache.store;


import com.scdt.cache.utils.TestUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

public class StorageBlockTest {

    @Test
    public void test() throws IOException {
        StorageBlock storageBlock = null;
        try {
            storageBlock = new StorageBlock(TestUtil.TEST_DIR, TestUtil.DEFAULT_STORAGE_BLOCK_SIZE);
            String testString = "test String";
            Pointer pointer = storageBlock.store(testString.getBytes());
            byte[] bytes = storageBlock.get(pointer);
            Assert.assertEquals(testString, new String(bytes));

            bytes = storageBlock.remove(pointer);
            Assert.assertEquals(testString, new String(bytes));
        } finally {
            if (storageBlock != null) {
                storageBlock.close();
            }
        }
    }

}