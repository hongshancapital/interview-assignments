package com.yujianfei.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ShortDN extends DomainName {

    private final String SUO = "http://suo.nz/";
    /**
     * 域名
     */
    String dn;
    /**
     * 路径
     */
    String path;

    public ShortDN(String path) {
        this.dn = SUO;
        this.path = path;
    }

    @Override
    public String getUrl() {
        return this.SUO + this.path;
    }

    /**
     * 短域名校验功能
     */
    @Override
    public boolean verify() {
        return (this.path.length() <= 8);
    }

}
