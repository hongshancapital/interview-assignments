package com.scdt.china.shorturl.controller;

import com.scdt.china.shorturl.configuration.ApplicationProperties;
import com.scdt.china.shorturl.exception.BusinessException;
import com.scdt.china.shorturl.exception.BusinessExceptions;
import com.scdt.china.shorturl.service.ShortUrlService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.net.URI;

@ExtendWith({SpringExtension.class, MockitoExtension.class})
@WebMvcTest(controllers = ShortUrlController.class)
public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ShortUrlService shortUrlService;

    @SpyBean
    private ApplicationProperties applicationProperties;

    @BeforeEach
    public void setUp() {
        Mockito.when(shortUrlService.generate("https://www.google.com")).thenReturn("ABC");
        Mockito.when(shortUrlService.expand("ABC")).thenReturn("https://www.google.com");
        Mockito.when(applicationProperties.getBaseUrl()).thenReturn("https://s.scdt.com/");
    }

    @Test
    public void generateShortUrlAndAccess() throws Exception {
        MvcResult generateResult = mockMvc.perform(MockMvcRequestBuilders.post("/")
                        .contentType(MediaType.TEXT_PLAIN).content("https://www.google.com"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String shortUrl = generateResult.getResponse().getContentAsString();
        Assertions.assertTrue(shortUrl.startsWith("https://s.scdt.com/"));

        String path = URI.create(shortUrl).getPath();

        MvcResult expandResult = mockMvc.perform(MockMvcRequestBuilders.get(path).accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        String fullUrl = expandResult.getResponse().getContentAsString();
        Assertions.assertEquals("https://www.google.com", fullUrl);
    }


    @Test
    public void badCase() throws Exception {
        Mockito.when(shortUrlService.expand("10000")).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/10000").accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();

        Mockito.when(shortUrlService.expand("110-222")).thenThrow(new BusinessException(BusinessExceptions.INVALID_CODE));
        Mockito.when(shortUrlService.expand("10086-222")).thenThrow(new BusinessException(BusinessExceptions.INVALID_CODE));
        mockMvc.perform(MockMvcRequestBuilders.get("/110-222").accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();

        mockMvc.perform(MockMvcRequestBuilders.get("/10086-222").accept(MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError()).andReturn();
    }

}