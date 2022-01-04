package me.huchao.sd.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.huchao.sd.web.Resp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
* <p> ShortDomainController Tester. </p>
* <p> 2021-12-29 12:30:47.893 </p>
*
* @author huchao36
* @version 0.0.1-SNAPSHOT
*/
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ShortDomainControllerTest {

    @Autowired
    private MockMvc mockMvc;


    /**
     *
     * Method: mapping()
     */
    @Test
    public void mappingTest() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/sd/mapping").content("test")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content= result.getResponse().getContentAsString(); //获取响应内容
        ObjectMapper mapper = new ObjectMapper();
        Resp resp = mapper.readValue(content, Resp.class);
        assertEquals(200, resp.getCode());

        mockMvc.perform(MockMvcRequestBuilders.post("/sd/mapping").content("")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();
    }

    /**
    *
    * Method: searchTest()
    */
    @Test
    public void searchTest() throws Exception {
        String origin = "www.baidu.com";
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/sd/mapping").content(origin)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        String content= result.getResponse().getContentAsString(); //获取响应内容
        ObjectMapper mapper = new ObjectMapper();
        Resp resp = mapper.readValue(content, Resp.class);
        assertEquals(200, resp.getCode());
        String shortUrl = resp.getData().toString();

        result = mockMvc.perform(MockMvcRequestBuilders.get("/sd/search")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError())
                .andReturn();

        result = mockMvc.perform(MockMvcRequestBuilders.get("/sd/search").param("sd", "")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        content= result.getResponse().getContentAsString(); //获取响应内容
        mapper = new ObjectMapper();
        resp = mapper.readValue(content, Resp.class);
        assertEquals(500, resp.getCode());

        result = mockMvc.perform(MockMvcRequestBuilders.get("/sd/search").param("sd", "shortUrl")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        content= result.getResponse().getContentAsString(); //获取响应内容
        resp = mapper.readValue(content, Resp.class);
        assertEquals(200, resp.getCode());
        assertNotNull(resp.getData());
        assertEquals("", resp.getData().toString());

        result = mockMvc.perform(MockMvcRequestBuilders.get("/sd/search").param("sd", shortUrl)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        content= result.getResponse().getContentAsString(); //获取响应内容
        resp = mapper.readValue(content, Resp.class);
        assertEquals(200, resp.getCode());
        assertNotNull(resp.getData());
        assertEquals(origin, resp.getData().toString());
    }
}
