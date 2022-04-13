package com.tinyurl.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinyurl.response.ResponseCode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class TinyUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;
    private String url = "http://www.test.com/encode.html";

    @Test
    void encode() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/encode.htm").param("url", url))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(mvcResult.getResponse().getContentAsString());
        assertEquals(ResponseCode.CODE_SUCCESS.getValue().toString(), rootNode.get("code").asText());
        System.out.println();
    }

    @Test
    void encodeNull() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/encode.htm"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(mvcResult.getResponse().getContentAsString());
        assertEquals(ResponseCode.CODE_INPUT_NULL.getValue().toString(), rootNode.get("code").asText());
        System.out.println();
    }

    @Test
    void decode() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/encode.htm").param("url", url))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(mvcResult.getResponse().getContentAsString());
        JsonNode dataNode = rootNode.get("data");
        String decodeUrl = dataNode.get("url") != null ? dataNode.get("url").asText() : "";

        mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/decode.htm").param("url", decodeUrl))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        JsonNode decodeRootNode = mapper.readTree(mvcResult.getResponse().getContentAsString());
        JsonNode decodeDataNode = decodeRootNode.get("data");
        String origninalUrl = decodeDataNode.get("url") != null ? decodeDataNode.get("url").asText() : "";

        assertEquals(url, origninalUrl);
    }

    @Test
    void decodeNull() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/decode.htm"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(mvcResult.getResponse().getContentAsString());
        assertEquals(ResponseCode.CODE_INPUT_NULL.getValue().toString(), rootNode.get("code").asText());
        System.out.println();
    }
}