package com.duoji.shortlink.ability.model;

import lombok.Data;

/**
 * @author XY
 * @Description
 * @createTime 2021年12月17日 19:44:00
 */

@Data
public class ShortLinkModel {

    /**
     * id
     */
    private Long id;

    /**
     * 长连接
     */
    private String longLink;

    /**
     * 长连接对应的短链接
     */
    private String shortLink;

    /**
     * 短链接过期时间
     */
    private Long expireTime;

}
