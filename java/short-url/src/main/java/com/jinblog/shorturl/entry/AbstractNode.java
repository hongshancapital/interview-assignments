package com.jinblog.shorturl.entry;

/**
 * 回收元素基类
 */
public class AbstractNode {
    private final String shortUrl;
    public AbstractNode(String shortUrl) {
        this.shortUrl = shortUrl;
    }
    public String getShortUrl() {
        return shortUrl;
    }
}
