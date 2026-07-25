package com.ttts.urlshortener.service;

/**
 * 长整形Value生成
 */
public interface LongValueCreateService {

    /**
     * 长整型数生成
     * @param value 输入字符串
     * @return
     */
    Long create(String value);
}
