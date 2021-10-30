package com.yujianfei;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortDNServiceApplication.class)
@AutoConfigureMockMvc
@Slf4j
public class ShortDNServiceApplicationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testSaveShortUrl() {
        log.info("testSaveShortUrl start...");
        String reqjson = "{\r\n" +
                "    \"longDN\": \"https://www.baidu.com/gggg\"\r\n" +
                "}";
        try {

            MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/shortdn/save")
                    .content(reqjson)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON);
            mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("testSaveShortUrl end...");
    }

    @Test
    public void testGetLongDN() {
        log.info("testGetLongDN start...");
        try {

            MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.get("/shortdn/url/Unueym")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON);
            mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("testGetShortDN end...");
    }

    @Test
    public void testSaveShortUrlError() {
        log.info("testSaveShortUrlError start...");
        String reqjson = "{\r\n" +
                "    \"longDN\": \"https//www.baidu.com/gggg\"\r\n" +
                "}";
        try {
            MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/shortdn/save")
                    .content(reqjson)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON);
            mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("testSaveShortUrlError end...");
    }

    @Test
    public void testSaveShortUrlLongError() {
        log.info("testSaveShortUrlLongError start...");
        String reqjson = "{\r\n" +
                "    \"longDN\": \"https//www.baidu.com/gggggggggggggggggggggggggggggg\"\r\n" +
                "}";
        try {
            MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/shortdn/save")
                    .content(reqjson)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON);
            mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("testSaveShortUrlLongError end...");
    }

    @Test
    public void testSaveShortUrlNullError() {
        log.info("testSaveShortUrlNullError start...");
        String reqjson="{\r\n" +
                "    \"longDN\": \"\"\r\n" +
                "}";
        try {
            MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.post("/shortdn/save")
                    .content(reqjson)
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON);
            mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("testSaveShortUrlNullError end...");
    }

    @Test
    public void testGetShortDNError() {
        log.info("testGetShortDNError...");
        try {

            MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.get("/shortdn/url/siresnadfi")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON);
            mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("testGetShortDNError end...");
    }

    @Test
    public void testGetShortdnNullError() throws Exception  {
        log.info("testGetShortdnNullError start...");

        try {
            MockHttpServletRequestBuilder loginRequestBuilder = MockMvcRequestBuilders.get("/shortdn/url/null")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_JSON);
            mockMvc.perform(loginRequestBuilder).andDo(MockMvcResultHandlers.print());

        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("testGetShortdnNullError end...");
    }

}
