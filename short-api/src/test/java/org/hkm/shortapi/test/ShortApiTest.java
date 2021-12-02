package org.hkm.shortapi.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hkm.ShortApiApplication;
import org.hkm.model.ShortUrl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
@SpringBootTest(classes = ShortApiApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ShortApiTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    private String testUrl;

    @Before
    public void setUp()
    {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void transfer() throws JsonProcessingException {

        ShortUrl data = new ShortUrl();
        data.setOriginUrl("http://www.baidu.com/asd");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writer().writeValueAsString(data);

        RequestBuilder request = post("/short").contentType(MediaType.APPLICATION_JSON).content(json);
        try {
            String url = mvc.perform(request).andDo(print()).andReturn().getResponse().getContentAsString();

            this.testUrl = url.substring(url.length()-8);
            System.out.println(this.testUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferDuplicate() throws JsonProcessingException {

        ShortUrl data = new ShortUrl();
        data.setOriginUrl("http://www.baidu.com/asd");

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writer().writeValueAsString(data);

        RequestBuilder request = post("/short").contentType(MediaType.APPLICATION_JSON).content(json);
        try {
            String url = mvc.perform(request).andDo(print()).andReturn().getResponse().getContentAsString();

            this.testUrl = url.substring(url.length()-8);
            System.out.println(this.testUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void transferNull() throws JsonProcessingException {

        ShortUrl data = new ShortUrl();

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writer().writeValueAsString(data);

        RequestBuilder request = post("/short").contentType(MediaType.APPLICATION_JSON).content(json);
        try {
            String url = mvc.perform(request).andDo(print()).andReturn().getResponse().getContentAsString();

            this.testUrl = url.substring(url.length()-8);
            System.out.println(this.testUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @After
    public void redirect() throws InterruptedException {
        RequestBuilder request = get("/short/"+this.testUrl);
        try {
            mvc.perform(request).andDo(print()).andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void redirectUnknow() throws InterruptedException {
        RequestBuilder request = get("/short/unknow");
        try {
            mvc.perform(request).andDo(print()).andReturn().getResponse().getContentAsString();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
