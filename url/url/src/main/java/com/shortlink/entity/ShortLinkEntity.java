package com.shortlink.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/**
 * 短链实体
 */
@Builder
@Data
public class ShortLinkEntity {

    private long id;

    // 短链
    private String shortLink;

    // 业务方id
    private Integer appid;

    // 原始Url
    private String originalUrl;

    // 过期时间
    private long expireTime;

    // 创建时间
    private Date createTime;


}
