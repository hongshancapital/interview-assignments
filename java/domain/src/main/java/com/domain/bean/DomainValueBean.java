package com.domain.bean;

/**
 * @author ：ji
 * @date ：2021/11/20
 * @description：封装存储url的bean
 */
public class DomainValueBean {
    /**
     * 要存储的url
     */
    private String url;
    /**
     * 当前url存储时的时间戳
     */
    private long time;

    public DomainValueBean(){};
    public DomainValueBean(String url,long time){
        this.url = url;
        this.time = time;
    };

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
