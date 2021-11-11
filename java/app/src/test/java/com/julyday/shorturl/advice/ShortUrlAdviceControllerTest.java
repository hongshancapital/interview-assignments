package com.julyday.shorturl.advice;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.julyday.shorturl.service.ShortUrlConverter;
import com.julyday.shorturl.vo.Result;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertTrue;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ShortUrlAdviceControllerTest {

    private static final String PRE_URI = "/api/url/";

    @MockBean
    private ShortUrlConverter shortUrlConverter;

    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testRuntimeExceptionHanlder() throws Exception {
        Mockito.when(shortUrlConverter.generatorShortUrl(Mockito.any(String.class))).thenThrow(new RuntimeException("for test"));
        String url = PRE_URI + "short?longUrl=";
        String longUrl = "http://www.julyday.com/test/01";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.INTERNAL_SERVER_ERROR.value());
        Result<String> result = objectMapper.readValue(response.getContentAsString(), new TypeReference<Result<String>>() {
        });
        assertTrue(result != null);
        assertTrue(result.getCode() == Result.INTERNAL_ERROR);
    }
}
