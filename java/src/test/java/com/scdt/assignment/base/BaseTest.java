package com.scdt.assignment.base;

import static io.restassured.RestAssured.given;

import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.web.server.LocalServerPort;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cn.hutool.core.lang.Dict;
import io.restassured.RestAssured;

/**
 * @title BaseTest.java
 * @description
 * @author
 * @date 2022-04-15 02:44:45
 */
public abstract class BaseTest {

    protected static TypeReference<BaseResult<Dict>> typeReference = new TypeReference<BaseResult<Dict>>() {};

    @LocalServerPort
    int port;

    @BeforeAll
    public void init() {
        RestAssured.port = port;
    }

    public static BaseResult<Dict> get(String url, Map<String, Object> param) {
        return JSON.parseObject(given().formParams(param).when().get(url).asString(), typeReference);
    }

    public static BaseResult<Dict> post(String url, Map<String, Object> param) {
        return JSON.parseObject(given().formParams(param).when().post(url).asString(), typeReference);
    }
}
