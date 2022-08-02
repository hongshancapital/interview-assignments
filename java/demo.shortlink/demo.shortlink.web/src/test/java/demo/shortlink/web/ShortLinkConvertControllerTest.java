package demo.shortlink.web;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.xinerde.demo.shortlink.service.ShortLinkService;
import com.xinerde.demo.shortlink.web.boot.StartApplication;

import lombok.extern.slf4j.Slf4j;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = StartApplication.class)
@Slf4j
@AutoConfigureMockMvc
public class ShortLinkConvertControllerTest {

    @Autowired
    private MockMvc mock;
    @Autowired
    private ShortLinkService shortLinkService;

    @Test
    public void fetchShortLinkTest() throws Exception{
        String longLink = "https://www.baidu.com/test";
        MvcResult mvcResult = mock.perform(
                MockMvcRequestBuilders.post("/fetchShortLink")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8")
                        .param("longLink",longLink)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("short Result: {}", content);
    }

    @Test
    public void fetchShortLinkParameterEmptyTest() throws Exception{
        String longLink = "";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/fetchShortLink")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("longLink",longLink)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("short Result: {}", content);
    }

    @Test
    public void fetchShortLinkParameterCheckErrorTest() throws Exception{
        String longLink = "1234sfd**";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/fetchShortLink")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("longLink",longLink)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("short Result: {}", content);
    }

    @Test
    public void fetchShortLinkServiceErrorTest() throws Exception{
        String longLink = "service_error_test";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/fetchShortLink")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("longLink",longLink)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("500")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("short Result: {}", content);
    }

    @Test
    public void fetchLongLinkErrorTest() throws Exception{
        String shortLink = "http://t.cn/0";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/fetchLongLink")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortLink",shortLink)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

    @Test
    public void fetchLongLinkSuccessTest() throws Exception{
        String longLink = "https://www.baidu.com/test";
        String shortLink = shortLinkService.fetchShortLink(longLink);
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/fetchLongLink")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortLink",shortLink)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

    @Test
    public void fetchLongLinkParameterEmptyTest() throws Exception{
        String longLink = "";
        String shortLink = shortLinkService.fetchShortLink(longLink);
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/fetchLongLink")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortLink",shortLink)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

    @Test
    public void fetchLongLinkNotExistsTest() throws Exception{
        String shortLink = "https://t.cn/qwerq";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/fetchLongLink")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortLink",shortLink)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("600")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

    @Test
    public void fetchLongLinkServiceErrorTest() throws Exception{
        String shortLink = "service_error_test";
        MvcResult mvcResult = mock.perform(
                        MockMvcRequestBuilders.post("/fetchLongLink")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .characterEncoding("UTF-8")
                                .param("shortLink",shortLink)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("500")) //比较json结果值
                .andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        response.setCharacterEncoding("UTF-8");
        String content = response.getContentAsString();
        log.info("long Result: {}", content);
    }

} 