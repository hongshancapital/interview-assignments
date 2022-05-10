package com.heyenan.shorturldemo.common.request;


/**
 * @author heyenan
 * @description 短域名服务请求体
 *
 * @date 2020/5/08
 */
public class ShortUrlRequest {

    /** 长链接 */
    private String originUrl;

    public String getOriginUrl() {
        return originUrl;
    }

    public void setOriginUrl(String s) {
        this.originUrl = s;
    }
}
