package com.tataazy.work.domainmanage.controller;

import com.tataazy.work.domainmanage.DomainManageApplicationTests;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@WebAppConfiguration
public class UrlControllerTest extends DomainManageApplicationTests {

    protected MockMvc mockMvc;

    @Autowired
    protected WebApplicationContext wac;

    @Before()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void generateShortUrl() {
        String api = "/domainapi/url/generateshorturl";
        String requestBody = "https://github.com/scdt-china/interview-assignments";

        try {
            mockMvc.perform(MockMvcRequestBuilders.post(api)
                                                  .accept(MediaType.APPLICATION_JSON)
                                                  .contentType(MediaType.APPLICATION_JSON)
                                                  .content(requestBody.getBytes())
            ).andExpect(MockMvcResultMatchers.status().isOk())
            .andReturn();
        } catch (Exception e) {
            Assert.fail();
        }

        String api2 =  "/domainapi/url/getnormalurl?shortUrl=https://frY3iMRJ";
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(api2)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.fail();
        }

    }

    @Test
    public void generateShortUrlInvalid() {
        String api = "/domainapi/url/generateshorturl";
        String requestBody = "fgs:/github.com/scdt-china/interview-assignments";

        try {
            mockMvc.perform(MockMvcRequestBuilders.post(api)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody.getBytes())
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.fail();
        }

    }

    @Test
    public void generateShortUrlInvalidTooLong() {

        String api = "/domainapi/url/generateshorturl";
        String requestBody = "https://github.com/scdt-china/interview-assignments?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";

        try {
            mockMvc.perform(MockMvcRequestBuilders.post(api)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(requestBody.getBytes())
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getNormalUrl() {
        String api =  "/domainapi/url/getnormalurl?shortUrl=https://frY3ifdfdJ";
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(api)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.fail();
        }
    }


    @Test
    public void getNormalUrlInvalid() {
        String api =  "/domainapi/url/getnormalurl?shortUrl=htps://frY3dMRJ";
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(api)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.fail();
        }
    }

    @Test
    public void getNormalUrlInvalidTooLong() {
        String api =  "/domainapi/url/getnormalurl?shortUrl=htps://frY3dMRJfdfgsgsresfsg";
        try {
            mockMvc.perform(MockMvcRequestBuilders.get(api)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(MockMvcResultMatchers.status().isOk())
                    .andReturn();
        } catch (Exception e) {
            Assert.fail();
        }
    }



}
