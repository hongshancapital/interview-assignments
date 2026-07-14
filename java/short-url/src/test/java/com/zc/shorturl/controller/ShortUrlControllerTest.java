package com.zc.shorturl.controller;

import com.zc.shorturl.dto.BaseResponse;
import com.zc.shorturl.dto.CreateShortUrlRequest;
import com.zc.shorturl.dto.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.util.HashMap;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DirtiesContext
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ShortUrlControllerTest {
    @Resource
    private TestRestTemplate template;

    @Test
    public void testCreateShortUrl(){
        CreateShortUrlRequest request = new CreateShortUrlRequest("http://www.test1.com");
        BaseResponse actual = template.postForObject("/api/v1/create", request, BaseResponse.class);
        assertThat(actual.getCode(), equalTo(ResultCode.SUCCESS.getCode()));

        // uri not found
        request = new CreateShortUrlRequest("http://www.test1.com");
        actual = template.postForObject("/api/v1/create1", request, BaseResponse.class);
        assertThat(actual.getCode(), equalTo(ResultCode.NOT_FOUND.getCode()));

        // invalid url
        request = new CreateShortUrlRequest("http123");
        actual = template.postForObject("/api/v1/create", request, BaseResponse.class);
        assertThat(actual.getCode(), equalTo(ResultCode.PARAMETER_ERROR.getCode()));

        // unkown exception
        HashMap<String, String> request1 = new HashMap<>();
        request1.put("l", "http");
        actual = template.postForObject("/api/v1/create", request1, BaseResponse.class);
        assertThat(actual.getCode(), equalTo(ResultCode.UNKNOWN_EXCEPTION.getCode()));
    }
}
