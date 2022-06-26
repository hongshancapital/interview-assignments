package com.ttts.urlshortener.domain;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class ShortUrlVO {
    //新链接
    String newUrl;
    //原始链接
    String orgUrl;
    //过期时间
    LocalDateTime expiresTime;
}
