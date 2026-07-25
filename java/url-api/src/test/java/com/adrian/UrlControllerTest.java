package com.adrian;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(classes = UrlApiApplication.class)
public class UrlControllerTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(UrlControllerTest.class);

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void before() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void testUrlController() throws Exception {
        String getShortUrl = "http://localhost:8080/getShortUrl";
        String s_baidu = getResponseString(getShortUrl, "https://www.baidu.com/");
        String s_qq = getResponseString(getShortUrl, "https://uland.taobao.com/sem/tbsearch?refpid=mm_26632258_3504122_32538762&keyword=%E6%B7%98%E7%89%B9%28%E5%8E%9F%E6%B7%98%E5%AE%9D%E7%89%B9%E4%BB%B7%E7%89%88%29&clk1=7061f08d45c5b67c6d9e30be11a523d8&upsId=7061f08d45c5b67c6d9e30be11a523d8");
        String s_sina = getResponseString(getShortUrl, "https://www.sina.com.cn/");
        String s_jd = getResponseString(getShortUrl, "https://www.jd.com/?cu=true&utm_source=baidu-pinzhuan&utm_medium=cpc&utm_campaign=t_288551095_baidupinzhuan&utm_term=0f3d30c8dba7459bb52f2eb5eba8ac7d_0_bec02d3f50994a64a1bbb2de97da6460");
        LOGGER.info("百度短域名：" + s_baidu);
        LOGGER.info("淘宝短域名：" + s_qq);
        LOGGER.info("新浪短域名：" + s_sina);
        LOGGER.info("京东短域名：" + s_jd);
        String getOriginalUrl = "http://localhost:8080/findOriginalUrl";
        String baidu = getResponseString(getOriginalUrl, s_baidu);
        String qq = getResponseString(getOriginalUrl, s_qq);
        String sina = getResponseString(getOriginalUrl, s_sina);
        String jd = getResponseString(getOriginalUrl, s_jd);
        LOGGER.info("百度完整域名：" + baidu);
        LOGGER.info("淘宝完整域名：" + qq);
        LOGGER.info("新浪完整域名：" + sina);
        LOGGER.info("京东完整域名：" + jd);
    }

    public String getResponseString(String requestUrl, String param) throws Exception {
        String responseString = mockMvc.perform(MockMvcRequestBuilders.get(requestUrl).param("url", param))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        return responseString;
    }
}
