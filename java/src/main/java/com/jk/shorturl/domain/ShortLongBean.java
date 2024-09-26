package com.jk.shorturl.domain;

/**
 * 此处不关心短域名中的域名部分，只要在域名映射中能够指向此服务就行，一旦进入此程序，只考虑短域名中的短码部分即可。
 */
public class ShortLongBean {
    private String longURL;

    private String shortCode;

    public String getLongURL() {
        return longURL;
    }

    public void setLongURL(String longURL) {
        this.longURL = longURL;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

}
