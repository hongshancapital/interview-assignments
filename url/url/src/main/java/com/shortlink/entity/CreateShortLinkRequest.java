package com.shortlink.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 创建短链请求实体
 */
@Builder
@Data
public class CreateShortLinkRequest{

    private String url;

    private String reqId;

    private Integer appId;

}
