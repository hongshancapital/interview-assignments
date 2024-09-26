package org.example.config;


public class URLNode {

    public URLNode(String longUrl, String shortUrl){
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public String longUrl = null;
    public String shortUrl = null;
    public URLNode pre = null;
    public URLNode next = null;
}
