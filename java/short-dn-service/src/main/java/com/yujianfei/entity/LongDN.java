package com.yujianfei.entity;

import java.util.regex.Pattern;

/**
 * 长域名对象
 *
 * @author Yujianfei
 */
public class LongDN extends DomainName {

    public LongDN(String url) {
        this.url = url;
    }

    /**
     * 域名校验功能
     */
    @Override
    public boolean verify() {
        return formatValid() && whileListValid();
    }

    /**
     * url 格式验证
     *
     * @return 是否成功
     */
    private boolean formatValid() {
        String urlPattern = "[http|https]+[://]+[0-9A-Za-z:/[-]_#[?][=][.][&]]*";
        return Pattern.matches(urlPattern, this.url);
    }

    /**
     * 白名单通过验证
     */

    private boolean whileListValid() {
        return true;
    }

}
