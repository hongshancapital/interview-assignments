package com.example.demo.common;

import com.example.demo.AssignmentApplicationTests;
import com.example.demo.request.QueryShortURLRequest;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParseException;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

@Slf4j
class GlobalExceptionHandlerTest extends AssignmentApplicationTests {

    @Autowired
    private TestRestTemplate template;

    @Test
    void handlerException() {
        BaseResponse actual = template.postForObject("/queryShortURL", null, BaseResponse.class);

        log.info("actual is:{}", actual);
        assertThat(actual.getMessage(), containsString("未知异常"));
    }
}