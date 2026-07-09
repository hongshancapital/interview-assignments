package com.sequoia.shortdomain.service;

/**
 * @author lei.chen_1
 *  ShortDomainService interface
 */
public interface IShortDomainService {



    /**
     *
     * @param longDomain long domain
     * @return short domain
     */
    String getShortDomainByLongDomain(String longDomain);

    /**
     *
     * @param shortDomain short Domain
     * @return long Domain
     */
    String getLongDomainByShortDomain(String shortDomain);
}
