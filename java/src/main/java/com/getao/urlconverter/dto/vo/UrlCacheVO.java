package com.getao.urlconverter.dto.vo;

import lombok.Data;

@Data
public class UrlCacheVO {

    public UrlCacheVO(){}

    public UrlCacheVO(String resUrl, String oriUrl) {
        this.resUrl = resUrl;
        this.oriUrl = oriUrl;
        // this.generateTime = generateTime;
    }

    /**
     * Generator产生出的短链接，作为缓存中的key
     */
    private String resUrl;

    /**
     * 短链接对应的原长链接
     */
    private String oriUrl;

    /**
     * 短链接生成时间
     */
    //  private String generateTime;
}