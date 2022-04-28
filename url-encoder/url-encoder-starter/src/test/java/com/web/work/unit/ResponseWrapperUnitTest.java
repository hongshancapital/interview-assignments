package com.web.work.unit;

import com.web.work.common.exception.ErrorCode;
import com.web.work.common.exception.ResponseWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author chenze
 * @version 1.0
 * @date 2022/4/27 11:34 AM
 */
@RunWith(MockitoJUnitRunner.class)
public class ResponseWrapperUnitTest {

    private final String test = "test";

    @Before
    public void setUp() {
    }

    @Test
    public void should_return_success_with_data() {
        ResponseWrapper<String> responseWrapper = ResponseWrapper.success(test);
        assertThat(responseWrapper.isSuccess()).isTrue();
        assertThat(responseWrapper.getMessage()).isEqualTo("OK");
        assertThat(responseWrapper.getData()).isEqualTo(test);
    }

    @Test
    public void should_return_success_no_data() {
        ResponseWrapper<String> responseWrapper = ResponseWrapper.success();
        assertThat(responseWrapper.isSuccess()).isTrue();
        assertThat(responseWrapper.getMessage()).isEqualTo("OK");
        assertThat(responseWrapper.getData()).isEqualTo(null);
    }

    @Test
    public void should_return_fail() {
        ResponseWrapper<String> responseWrapper = ResponseWrapper.fail(ErrorCode.URL_EXCEPTION);
        assertThat(responseWrapper.isSuccess()).isFalse();
        assertThat(responseWrapper.getMessage()).isEqualTo(ErrorCode.URL_EXCEPTION.getMessage());
        assertThat(responseWrapper.getErrorCode()).isEqualTo(ErrorCode.URL_EXCEPTION.getCode());
    }
}
