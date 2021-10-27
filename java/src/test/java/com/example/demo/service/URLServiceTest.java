package com.example.demo.service;

import com.example.demo.AssignmentApplicationTests;
import com.example.demo.common.Constants;
import com.example.demo.response.BaseResponse;
import com.example.demo.response.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.core.IsEqual.equalTo;

@Slf4j
public class URLServiceTest extends AssignmentApplicationTests {

    @Autowired
    private URLService urlService;

    @Test
    @DisplayName("生成短链接")
    public void op1() {
        String originalURL = "www.baidu.com";
        BaseResponse response = urlService.queryShortURL(originalURL);
        log.info("\ntinyUrl is:{}", response);

        BaseResponse result = urlService.queryOriginalURL((String)response.getData());
        log.info("\nresult is:{}", result);
        assertThat((String)result.getData(), equalTo("www.baidu.com"));
    }

    @Test
    @DisplayName("长链接已存在")
    public void op2() {
        String originalURL = "www.baidu.com";
        BaseResponse response = urlService.queryShortURL(originalURL);
        log.info("\ntinyUrl is:{}", response);

        BaseResponse result = urlService.queryOriginalURL((String)response.getData());
        log.info("\nresult is:{}", result);
        assertThat((String)result.getData(), equalTo("www.baidu.com"));
    }

    @Test
    @DisplayName("短链接不存在,获取不到长链接")
    public void op3() {
        BaseResponse result = urlService.queryOriginalURL("BLLLLL");
        log.info("\nresult is:{}", result);
        assertThat(result.getMessage(), equalTo("短连接不存在"));
    }

    @Test
    @DisplayName("缓存中没有此短链接")
    public void op4() {
        boolean result = urlService.isExistInCache("BLLLLL");
        log.info("\nresult is:{}", result);

        assertThat(result, equalTo(false));
    }

    @Test
    @DisplayName("缓存中有此短链接")
    public void op5() {
        String originalURL = "www.baidu.com";
        BaseResponse response = urlService.queryShortURL(originalURL);
        boolean result = urlService.isExistInCache((String)response.getData());
        log.info("\nresult is:{}", result);

        assertThat(result, equalTo(true));
    }

    @Test
    @DisplayName("idToShortURL")
    public void op6() {
        long n = (int)Math.random() + 1;
        String shortURL = urlService.idToShortURL(n);
        long n1 = urlService.shortURLtoID(shortURL);
        log.info("\nresult is:{}", shortURL);

        assertThat(n, equalTo(n));
    }

    @Test
    @DisplayName("shortURLtoID")
    public void op7() {
        String shortURL = "baAD55";
        long id = urlService.shortURLtoID(shortURL);
        String shortURL1 = urlService.idToShortURL(id);
        log.info("\nresult is:{}", id);

        assertThat(shortURL, equalTo(shortURL1));
    }

    @Test
    @DisplayName("reverse")
    public void op8() {
        String str = "abcd";
        String str1 = urlService.reverse(str);
        String str2 = urlService.reverse(str1);
        log.info("\nresult is:{}", str1);

        assertThat(str, equalTo(str2));
    }

    @Test
    @DisplayName("reverse is blank")
    public void op9() {
        String str = "";
        String str1 = urlService.reverse(str);
        log.info("\nresult is:{}", str1);

        assertThat(str, equalTo(str1));
    }

    @Test
    @DisplayName("shortURLtoID url过长")
    public void op10() {
        String shortURL = "abcdADDEF55232";
        long id = urlService.shortURLtoID(shortURL);
        log.info("\nresult is:{}", id);

        assertThat(id, equalTo(Long.MAX_VALUE));
    }

    @Test
    @DisplayName("reverse is null")
    public void op11() {
        String str = "";
        String str1 = urlService.reverse(str);
        log.info("\nresult is:{}", str1);

        assertThat(str, equalTo(str1));
    }

//    @Test
//    @DisplayName("queryShortURL limit")
//    public void op12() {
//        BaseResponse response = null;
//        String str = "";
//        for(long i = 0; i < Long.MAX_VALUE; i++){
//            str = Constants.HTTP_LINK + RandomStringUtils.random(7);
//            response = urlService.queryShortURL(str);
//            if(response.getCode() == ResultCode.SHORTURL_LIMIT.getCode()){
//                break;
//            }
//        }
//        log.info("\nresult is:{}", response);
//
//        assertThat(ResultCode.SHORTURL_LIMIT.getCode(), equalTo(response.getCode()));
//    }
}
