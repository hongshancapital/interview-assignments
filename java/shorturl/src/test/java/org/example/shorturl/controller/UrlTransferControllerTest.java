package org.example.shorturl.controller;

import cn.hutool.cache.Cache;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.shorturl.common.ApiResult;
import org.example.shorturl.enums.ResultCodeEnum;
import org.example.shorturl.generator.IdGeneratorWrapper;
import org.example.shorturl.modal.LongUrlDto;
import org.example.shorturl.modal.ShortUrlDto;
import org.example.shorturl.util.ShortUrlUtil;
import org.example.shorturl.util.UrlCacheUtil;
import org.hamcrest.core.Is;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author bai
 * @date 2022/3/20 0:52
 */
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class UrlTransferControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private IdGeneratorWrapper wrapper;
    /** 映射器 */
    ObjectMapper mapper = new ObjectMapper();
    
    /**
     * 测试 长url==>短url
     * [预期结果] 成功
     */
    @Test
    @SneakyThrows
    public void test01() {
        String url = "http://halo.baicloud.top";
        LongUrlDto longUrlDto = new LongUrlDto().setUrl(url);
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/longUrlToShortUrl")
                                                                     .contentType(MediaType.APPLICATION_JSON)
                                                                     .content(mapper.writeValueAsString(longUrlDto)))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andExpect(MockMvcResultMatchers.jsonPath("success", Is.is(true)))
                                      .andExpect(MockMvcResultMatchers.jsonPath("code", Is.is(ResultCodeEnum.SUCCESS.getCode())))
                                      .andExpect(MockMvcResultMatchers.jsonPath("appCode", Is.is(ResultCodeEnum.SUCCESS.getAppCode())))
                                      .andDo(MockMvcResultHandlers.print())
                                      .andReturn();
        ApiResult shortUrlDtoApiResult = mapper.readValue(mvcResult1.getResponse()
                                                                    .getContentAsString(), ApiResult.class);
        ShortUrlDto parse = mapper.readValue(mapper.writeValueAsString(shortUrlDtoApiResult.getData()), ShortUrlDto.class);
        String targetShortUrl = parse.getUrl();
        
        ShortUrlDto shortUrlDto = new ShortUrlDto().setUrl(targetShortUrl);
        mockMvc.perform(MockMvcRequestBuilders.post("/shortUrlToLongUrl")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(shortUrlDto)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("success", Is.is(true)))
               .andExpect(MockMvcResultMatchers.jsonPath("code", Is.is(ResultCodeEnum.SUCCESS.getCode())))
               .andExpect(MockMvcResultMatchers.jsonPath("appCode", Is.is(ResultCodeEnum.SUCCESS.getAppCode())))
               .andDo(MockMvcResultHandlers.print())
               .andReturn();
    }
    
    /**
     * 测试 短url==>长url 过期
     * [预期结果] 失败
     */
    @Test
    @SneakyThrows
    public void test02() {
        ShortUrlDto shortUrlDto = new ShortUrlDto().setUrl("http://bai.cn/ZIAjn731");
        mockMvc.perform(MockMvcRequestBuilders.post("/shortUrlToLongUrl")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(shortUrlDto)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("success", Is.is(false)))
               .andExpect(MockMvcResultMatchers.jsonPath("code", Is.is(ResultCodeEnum.FAILURE.getCode())))
               .andExpect(MockMvcResultMatchers.jsonPath("appCode", Is.is(ResultCodeEnum.FAILURE.getAppCode())))
               .andDo(MockMvcResultHandlers.print())
               .andReturn();
    }
    
    /**
     * 测试 短url==>长url 短域名不正确
     * [预期结果] 失败
     */
    @Test
    @SneakyThrows
    public void test03() {
        ShortUrlDto shortUrlDto = new ShortUrlDto().setUrl("http://ba.cn/ZIAjn731");
        mockMvc.perform(MockMvcRequestBuilders.post("/shortUrlToLongUrl")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(shortUrlDto)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("success", Is.is(false)))
               .andExpect(MockMvcResultMatchers.jsonPath("code", Is.is(ResultCodeEnum.FAILURE.getCode())))
               .andExpect(MockMvcResultMatchers.jsonPath("appCode", Is.is(ResultCodeEnum.FAILURE.getAppCode())))
               .andDo(MockMvcResultHandlers.print())
               .andReturn();
    }
    
    /**
     * 测试 短url==>长url 格式非法
     * [预期结果] 失败
     */
    @Test
    @SneakyThrows
    public void test04() {
        ShortUrlDto shortUrlDto = new ShortUrlDto().setUrl("http://bai.cn/11ZIAjn731");
        mockMvc.perform(MockMvcRequestBuilders.post("/shortUrlToLongUrl")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(shortUrlDto)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("success", Is.is(false)))
               .andExpect(MockMvcResultMatchers.jsonPath("code", Is.is(ResultCodeEnum.FAILURE.getCode())))
               .andExpect(MockMvcResultMatchers.jsonPath("appCode", Is.is(ResultCodeEnum.FAILURE.getAppCode())))
               .andDo(MockMvcResultHandlers.print())
               .andReturn();
    }
    
    /**
     * 测试 长url==>短url 缓存命中
     * [预期结果] 成功
     */
    @Test
    @SneakyThrows
    public void test05() {
        String url = "https://halo.baicloud.top";
        LongUrlDto longUrlDto = new LongUrlDto().setUrl(url);
        mockMvc.perform(MockMvcRequestBuilders.post("/longUrlToShortUrl")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(longUrlDto)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("success", Is.is(true)))
               .andExpect(MockMvcResultMatchers.jsonPath("code", Is.is(ResultCodeEnum.SUCCESS.getCode())))
               .andExpect(MockMvcResultMatchers.jsonPath("appCode", Is.is(ResultCodeEnum.SUCCESS.getAppCode())))
               .andDo(MockMvcResultHandlers.print())
               .andReturn();
    }
    
    /**
     * 测试 短url==>长url https
     * [预期结果] 失败
     */
    @Test
    @SneakyThrows
    public void test06() {
        ShortUrlDto shortUrlDto = new ShortUrlDto().setUrl("https://bai.cn/ZIAjn731");
        mockMvc.perform(MockMvcRequestBuilders.post("/shortUrlToLongUrl")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(shortUrlDto)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("success", Is.is(false)))
               .andExpect(MockMvcResultMatchers.jsonPath("code", Is.is(ResultCodeEnum.FAILURE.getCode())))
               .andExpect(MockMvcResultMatchers.jsonPath("appCode", Is.is(ResultCodeEnum.FAILURE.getAppCode())))
               .andDo(MockMvcResultHandlers.print())
               .andReturn();
    }
    
    /**
     * 测试 短url==>长url http
     * [预期结果] 失败
     */
    @Test
    @SneakyThrows
    public void test07() {
        ShortUrlDto shortUrlDto = new ShortUrlDto().setUrl("http://bai.cn/ZIAjn731");
        mockMvc.perform(MockMvcRequestBuilders.post("/shortUrlToLongUrl")
                                              .contentType(MediaType.APPLICATION_JSON)
                                              .content(mapper.writeValueAsString(shortUrlDto)))
               .andExpect(MockMvcResultMatchers.status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("success", Is.is(false)))
               .andExpect(MockMvcResultMatchers.jsonPath("code", Is.is(ResultCodeEnum.FAILURE.getCode())))
               .andExpect(MockMvcResultMatchers.jsonPath("appCode", Is.is(ResultCodeEnum.FAILURE.getAppCode())))
               .andDo(MockMvcResultHandlers.print())
               .andReturn();
    }
    
    /**
     * 测试 长url==>短url https
     * [预期结果] 失败
     */
    @Test
    @SneakyThrows
    public void test08() {
        String url = "https://halo.baicloud.top";
        LongUrlDto longUrlDto = new LongUrlDto().setUrl(url);
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/longUrlToShortUrl")
                                                                     .contentType(MediaType.APPLICATION_JSON)
                                                                     .content(mapper.writeValueAsString(longUrlDto)))
                                      .andExpect(MockMvcResultMatchers.status().isOk())
                                      .andExpect(MockMvcResultMatchers.jsonPath("success", Is.is(true)))
                                      .andExpect(MockMvcResultMatchers.jsonPath("code", Is.is(ResultCodeEnum.SUCCESS.getCode())))
                                      .andExpect(MockMvcResultMatchers.jsonPath("appCode", Is.is(ResultCodeEnum.SUCCESS.getAppCode())))
                                      .andDo(MockMvcResultHandlers.print())
                                      .andReturn();
    }
    
    /**
     * 测试 code生成性能及重复性
     * [预期结果] 成功
     */
    @Test
    @SneakyThrows
    public void test10() {
        ArrayList<String> list = new ArrayList<>();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000000; i++) {
            String decimal = ShortUrlUtil.decimalToSixtyTwo(wrapper.nextLongId());
            list.add(decimal);
        }
        long end = System.currentTimeMillis();
        long count = list.stream().distinct().count();
        long cost = end - start;
        log.info("生成code:[{}]pcs, cost:[{}]ms, avg:[{}]/ms", count, cost, count / cost);
    }
    
    /**
     * 测试 code生成性能及重复性
     * [预期结果] 成功
     */
    @Test
    @SneakyThrows
    public void test11() {
        for (int i = 0; i < 10; i++) {
            test10();
        }
    }
    
    /**
     * 测试 code生成及回放
     * [预期结果] 成功
     */
    @Test
    @SneakyThrows
    public void test12() {
        for (int i = 0; i < 1000; i++) {
            long l1 = wrapper.nextLongId();
            String decimal = ShortUrlUtil.decimalToSixtyTwo(l1);
            long l2 = ShortUrlUtil.sixtyTwoToDecimal(decimal);
            assertEquals(l1, l2);
        }
    }
    
    /**
     * 测试 特殊code身生成
     * [预期结果] 成功
     */
    @Test
    @SneakyThrows
    public void test13() {
        String s = ShortUrlUtil.decimalToSixtyTwo(0);
        assertEquals(s, "0");
    }
    
    /**
     * 测试 缓存实例
     * [预期结果] 成功
     */
    @Test
    @SneakyThrows
    public void test14() {
        Cache<String, String> longInstancen = UrlCacheUtil.longUrlInstance();
        Cache<String, String> shortUrlInstance = UrlCacheUtil.shortUrlInstance();
        log.info(String.valueOf(longInstancen.size()));
        log.info(String.valueOf(shortUrlInstance.size()));
    }
    
    private static final Digester DIGESTER = new Digester(DigestAlgorithm.MD5);
    
    /**
     * 测试 长链接摘要长度
     * [预期结果] 成功
     */
    @Test
    @SneakyThrows
    public void test15() {
        String s = DIGESTER.digestHex("https://github.com/scdt-china/interview-assignments");
        log.info(s);
    }
}