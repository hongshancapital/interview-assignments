package org.goofly.shortdomain.repository;

/**
 * @author : goofly
 * @Email: 709233178@qq.com
 */
public interface ShortDomainRepository {

    void save(String shortCode,String originalUrl);

    String getOriginalUrl(String shortCode);
}
