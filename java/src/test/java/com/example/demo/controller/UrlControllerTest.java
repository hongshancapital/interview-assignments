package com.example.demo.controller;

import com.example.demo.AssignmentApplicationTests;
import com.example.demo.common.Constants;
import com.example.demo.request.QueryOriginalURLRequest;
import com.example.demo.request.QueryShortURLRequest;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

@Slf4j
public class UrlControllerTest extends AssignmentApplicationTests {
    @Autowired
    private TestRestTemplate template;

    private QueryShortURLRequest queryShortURLRequest;
    private QueryOriginalURLRequest queryOriginalURLRequest;

    @BeforeEach
    public void init() {
        queryShortURLRequest = new QueryShortURLRequest();
        queryOriginalURLRequest = new QueryOriginalURLRequest();
    }

    @AfterEach
    public void clear() {
        queryShortURLRequest = null;
        queryOriginalURLRequest = null;
    }

    @Test
    @DisplayName("长链接不能为空")
    public void queryShortURL1() {
        BaseResponse actual = template.postForObject("/queryShortURL", queryShortURLRequest, BaseResponse.class);

        log.info("actual is:{}", actual);
        assertThat(actual.getMessage(), containsString("不能为空"));
    }

    @Test
    @DisplayName("长链接最大长度不能超过300个字符")
    public void queryShortURL2() {
        queryShortURLRequest.setOriginUrl(Constants.HTTP_LINK + RandomStringUtils.randomAscii(301));

        BaseResponse actual = template.postForObject("/queryShortURL", queryShortURLRequest, BaseResponse.class);

        log.info("actual is:{}", actual);

        assertThat(actual.getMessage(), containsString("最大长度不能超过300个字符"));
    }

    @Test
    @DisplayName("长链接必须以http或https打头")
    public void queryShortURL3() {
        queryShortURLRequest.setOriginUrl(RandomStringUtils.randomAscii(10));

        BaseResponse actual = template.postForObject("/queryShortURL", queryShortURLRequest, BaseResponse.class);

        log.info("actual is:{}", actual);

        assertThat(actual.getMessage(), containsString("必须以http或https打头"));
    }

    @Test
    @DisplayName("操作成功")
    public void queryShortURL4() {
        queryShortURLRequest.setOriginUrl("https://zh.wikipedia.org/zh-cn/%E6%96%90%E6%B3%A2%E9%82%A3%E5%A5%91%E6%95%B0");
        BaseResponse actual = template.postForObject("/queryShortURL", queryShortURLRequest, BaseResponse.class);

        log.info("actual is:{}", actual);

        assertThat(actual.getMessage(), equalTo(ResultCode.SUCCESS.getMessage()));
    }

    @Test
    @DisplayName("短链接不能为空")
    public void queryOriginalURL1() {
        BaseResponse actual = template.postForObject("/queryOriginalURL", queryOriginalURLRequest, BaseResponse.class);

        log.info("actual is:{}", actual);

        assertThat(actual.getMessage(), containsString("不能为空"));
    }

    @Test
    @DisplayName("短链接最大长度不能超过8个字符")
    public void queryOriginalURL2() {
        queryOriginalURLRequest.setShortURL(RandomStringUtils.randomAlphanumeric(9));

        BaseResponse actual = template.postForObject("/queryOriginalURL", queryOriginalURLRequest, BaseResponse.class);

        log.info("actual is:{}", actual);

        assertThat(actual.getMessage(), containsString("最大长度不能超过8个字符"));
    }

    @Test
    @DisplayName("短链接必须A−Z,a−z,0−9")
    public void queryOriginalURL3() {
        queryOriginalURLRequest.setShortURL(RandomStringUtils.randomAlphanumeric(7) + "_");

        BaseResponse actual = template.postForObject("/queryOriginalURL", queryOriginalURLRequest, BaseResponse.class);

        log.info("actual is:{}", actual);

        assertThat(actual.getMessage(), containsString("必须A−Z,a−z,0−9"));
    }

    @Test
    @DisplayName("操作成功")
    public void queryOriginalURL4() {
        queryShortURLRequest.setOriginUrl("https://zh.wikipedia.org/zh-cn/%E6%96%90%E6%B3%A2%E9%82%A3%E5%A5%91%E6%95%B0");
        BaseResponse response = template.postForObject("/queryShortURL", queryShortURLRequest, BaseResponse.class);

        queryOriginalURLRequest.setShortURL((String)response.getData());
        BaseResponse actual = template.postForObject("/queryOriginalURL", queryOriginalURLRequest, BaseResponse.class);

        log.info("actual is:{}", actual);

        assertThat(actual.getMessage(), equalTo(ResultCode.SUCCESS.getMessage()));
    }
}
