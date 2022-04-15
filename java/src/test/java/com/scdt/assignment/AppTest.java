package com.scdt.assignment;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.scdt.assignment.base.BaseResult;
import com.scdt.assignment.base.BaseTest;
import com.scdt.assignment.config.Msg;
import com.scdt.assignment.controller.bo.ShortUrlBo;
import com.scdt.assignment.repository.ShortUrlRepository;
import com.scdt.assignment.service.ShortUrlService;
import com.xiesx.fastboot.base.result.R;

import cn.hutool.core.lang.Dict;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.log4j.Log4j2;

/**
 * @title AppTest.java
 * @description
 * @author
 * @date 2022-04-15 15:56:38
 */
@Log4j2
@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = AppApplication.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class AppTest extends BaseTest {

    public final static String longurl = "https://github.com/scdt-china/interview-assignments/tree/master/java";

    public final static String api_create = "/api/short-url/create";

    public final static String api_query = "/api/short-url/query";

    @Autowired
    ShortUrlService mShortUrlService;

    @Autowired
    ShortUrlRepository mShortUrlRepository;

    @Override
    @BeforeAll
    public void init() {
        super.init();
        mShortUrlRepository.deleteAll();
    }

    @AfterAll
    public void destroy() {
        log.info("rows count:{}", mShortUrlRepository.count());
    }

    @Test
    @Order(1)
    public void test_controller() {
        Dict params = Dict.create();

        BaseResult<Dict> result = get(api_create, params.set("longurl", longurl));
        String shorturl = R.eval(result, "$.data.shorturl").toString();
        assertTrue(result.isSuccess());
        assertNotNull(shorturl);

        result = get(api_query, params.set("shorturl", shorturl));
        assertTrue(result.isSuccess());
        assertEquals(R.eval(result, "$.data.longurl"), longurl);
    }

    @Test
    @Order(2)
    public void test_controller_catch() {
        Dict params = Dict.create();

        BaseResult<Dict> result = get(api_create, params.set("longurl", "abc"));
        assertEquals(result.getCode(), Msg.INVALID.getStatus());

        result = get(api_create, params.set("longurl", "http://github.com"));
        assertEquals(result.getCode(), R.CODE_SUCCESS.intValue());

        result = get(api_query, params.set("shorturl", "xxxx"));
        assertEquals(result.getCode(), Msg.INVALID.getStatus());

        result = get(api_query, params.set("shorturl", "http://github.com"));
        assertEquals(result.getCode(), Msg.INVALID.getStatus());

        result = get(api_query, params.set("shorturl", "http://xxxx.cn/abcdefgh9"));
        assertEquals(result.getCode(), Msg.INVALID.getStatus());

        result = get(api_query, params.set("shorturl", "http://domain.cn/abcdefgh9"));
        assertEquals(result.getCode(), Msg.LENGTH.getStatus());

        result = get(api_query, params.set("shorturl", "http://domain.cn/abcdefgh"));
        assertEquals(result.getCode(), Msg.NOT_EXIST.getStatus());

        result = get(api_query, params.set("shorturl", "https://domain.cn/abcdefgh"));
        assertEquals(result.getCode(), Msg.NOT_EXIST.getStatus());
    }

    @Test
    @Order(3)
    public void test_service() {
        ShortUrlBo su1 = mShortUrlService.create(longurl);
        ShortUrlBo su2 = mShortUrlService.query(su1.getShorturl());
        assertEquals(su1, su2);
    }

    @Test
    @Order(4)
    public void test_repository() {
        for (int i = 0; i < 10000; i++) {
            String longurl = StrUtil.format("https://{}.com/{}/{}", RandomUtil.randomString(6), RandomUtil.randomStringUpper(8), RandomUtil.randomString(10));
            ShortUrlBo su1 = mShortUrlService.create(longurl);
            assertNotNull(su1.getShorturl());
        }
    }
}
