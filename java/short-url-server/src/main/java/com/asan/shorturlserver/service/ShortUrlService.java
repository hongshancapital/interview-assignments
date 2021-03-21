package com.asan.shorturlserver.service;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author asan
 * @since 2021-03-19
 */
public interface ShortUrlService {

    String toShort(String url);

    String getOriginUrl(String code);
}
