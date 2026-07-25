package com.eagle.shorturl.service;

import javax.validation.constraints.NotBlank;

/**
 * @author eagle
 * @description
 */
public interface ShortUrlService {

    /**
     * longUrl -> shortUrl
     * @param longUrl
     * @return
     */
    String add(@NotBlank String longUrl);

    /**
     * shortUrl -> longUrl
     * @param shortUrl
     * @return
     */
    String get(@NotBlank String shortUrl);

}
