package com.scdt.domain.controller;

import com.scdt.domain.service.IDomainService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.web.context.WebApplicationContext;

import static com.scdt.domain.Constant.DEMO_LONG_URL;
import static com.scdt.domain.Constant.DEMO_SHORT_URL;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 张来刚
 * 2021/10/9 0009.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(DomainConvertController.class)
public class DomainConvertControllerTest {

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IDomainService domainService;


    @Test
    public void convertToShortUrl() throws Exception {
        RequestBuilder  request = post("/domain/convert/getShortUrl")
                .param("longUrl",DEMO_LONG_URL);
        String json = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",is(1)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }

    @Test
    public void convertToLongUrl() throws Exception {
        RequestBuilder  request = post("/domain/convert/getLongUrl")
                .param("shortUrl",DEMO_SHORT_URL);
        String json = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status",is(1)))
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
    }
}
