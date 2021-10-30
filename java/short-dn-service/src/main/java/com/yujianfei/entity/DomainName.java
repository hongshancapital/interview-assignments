package com.yujianfei.entity;

import lombok.Data;

/**
 * 域名抽象类
 * @author Yujianfei
 */
@Data
public abstract class DomainName {

    /**
     * url串
     */
    String url;

    /**
     * 域名校验功能
     * @return true/false
     */
    public abstract boolean verify();

}
