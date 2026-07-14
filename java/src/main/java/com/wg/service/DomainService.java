package com.wg.service;

public interface DomainService {

    String getShortUrl(String realUrl);

    String getRealUrl(String shortCode);

}
