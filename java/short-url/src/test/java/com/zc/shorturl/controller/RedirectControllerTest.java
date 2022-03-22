package com.zc.shorturl.controller;

import com.zc.shorturl.dto.BaseResponse;
import com.zc.shorturl.dto.CreateShortUrlRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.IOException;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@DirtiesContext
@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class RedirectControllerTest {
    @Resource
    private TestRestTemplate template;

    @Test
    public void testGetLongUrl(){
        String longUrl = "http://www.test1.com";
        CreateShortUrlRequest request = new CreateShortUrlRequest(longUrl);
        BaseResponse createResponse = template.postForObject("/api/v1/create", request, BaseResponse.class);
        String[] splits = String.valueOf(createResponse.getData()).split("/");
        String shortUrl = splits[splits.length - 1];

        RestTemplate restTemplate = new RestTemplate(new SimpleClientHttpRequestFactory() {
            @Override
            protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException
            {
                super.prepareConnection(connection, httpMethod);
                connection.setInstanceFollowRedirects(false);
            }
        });

        ResponseEntity response = restTemplate.getForEntity("http://localhost:8080/" + shortUrl, BaseResponse.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.FOUND));
        assertThat(String.valueOf(response.getHeaders().getLocation()), equalTo(longUrl));

        // 不存在的url
        response = template.getForEntity("/notExistUrl", BaseResponse.class);
        assertThat(response.getStatusCode(), equalTo(HttpStatus.NOT_FOUND));
    }
}
