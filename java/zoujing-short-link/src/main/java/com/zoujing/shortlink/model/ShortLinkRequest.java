package com.zoujing.shortlink.model;

public class ShortLinkRequest extends ToString {
    /**
     * 请求应用source
     */
    private Long sourceApp;

    /**
     * 短连接
     */
    private String shortLink;

    /**
     * 长连接
     */
    private String longLink;

    public Long getSourceApp() {
        return sourceApp;
    }

    public void setSourceApp(Long sourceApp) {
        this.sourceApp = sourceApp;
    }

    public String getShortLink() {
        return shortLink;
    }

    public void setShortLink(String shortLink) {
        this.shortLink = shortLink;
    }

    public String getLongLink() {
        return longLink;
    }

    public void setLongLink(String longLink) {
        this.longLink = longLink;
    }
}
