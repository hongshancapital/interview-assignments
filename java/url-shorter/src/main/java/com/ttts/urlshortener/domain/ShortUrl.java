package com.ttts.urlshortener.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ShortUrl implements Serializable {
    //主键，唯一ID
    private Long id;
    //短链
    private Long surl;
    //长链
    private String lurl;
    //长链的md5值, 用于优化索引
    private String lmd5;
    //创建时间
    private LocalDateTime crateTime;
    //过期时间
    private LocalDateTime expiresTime;
}
