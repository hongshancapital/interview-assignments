package com.scdt.url.tiny_url;

import com.scdt.url.BaseComponentTest;
import com.scdt.url.tiny_url.model.TinyUrlId;
import com.scdt.url.tiny_url.model.TinyUrlIdGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.*;


class TinyUrlIdGeneratorComponentTest extends BaseComponentTest {

    @Autowired
    private TinyUrlIdGenerator tinyUrlIdGenerator;

    @Test
    void testCreateSameTinyUrlId() {
        var originalUrl = "http://asdsa.test.com/asdadsadsasd";
        TinyUrlId tinyUrlId1 = tinyUrlIdGenerator.generate(originalUrl);
        TinyUrlId tinyUrlId2 = tinyUrlIdGenerator.generate(originalUrl);
        assertFalse(tinyUrlId1.equals("null"));
        assertFalse(tinyUrlId1.equals(null));
        assertTrue(tinyUrlId1.hashCode() == tinyUrlId2.hashCode());
        assertEquals(tinyUrlId1, tinyUrlId2);
    }

    @Test
    void testCreateDifTinyUrlId() {
        var originalUrl1 = "http://asdsa.test.com/asdadsadsasd1";
        var originalUrl2 = "http://asdsa.test.com/asdadsadsasd2";
        TinyUrlId tinyUrlId1 = tinyUrlIdGenerator.generate(originalUrl1);
        TinyUrlId tinyUrlId2 = tinyUrlIdGenerator.generate(originalUrl2);
        assertNotEquals(tinyUrlId1, tinyUrlId2);
    }
}