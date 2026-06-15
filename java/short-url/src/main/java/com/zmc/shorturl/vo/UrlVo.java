package com.zmc.shorturl.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Description: 短链接参数
 * Author: Zmc
 * Date: 2021-12-11 18:20
 **/
@Getter
@Setter
@ToString
public class UrlVo {

    private long id;

    private String shortUrl;

    private String longUrl;

    public UrlVo(long id, String shortUrl, String longUrl) {
        this.id = id;
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }
}
