package com.xwt.domain.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 域名服务bean
 *
 * @author xiwentao
 * @date: 2021-07-21
 */
@Data
@Builder
public class DomainNameResponse implements Serializable {
    /**
     * UID
     */
    private static final long serialVersionUID = 1234567890L;

    /**
     * 短域名
     */
    private String shortUrl;

    /**
     * 长域名
     */
    private String longUrl;
}
