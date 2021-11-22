package com.liukun.shortdomain.controller;

import cn.hutool.json.JSONUtil;
import com.google.common.base.Charsets;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * <b>Class name</b>:
 * </p>
 * <p>
 * <b>Class description</b>: Class description goes here.
 * </p>
 * <p>
 * <b>Author</b>: kunliu
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 2021/10/7       kunliu        1.0          Initial Creation
 *
 * </pre>
 *
 * @author kunliu
 * @date 2021/10/7
 * </p>
 */
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class ShortDomainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void createShortUrl() throws Exception {
        String url = "https://blog.csdn.net/diaochuangqi7487/article/details/102134868";
        Map<String, String> params = new LinkedHashMap<>();
        params.put("url", url);
        MvcResult mvcResult = mockMvc.perform(post("/api/createShortUrl").
                contentType(MediaType.APPLICATION_JSON).content(JSONUtil.toJsonStr(params))).andReturn();
        log.info("return result:{}", mvcResult.getResponse().getContentAsString(Charsets.UTF_8));
    }

    @Test
    public void getLongUrl() throws Exception {
//        String url = "https://blog.csdn.net/diaochuangqi7487/article/details/102134868";
//        Map<String, String> params = new LinkedHashMap<>();
//        params.put("url", url);
        String url = "0QQJI5ZJ";
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/api/getLongUrl?url=" + url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        log.info("return result:{}", mvcResult.getResponse().getContentAsString(Charsets.UTF_8));
    }
}