package com.zhouhongbing.shorturl.entity;

import lombok.Data;

/**
 * @version 1.0
 * @Author 海纳百川zhb
 **/
@Data
public class ShorterUrl {

    /**
     * 无参构造函数
     */
    public ShorterUrl() {
    }

    ;

    /**
     * 构造函数
     *
     * @param longUrl
     */
    public ShorterUrl(String longUrl) {
        this.longUrl = longUrl;
    }


    private String longUrl;   //长域名

    private Long id;  //雪花算法id

    private String base62ShortUrl; //雪花算法id转为62进制的短域名

    private String shorterUrl; //短域名

    private String userId; //用户id

    private String appId; //设备id

    private int count; //点击次数


}
