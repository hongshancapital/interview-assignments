package com.jinblog.shorturl.entry;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AbstractNodeTest {

    @Test
    void getShortUrl() {
        LRUNode aaa = new LRUNode("AAA");
        assertEquals("AAA", aaa.getShortUrl());
    }
}