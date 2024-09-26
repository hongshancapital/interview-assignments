package com.service;


import com.service.impl.DomainConversionServiceImpl;
import org.junit.Test;
import org.mockito.InjectMocks;

public class DomainNameServiceImplTest {

    @InjectMocks
    private DomainConversionServiceImpl domainConversionServiceImpl;

    @Test
    void shortUrlTest() {
        try {

            String shortUrl = domainConversionServiceImpl.getShortDomainUrl("http://www.baidu.com");
            System.out.println(domainConversionServiceImpl.getLongDomainUrl(shortUrl));
            System.out.println(domainConversionServiceImpl.getLongDomainUrl(null));
            System.out.println(domainConversionServiceImpl.getLongDomainUrl(""));
            domainConversionServiceImpl.getShortDomainUrl("http://www.baidu.com");
            domainConversionServiceImpl.getShortDomainUrl("http://www.google.com");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
