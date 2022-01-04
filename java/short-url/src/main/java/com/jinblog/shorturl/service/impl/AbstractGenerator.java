package com.jinblog.shorturl.service.impl;

import com.jinblog.shorturl.service.Generator;

abstract class AbstractGenerator implements Generator {

    private int urlMaxLen;
    public AbstractGenerator() {

    }

    public AbstractGenerator(int urlMaxLen) {
        this.urlMaxLen = urlMaxLen;
    }

    public int getUrlMaxLen() {
        return urlMaxLen;
    }

    public void setUrlMaxLen(int urlMaxLen) {
        this.urlMaxLen = urlMaxLen;
    }
}
