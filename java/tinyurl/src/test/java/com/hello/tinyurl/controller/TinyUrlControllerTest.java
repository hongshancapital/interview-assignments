package com.hello.tinyurl.controller;

import com.hello.tinyurl.service.TinyUtlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest
class TinyUrlControllerTest {

    @Resource
    private MockMvc mockMvc;

    @MockBean
    private TinyUtlService tinyUrlService;

    String originalUrl = "https://www.baidu.com/";
    String tinyUrl = "hello";

    final String p404 = "<!DOCTYPE html>\r\n" +
            "<html lang=\"en\">\r\n" +
            "<head>\r\n" +
            "    <title>404</title>\r\n" +
            "    <meta charset=\"UTF-8\">\r\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n" +
            "</head>\r\n" +
            "<body>\r\n" +
            "<h1>404 Not Found!</h1>\r\n" +
            "</body>\r\n" +
            "</html>";

    final String pHome = "<!DOCTYPE html>\r\n" +
            "<html lang=\"en\">\r\n" +
            "<head>\r\n" +
            "    <title>home</title>\r\n" +
            "    <meta charset=\"UTF-8\">\r\n" +
            "    <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/>\r\n" +
            "</head>\r\n" +
            "<body>\r\n" +
            "<h1>hello!</h1>\r\n" +
            "</body>\r\n" +
            "</html>";

    @Test
    public void saveOriginalUrlTest() throws Exception {
        Mockito.when(tinyUrlService.saveOriginalUrl(originalUrl)).thenReturn(tinyUrl);
        MvcResult result = this.mockMvc
                .perform(post("/url/save?originalUrl=" + originalUrl))
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        assertEquals("{\"status\":200,\"description\":null,\"data\":\"" + tinyUrl + "\"}", responseContent);
    }

    @Test
    public void saveOriginalUrlEmptyParameterTest() throws Exception {
        MvcResult result = this.mockMvc
                .perform(post("/url/save?originalUrl="))
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        assertEquals("{\"status\":400,\"description\":\"empty parameter.\",\"data\":null}", responseContent);
    }

    @Test
    public void saveOriginalUrlExceptionTest() throws Exception {
        Mockito.when(tinyUrlService.saveOriginalUrl(originalUrl)).thenThrow(new Exception("Cache must not be null"));
        MvcResult result = this.mockMvc
                .perform(post("/url/save?originalUrl=" + originalUrl))
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        assertEquals("{\"status\":500,\"description\":\"Cache must not be null\",\"data\":null}", responseContent);
    }

    @Test
    public void getOriginalUrlTest() throws Exception {
        Mockito.when(tinyUrlService.getOriginalUrl(tinyUrl)).thenReturn(originalUrl);
        MvcResult result = this.mockMvc
                .perform(get("/i/" + tinyUrl))
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        Assertions.assertEquals("", responseContent);
    }

    @Test
    public void getOriginalUrlReturnNullTest() throws Exception {
        Mockito.when(tinyUrlService.getOriginalUrl(tinyUrl)).thenReturn(null);
        MvcResult result = this.mockMvc
                .perform(get("/i/" + tinyUrl))
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        assertEquals(p404, responseContent);
    }

    @Test
    public void getOriginalUrlExceptionTest() throws Exception {
        Mockito.when(tinyUrlService.getOriginalUrl(tinyUrl)).thenThrow(new Exception("Cache must not be null"));
        MvcResult result = this.mockMvc
                .perform(get("/i/" + tinyUrl))
                .andDo(print())
                .andReturn();
        String responseContent = result.getResponse().getContentAsString();
        assertEquals(p404, responseContent);
    }

}