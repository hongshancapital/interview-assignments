package com.cyh.assignment.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DomainConvertorControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private static final Logger logger = LoggerFactory.getLogger(DomainConvertorControllerTest.class);

    private HttpHeaders headers;


    @BeforeTestExecution
    void init() {
        headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
    }

    @Test
    void saveShortDomain() {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("fullDomain","http://baidu.com");
        String result = restTemplate.postForObject("/saveShortDomain",new HttpEntity(params,headers),
                String.class);
        logger.info(result);
    }

    @Test
    void saveShortDomainWithIllegalUrl() {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("fullDomain","httpttt://baidu.com");
        String result = restTemplate.postForObject("/saveShortDomain",new HttpEntity(params,headers),
                String.class);
        logger.info(result);
    }


    @Test
    void getFullDomain() {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("fullDomain","http://baidu.com");
        String shortDomain = restTemplate.postForObject("/saveShortDomain",new HttpEntity(params,headers),
                String.class);
        params.clear();
        params.add("shortDomain",shortDomain);
        String result = restTemplate.postForObject("/getFullDomain",new HttpEntity(params,headers),
                String.class);
        logger.info(result);
    }

    @Test
    void getFullDomainNotExisted() {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        params.add("fullDomain","http://baidu.com");
        String shortDomain = restTemplate.postForObject("/saveShortDomain",new HttpEntity(params,headers),
                String.class);
        params.clear();
        params.add("shortDomain","test");
        String result = restTemplate.postForObject("/getFullDomain",new HttpEntity(params,headers),
                String.class);
        logger.info(result);
    }

    @Test
    void testLRU() {
        MultiValueMap<String,Object> params = new LinkedMultiValueMap<>();
        // 存第1个
        params.add("fullDomain","http://baidu.com");
        String shortDomain1 = restTemplate.postForObject("/saveShortDomain",new HttpEntity(params,headers),
                String.class);
        // 存第2个
        params.clear();
        params.add("fullDomain","http://google.com");
        String shortDomain2 = restTemplate.postForObject("/saveShortDomain",new HttpEntity(params,headers),
                String.class);
        // 读第1个
        params.clear();
        params.add("shortDomain",shortDomain1);
        String result1 = restTemplate.postForObject("/getFullDomain",new HttpEntity(params,headers),
                String.class);
        // 存第3个，安装LRU，应该顶掉第2个
        params.clear();
        params.add("fullDomain","http://bing.com");
        String shortDomain3 = restTemplate.postForObject("/saveShortDomain",new HttpEntity(params,headers),
                String.class);
        // 读第2个，应该不存在
        params.clear();
        params.add("shortDomain",shortDomain2);
        String result2 = restTemplate.postForObject("/getFullDomain",new HttpEntity(params,headers),
                String.class);
        logger.info("this should fail" + result2);
        // 读第1个，应该存在
        params.clear();
        params.add("shortDomain",shortDomain1);
        String result3 = restTemplate.postForObject("/getFullDomain",new HttpEntity(params,headers),
                String.class);
        logger.info("this should success" + result3);
    }
}