package com.example.shorturl.vo;

public class ShortURLNotFoundException extends BusinessException {

    public ShortURLNotFoundException(String surl) {
        super("找不到该短链接 " + surl);
    }

}