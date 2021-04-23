package com.interview.shorter.controller;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URLDecoder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShorterControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void testShorter() throws Exception {
        String orinalString = "http://www.google.com";
        String source = URLDecoder.decode(orinalString, "UTF-8");
        MvcResult r = mockMvc.perform(get("/shorter?source=" + source))
                .andExpect(status().isOk()).andReturn();
        String shortCode = r.getResponse().getContentAsString();

        MvcResult r2 = mockMvc.perform(get("/get?shortCode=" + shortCode))
                .andExpect(status().isOk()).andReturn();
        Assert.assertEquals(orinalString, r2.getResponse().getContentAsString());


    }

}
