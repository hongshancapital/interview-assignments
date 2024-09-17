package com.example.demo.domainnameservice.service;

/**
 * domain name storage and visit.
 *
 * @author laurent
 * @date 2021-12-11 下午1:17
 */
public interface DomainNameService {

    /**
     * store long url.
     * @param url   raw url.
     * @return      shortened url.
     */
    String storeUrl(String url);

    /**
     * visit short url.
     * @param url   shortened url.
     * @return      raw url.
     */
    String visitUrl(String url);

}
