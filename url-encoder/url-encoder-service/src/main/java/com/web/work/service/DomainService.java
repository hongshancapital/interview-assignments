package com.web.work.service;

/**
 * domain service interface
 *
 * @author chenze
 * @version 1.0
 * @date 2022/4/26 7:25 PM
 */
public interface DomainService {

    /**
     * create short domain
     *
     * @param fullUrl full url
     * @return 62radix code
     */
    String createShortDomain(String fullUrl);

    /**
     * get full domain
     *
     * @param shortUrl shor url
     * @return full Url
     */
    String getFullDomain(String shortUrl);
}
