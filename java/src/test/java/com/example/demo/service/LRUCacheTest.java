package com.example.demo.service;

import com.example.demo.model.URL;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    private LRUCache lruCache;

    @BeforeEach
    public void setUp() throws Exception {
        lruCache = new LRUCache(2L);
    }

    @AfterEach
    public void clear() {
        lruCache = null;
    }

    @Test
    void put() {
        URL url = new URL();
        url.setId(1);
        url.setShortURL("b");
        url.setOriginalURL("https://zh.wikipedia.org/");
        lruCache.put(1L, url);
        assertThat(lruCache.get(1L).getId(), equalTo(1));
    }

    @Test
    void get() {
        URL url = new URL();
        url.setId(1);
        url.setShortURL("b");
        url.setOriginalURL("https://zh.wikipedia.org/");
        lruCache.put(1L, url);
        assertThat(lruCache.get(1L).getId(), equalTo(1L));
    }

    @Test
    public void getIdByOriginalURL(){
        String originalURL = null;
        assertThat(lruCache.getIdByOriginalURL(originalURL), equalTo(Long.MIN_VALUE));

        URL url = new URL();
        url.setId(1);
        url.setShortURL("b");
        url.setOriginalURL("https://zh.wikipedia.org/");
        lruCache.put(1L, url);
        originalURL = "https://zh.wikipedia.org/";
        assertThat(lruCache.getIdByOriginalURL(originalURL), equalTo(1L));
    }

    @Test
    void addLast() {
        URL url1 = new URL();
        url1.setId(1);
        url1.setShortURL("b");
        url1.setOriginalURL("https://zh.wikipedia.org/");
        lruCache.put(1L, url1);
        URL url2 = new URL();
        url2.setId(2);
        url2.setShortURL("c");
        url2.setOriginalURL("https://leetcode-cn.com/");
        lruCache.put(2L, url2);
        assertThat(lruCache.get(2L).getId(), equalTo(2L));
    }

    @Test
    void moveNodeToTail() {
        URL url1 = new URL();
        url1.setId(1);
        url1.setShortURL("b");
        url1.setOriginalURL("https://zh.wikipedia.org/");
        lruCache.put(1L, url1);
        URL url2 = new URL();
        url2.setId(2);
        url2.setShortURL("c");
        url2.setOriginalURL("https://leetcode-cn.com/");
        lruCache.put(2L, url2);
        URL url3 = new URL();
        url3.setId(3);
        url3.setShortURL("d");
        url3.setOriginalURL("https://book.douban.com/");
        lruCache.put(3L, url3);
        URL url4 = new URL();
        url4.setId(2);
        url4.setShortURL("e");
        url4.setOriginalURL("https://www.baidu.com/");
        lruCache.put(2L, url4);
        assertThat(lruCache.get(2L).getShortURL(), equalTo("e"));
    }

    @Test
    void removeHead() {
        URL url1 = new URL();
        url1.setId(1);
        url1.setShortURL("b");
        url1.setOriginalURL("https://zh.wikipedia.org/");
        lruCache.put(1L, url1);
        URL url2 = new URL();
        url2.setId(2);
        url2.setShortURL("c");
        url2.setOriginalURL("https://leetcode-cn.com/");
        lruCache.put(2L, url2);
        URL url3 = new URL();
        url3.setId(3);
        url3.setShortURL("d");
        url3.setOriginalURL("https://book.douban.com/");
        lruCache.put(3L, url3);
        assertThat(lruCache.get(1L), equalTo(null));
    }
}