package com.skg.domain.demo.service.domain;

/**
 * @Author smith skg
 * @Date 2021/10/12 10:27
 * @Version 1.0
 */
public interface IDomainService {

    String generateShort(String domain);

    String getLongByShortDomain(String domain);

    String generateShortLocal(String domain);

    String getLongByShortDomainLocal(String domain);
}
