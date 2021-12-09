package com.scdt.url.tiny_url;

import com.scdt.url.BaseComponentTest;
import com.scdt.url.tiny_url.model.TinyUrl;
import com.scdt.url.tiny_url.model.TinyUrlStorages;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static com.scdt.url.tiny_url.model.TinyUrlId.tinyUrlId;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TinyUrlStoragesComponentTest extends BaseComponentTest {

    @Autowired
    private TinyUrlStorages tinyUrlStorages;

    @Test
    void testPutTinyUrlStorages() {
        String key1 = "key1";
        String key2 = "key22";
        var value = TinyUrl.create(tinyUrlId(key1), key2);
        tinyUrlStorages.putIfAbsent(key1, value);
        var saved = tinyUrlStorages.get(key1);
        assertEquals(value, saved);
    }

    @Test
    void testGetTinyUrlStorages_Supplemental() {
        String key1 = "key11";
        String key2 = "key22";
        var value = TinyUrl.create(tinyUrlId(key1), key2);
        tinyUrlStorages.putIfAbsent(key1, value);
        var saved = tinyUrlStorages.getSupplemental(key2);
        assertEquals(value, saved);
    }
}