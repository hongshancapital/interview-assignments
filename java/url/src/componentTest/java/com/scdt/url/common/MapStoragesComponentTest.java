package com.scdt.url.common;

import com.scdt.url.BaseComponentTest;
import com.scdt.url.common.util.MapStorages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MapStoragesComponentTest extends BaseComponentTest {

    @Autowired
    private MapStorages<String, String, String, Function<String, String>> storages;

    @Test
    void testPutStorage_NoSupplemental() {
        String key1 = "key1";
        var value = "value1";
        storages.putIfAbsent(key1, value);
        var saved = storages.get(key1);
        assertEquals(value, saved);
    }
}