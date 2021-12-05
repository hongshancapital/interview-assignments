package com.wwwang.assignment.shortenurl.entity;

public class ShortUrl {

    private long id;

    private String content;

    public ShortUrl(long id,String content){
        this.id = id;
        this.content = content;
    }

    public String getContent() {
        return content;
    }
}
