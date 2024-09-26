package com.jinblog.shorturl.service.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StorageTrieTest {

    StorageTrie urlMapTrie = new StorageTrie();
    @Test
    void save() {
        String shortUrl = "su1";
        String longUrl = "lu1";
        assertNull(urlMapTrie.find(shortUrl));
        urlMapTrie.save(longUrl, shortUrl);
        assertEquals(longUrl, urlMapTrie.find(shortUrl));
        urlMapTrie.delete(shortUrl);
    }

    @Test
    void find() {
        String shortUrl = "su1";
        String longUrl = "lu1";
        assertNull(urlMapTrie.find(shortUrl));
        urlMapTrie.save(longUrl, shortUrl);
        assertEquals(longUrl, urlMapTrie.find(shortUrl));
        urlMapTrie.delete(shortUrl);
        assertNull(urlMapTrie.find(shortUrl));
        assertNull(urlMapTrie.find("xxx"));
    }

    @Test
    void delete() {
        String shortUrl = "su1";
        String longUrl = "lu1";
        assertNull(urlMapTrie.find(shortUrl));
        urlMapTrie.save(longUrl, shortUrl);
        assertEquals(longUrl, urlMapTrie.find(shortUrl));
        urlMapTrie.delete(shortUrl);
        assertNull(urlMapTrie.find(shortUrl));
        assertNull(urlMapTrie.find("xxx"));

        // 插入多个
        String shortUrl2 = "su2";
        String longUrl2 = "lu2";
        assertNull(urlMapTrie.find(shortUrl2));
        urlMapTrie.save(longUrl2, shortUrl2);
        assertEquals(longUrl2, urlMapTrie.find(shortUrl2));

        String shortUrl3 = "su3";
        String longUrl3 = "lu3";
        assertNull(urlMapTrie.find(shortUrl3));
        urlMapTrie.save(longUrl3, shortUrl3);
        assertEquals(longUrl3, urlMapTrie.find(shortUrl3));

        urlMapTrie.delete(shortUrl2);
        assertNull(urlMapTrie.find(shortUrl2));

        urlMapTrie.delete(shortUrl3);
        assertNull(urlMapTrie.find(shortUrl3));
    }
}