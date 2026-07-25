package com.bolord.shorturl.storage;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.bolord.shorturl.config.ShortUrlProperties;

class SimpleUrlMappingStorageTest {

    private long maxCacheSize = 500;

    SimpleUrlMappingStorage init() {
        ShortUrlProperties properties = new ShortUrlProperties();
        properties.setMaxCacheSize(maxCacheSize);
        SimpleUrlMappingStorage storage = new SimpleUrlMappingStorage(properties);
        storage.init();

        return storage;
    }

    @Test
    void get() {
        SimpleUrlMappingStorage storage = init();

        assertThrows(NullPointerException.class, () -> storage.get(null));
        assertNull(storage.get(""));
        assertNull(storage.get("test"));
        assertEquals(0, storage.size());

        for (int i = 0; i < maxCacheSize; i++) {
            String key   = "testKey" + i;
            String value = "testValue" + i;
            storage.set(key, value);

            assertEquals(storage.get(key), value);
        }
    }

    @Test
    void set() {
        SimpleUrlMappingStorage storage = init();

        for (int i = 0; i < maxCacheSize; i++) {
            String key   = "testKey" + i;
            String value = "testValue" + i;
            storage.set(key, value);
        }

        assertEquals(storage.get("testKey11"), "testValue11");

        storage.set("testKey11", "updateValue11");
        assertEquals(storage.get("testKey11"), "updateValue11");
    }

    @Test
    void remove() {
        SimpleUrlMappingStorage storage = init();

        assertThrows(NullPointerException.class, () -> storage.remove(null));

        storage.set("testKey1", "testValue1");
        assertEquals(storage.get("testKey1"), "testValue1");

        storage.remove("testKey1");
        assertNull(storage.get("testKey1") );
    }

    @Test
    void size() {
        SimpleUrlMappingStorage storage = init();

        assertEquals(0, storage.size());

        for (int i = 0; i < maxCacheSize; i++) {
            String key   = "testKey" + i;
            String value = "testValue" + i;
            storage.set(key, key);
        }

        assertEquals(maxCacheSize, storage.size());

        for (int i = 0; i < maxCacheSize / 2; i++) {
            storage.set("overloadKey" + i, "overloadValue" + i);
        }

        try {
            // 缓存可能会暂时超过最大阈值，这里设置清理等待时间
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            assertEquals(maxCacheSize, storage.size());
        }

    }

}
