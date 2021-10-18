package com.wh.franken.shorturl.app.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author fanliang
 */
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
