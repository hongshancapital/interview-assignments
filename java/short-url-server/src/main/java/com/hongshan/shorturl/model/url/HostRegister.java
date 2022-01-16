package com.hongshan.shorturl.model.url;

import lombok.Data;

import java.util.Date;

/**
 * @author: huachengqiang
 * @date: 2022/1/16
 * @description:
 */
@Data
public class HostRegister {
    /**
     * 注册的授信域名
     */
    private String host;
    /**
     * 有效时间
     */
    private Date expire;
    /**
     * 签名计算因子
     */
    private String secret;
}
