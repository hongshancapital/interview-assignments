package com.coderdream.bean;

import lombok.Data;

/**
 * 短链接Bean
 *
 * @author CoderDream
 * @version 1.0
 * @date 2022/5/8
 */
@Data
public class ShortLinkBean {

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
