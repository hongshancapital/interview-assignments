package com.jinblog.shorturl.service;

public interface Generator {
    public String generate();
    public boolean validate(String shortUrl);
}
