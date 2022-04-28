package com.web.work.unit;

import com.web.work.validator.DomainValidator;
import com.web.work.common.exception.BadRequestException;
import com.web.work.common.exception.ErrorCode;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 1:23 AM
 */
@RunWith(MockitoJUnitRunner.class)
public class DomainValidatorUnitTest {

    private DomainValidator domainValidator;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String errorFullUrl = "abc://www.baidu.com/ass/1344";

    @Before
    public void setUp() {
        domainValidator = new DomainValidator();
    }

    @Test
    public void should_validate_url_exception() {
        thrown.expect(BadRequestException.class);
        thrown.expectMessage(ErrorCode.URL_EXCEPTION.getMessage());
        domainValidator.validateUrl(errorFullUrl);
    }
}
