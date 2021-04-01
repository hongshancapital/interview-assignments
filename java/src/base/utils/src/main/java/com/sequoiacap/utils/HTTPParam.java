package com.sequoiacap.utils;

/**
 * Created by XdaTk on 2014/12/21.
 * <p/>
 * HTTP请求参数封装
 */
public class HTTPParam {
    //请求参数
    private String key;
    //参数值
    private String value;
 
    public HTTPParam() {
 
    }
 
    public HTTPParam(String key, String value) {
        this.key = key;
        this.value = value;
    }
 
    public String getKey() {
        return key;
    }
 
    public void setKey(String key) {
        this.key = key;
    }
 
    public String getValue() {
        return value;
    }
 
    public void setValue(String value) {
        this.value = value;
    }
}