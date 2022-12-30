package com.url.bean;

import com.url.error.BusinessCode;
import java.io.Serializable;

/**
 * 结果封装类
 * @Author jeckzeng
 * @Date 2022/4/30
 * @Version 1.0
 */
public class UrlResultBean implements Serializable {

    private Integer code;

    private String desc;

    private String urlData;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setUrlData(String urlData) {
        this.urlData = urlData;
    }

    public String getUrlData() {
        return urlData;
    }

    public UrlResultBean() {

    }

    public UrlResultBean(BusinessCode businessCode,String urlData){
        this.code = businessCode.code();
        this.desc = businessCode.msg();
        this.urlData = urlData;
    }

}
