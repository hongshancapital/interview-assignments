package com.assignment.api;

/**
 * @Author: shican.sc
 * @Date: 2022/6/12 22:16
 * @see
 */
public interface DomainTransforAPIServer {
    /**
     * 短域名转长域名
     *
     * @param shotDomain 短域名
     * @return
     */
     String shotToLongDomain(String shotDomain);

    /**
     * 长域名转短域名
     *
     * @param longDomain
     * @return
     */
     String longToShotDomain(String longDomain);

}
