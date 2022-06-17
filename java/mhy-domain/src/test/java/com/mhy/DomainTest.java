package com.mhy;

import com.mhy.controller.DomainController;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class DomainTest {

    private MockMvc mvc;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new DomainController()).build();
    }

    @Test
    public void test() throws Exception {
        String longUrl = "https://baike.baidu.com/item/%E6%95%B0%E6%8D%AE%E4%BB%93%E5%BA%93/381916";

        //调用长链接存储接口
        String shortUrl = testStorageLongUrl(longUrl);
        log.info("DomainTest：长链接：{}，转换为短链接：{}", longUrl, shortUrl);

        //调用获取长链接接口
        if (shortUrl != null) {
            //调用获取长链接接口
            String url = testGetLongUrl(shortUrl);
            log.info("DomainTest：通过短链接：{}，获取长链接：{}", shortUrl, url);
        }
    }

    /**
     * 调用长链接存储接口
     *
     * @param longUrl 长链接
     * @return 短链接
     */
    private String testStorageLongUrl(String longUrl) throws Exception {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("longUrl", longUrl);

        MvcResult result = mvc.perform(
                post("/domain/longUrl/storage")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .params(params)
        ).andReturn();

        int status = result.getResponse().getStatus();
        if (status == 200) {
            return result.getResponse().getContentAsString();
        } else {
            return null;
        }
    }

    /**
     * 调用获取长链接接口
     *
     * @param shortUrl 短链接
     * @return 长链接
     */
    private String testGetLongUrl(String shortUrl) throws Exception {
        MvcResult result = mvc.perform(
                get("/domain/longUrl/get")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .content("shortUrl=" + shortUrl)
        ).andReturn();

        int status = result.getResponse().getStatus();
        if (status == 200) {
            return result.getResponse().getContentAsString();
        } else {
            return null;
        }
    }
}
