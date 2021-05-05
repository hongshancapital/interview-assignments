package com.wangxinyu.interview.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wangxinyu.interview.bean.CommonReturnType;
import com.wangxinyu.interview.bean.UrlDTO;
import com.wangxinyu.interview.cons.ErrorReason;
import org.junit.Assert;
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

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SearchForLURLControllerTest {


    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    private MultiValueMap<String, String> multiValueMap;
    private HttpHeaders headers;
    private HttpEntity<MultiValueMap<String, String>> entity;
    private String getLURLInvokeUrl;
    private String genInvokeUrl;
    private HttpMethod method;
    private LinkedList<String> lUrlTestDataList = new LinkedList<>();
    //成功结果记录map，<ShortURL, LongURL>
    private  HashMap<String, String> SUCCESS_RECORD_MAP = new HashMap<>();


    @BeforeEach
    void prepareParam() {
        method = HttpMethod.GET;
        multiValueMap = new LinkedMultiValueMap<>();
        headers = new HttpHeaders();
        entity = new HttpEntity<>(multiValueMap, headers);
        getLURLInvokeUrl = "http://localhost:" + port + "/getLUrl";
        genInvokeUrl = "http://localhost:" + port + "/genSUrl";
        prepareData();
    }

    void prepareData() {
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658021");//长度64
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658022");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658023");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658024");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658025");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658026");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658027");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658028");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658029");
        lUrlTestDataList.add("http://wiki.xxxxxx-inc.com/pages/viewpage.action?pageId=37658020");
        headers.add("Content-Type", "application/x-www-form-urlencoded");
        for (String next : lUrlTestDataList) {
            multiValueMap.clear();
            multiValueMap.add("lUrl", next);
            ResponseEntity<String> response = restTemplate.exchange(genInvokeUrl, HttpMethod.POST, entity, String.class);
            CommonReturnType commonReturnType = JSON.parseObject(response.getBody(), CommonReturnType.class);
            UrlDTO urlDTO = ((JSONObject) commonReturnType.getData()).toJavaObject(UrlDTO.class);
            String shortURL = urlDTO.getShortURL();
            //将结果放入Map
            SUCCESS_RECORD_MAP.put(shortURL, next);
        }
    }

    @Test
    void searchForLURL() throws Exception {
        multiValueMap.clear();
        for (Map.Entry<String, String> next : SUCCESS_RECORD_MAP.entrySet()) {
            String sUrl = next.getKey();
//            String sUrl = "2pFq1&19";
            String lUrl = next.getValue();
            System.out.println(String.format("%s?sUrl=%s",getLURLInvokeUrl,sUrl));
            ResponseEntity<String> response = restTemplate.exchange(new URI(String.format("%s?sUrl=%s",getLURLInvokeUrl,URLEncoder.encode(sUrl,"utf-8"))), method, entity, String.class);
            CommonReturnType commonReturnType = JSON.parseObject(response.getBody(), CommonReturnType.class);
            assertThat(commonReturnType.getCode(), equalTo(0));
            UrlDTO urlDTO = ((JSONObject) commonReturnType.getData()).toJavaObject(UrlDTO.class);
            assertThat(urlDTO.getLongURL(), equalTo(lUrl));
            assertThat(urlDTO.getShortURL(), equalTo(sUrl));
        }
        //测试sURL过长
        String too_long_surl = "1432567809876543234567";
        ResponseEntity<String> response = restTemplate.exchange(new URI(String.format("%s?sUrl=%s",getLURLInvokeUrl,URLEncoder.encode(too_long_surl,"utf-8"))), method, entity, String.class);
        CommonReturnType commonReturnType = JSON.parseObject(response.getBody(), CommonReturnType.class);
        assertThat(commonReturnType.getCode(), equalTo(ErrorReason.SURL_TOO_LONG.getCode()));
        assertThat(commonReturnType.getErrorMsg(), equalTo(ErrorReason.SURL_TOO_LONG.getReason()));
        Assert.assertNull(commonReturnType.getData());

        //测试sURL中含有特殊字符
        String surl_contains_not_support_char = "中国";
        response = restTemplate.exchange(new URI(String.format("%s?sUrl=%s",getLURLInvokeUrl,URLEncoder.encode(surl_contains_not_support_char,"utf-8"))), method, entity, String.class);
        commonReturnType = JSON.parseObject(response.getBody(), CommonReturnType.class);
        assertThat(commonReturnType.getCode(), equalTo(ErrorReason.OTHER_ERROR.getCode()));
        Assert.assertNotNull(commonReturnType.getErrorMsg());
        Assert.assertNull(commonReturnType.getData());

        //测试无法检索到的sUrl
        String surl_that_can_not_be_found = "";
        response = restTemplate.exchange(new URI(String.format("%s?sUrl=%s",getLURLInvokeUrl,URLEncoder.encode(surl_that_can_not_be_found,"utf-8"))), method, entity, String.class);
        commonReturnType = JSON.parseObject(response.getBody(), CommonReturnType.class);
        assertThat(commonReturnType.getCode(), equalTo(ErrorReason.NOT_FOUND.getCode()));
        assertThat(commonReturnType.getErrorMsg(), equalTo(ErrorReason.NOT_FOUND.getReason()));
        Assert.assertNull(commonReturnType.getData());

    }
}