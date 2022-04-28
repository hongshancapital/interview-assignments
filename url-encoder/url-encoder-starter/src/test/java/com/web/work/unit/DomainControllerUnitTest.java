package com.web.work.unit;

import com.web.work.validator.DomainValidator;
import com.web.work.common.exception.ResponseWrapper;
import com.web.work.controller.DomainController;
import com.web.work.service.DomainService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class DomainControllerUnitTest {

    @Mock
    private DomainValidator domainValidator;
    @Mock
    private DomainService domainService;

    private DomainController controller;

    private String fullUrl = "https://www.baidu.com/ass/1344";
    private String shortUrl = "https://abc.com/12345A";

    @Before
    public void setUp() {
        controller = new DomainController(domainValidator, domainService);
    }

    @Test
    public void should_create_domain_successfully() {
        ResponseWrapper responseWrapper = controller.createDomain(fullUrl);
        verify(domainValidator, times(1)).validateUrl(fullUrl);
        verify(domainService, times(1)).createShortDomain(fullUrl);
        assertThat(responseWrapper.isSuccess()).isTrue();
    }

    @Test
    public void should_get_domain_successfully() {
        ResponseWrapper responseWrapper = controller.getDomain(shortUrl);
        verify(domainValidator, times(1)).validateUrl(shortUrl);
        verify(domainService, times(1)).getFullDomain(shortUrl);
        assertThat(responseWrapper.isSuccess()).isTrue();
    }

}