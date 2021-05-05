package com.wangxinyu.interview.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangxinyu.interview.bean.CommonReturnType;
import com.wangxinyu.interview.bean.UrlDTO;
import com.wangxinyu.interview.cons.ErrorReason;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.LinkedList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GenAndSaveSURLControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;


    private MultiValueMap<String, String> multiValueMap;
    private HttpHeaders headers;
    private HttpEntity<MultiValueMap<String, String>> entity;
    private String testInvokeUrl;
    private HttpMethod method;
    private LinkedList<String> lUrlTestDataList = new LinkedList<>();
    private final int SURL_LENGTH = 8;
    //成功结果记录map，<ShortURL, LongURL>
    public static final HashMap<String, String> SUCCESS_RECORD_MAP = new HashMap<>();

    @BeforeEach
    void prepareParam() {
        method = HttpMethod.POST;
        multiValueMap = new LinkedMultiValueMap<>();
        headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        entity = new HttpEntity<>(multiValueMap, headers);
        testInvokeUrl = "http://localhost:" + port + "/genSUrl";
        prepareData();
    }


    void prepareData() {
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658001");//长度64
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658002");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658003");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658004");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658005");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658006");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658007");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658008");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658009");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658010");
    }

    @Test
    void testGenAndSaveSUrl() {
//        ResponseEntity<List> result = restTemplate.getForEntity("/genSUrl", List.class);
//        MatcherAssert.assertThat(result.getBody(), Matchers.notNullValue());

        for (String next : lUrlTestDataList) {
            multiValueMap.clear();
            multiValueMap.add("lUrl", next);
            exchangeAndAssert();

        }
        //验证过长的URL是否会返回错误code
        String testLongURL = "http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658010;http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658010;http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658010;http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658010";
        multiValueMap.clear();
        multiValueMap.add("lUrl", testLongURL);
        exchangeAndAssert();

        //验证过短的lUrl会返回错误的code
        String testTooShortlURL = "";
        multiValueMap.clear();
        multiValueMap.add("lUrl", testTooShortlURL);
        exchangeAndAssert();

    }

    private void exchangeAndAssert() {
        ResponseEntity<String> response = restTemplate.exchange(testInvokeUrl, method, entity, String.class);
        CommonReturnType commonReturnType = JSON.parseObject(response.getBody(), CommonReturnType.class);
        System.out.println(commonReturnType);
        String lUrl = multiValueMap.getFirst("lUrl");
        int lUrlLength = lUrl.length();
        if (lUrlLength > 8 && lUrlLength <= 200) {
            assertThat(commonReturnType.getCode(), equalTo(0));
            assertThat(commonReturnType.getErrorMsg(), equalTo(""));
            UrlDTO urlDTO = ((JSONObject) commonReturnType.getData()).toJavaObject(UrlDTO.class);
            assertThat(urlDTO.getLongURL(), equalTo(lUrl));
            String shortURL = urlDTO.getShortURL();
            Assert.assertNotNull(shortURL);
            assertThat(shortURL.length(), equalTo(SURL_LENGTH));
            //不能出现重复的SUrl
            Assert.assertNull(SUCCESS_RECORD_MAP.get(shortURL));
            //将结果放入Map
            SUCCESS_RECORD_MAP.put(shortURL, lUrl);
        } else if (lUrlLength <= 8) {
            assertThat(commonReturnType.getCode(), equalTo(ErrorReason.LURL_TOO_SHORT.getCode()));
            assertThat(commonReturnType.getErrorMsg(), equalTo(ErrorReason.LURL_TOO_SHORT.getReason()));
        } else {
            assertThat(commonReturnType.getCode(), equalTo(ErrorReason.LURL_TOO_LONG.getCode()));
            assertThat(commonReturnType.getErrorMsg(), equalTo(ErrorReason.LURL_TOO_LONG.getReason()));
            Assert.assertNull(commonReturnType.getData());
        }
    }
}