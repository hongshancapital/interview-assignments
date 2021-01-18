package com.wb.shorturl.controller;

import com.wb.shorturl.common.exception.BaseErrorCode;
import com.wb.shorturl.common.web.ApiResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author bing.wang
 * @date 2021/1/8
 */


@SpringBootTest
public class ShortUrlControllerTests {

    @Autowired
    private ShortUrlController shortUrlController;

    @Autowired
    private HttpServletRequest request;

    /**
     * 测试登录成功
     * @param
     */
    @Test
    void toErrorPage1(){
        String res = shortUrlController.login(request,"test","test123");
        Assertions.assertEquals(res,"index");
    }

    /**
     * 测试登录失败
     * @param
     */
    @Test
    void toErrorPage2(){
        String res = shortUrlController.login(request,"test","test");
        Assertions.assertEquals(res,"login");
    }

    /**
     * 测试异常状码跳转
     * @param
     */
    @ParameterizedTest
    @ValueSource(ints = {403,404,500})
    void toErrorPage(int status){
        String res = shortUrlController.toErrorPage(status);
        Assertions.assertEquals(res,"error/"+status);
    }

    /**
     * 测试短码存在
     *
     * @param shortCode the query shortCode
     */

    @ParameterizedTest
    @ValueSource(strings = {"Os7jWHKNc4"})
    void getOriginUrlByShortCodeSuccess(String shortCode) {
        String redirect = shortUrlController.shortCodeRedirect(shortCode);
        Assertions.assertNotEquals(redirect, "redirect:/error/500");
    }

    /**
     * 测试短码不存在
     *
     * @param shortCode the query shortCode
     */
    @ParameterizedTest
    @ValueSource(strings = {"notExistuwS",""})
    void shortCodeRedirectFailure(String shortCode) {
        String redirect = shortUrlController.shortCodeRedirect(shortCode);
        Assertions.assertEquals(redirect, "redirect:error/500");
    }
}
