package com.liuxiang.model.po;

import lombok.Data;

/**
 * @author liuxiang6
 * @date 2022-01-07
 **/
@Data
public class ShortUrlMappingPo {
    private Long id;
    private String shortUrl;
    private String longUrl;
    private Long createTime;
}
