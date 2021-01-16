package com.ascmix.surl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class LinkControllerTests {
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void createAndDelete() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String testUrl = "https://www.bing.com";
        Map<String, String> params = new HashMap<>();
        params.put("url", testUrl);
        String jsonBody = objectMapper.writeValueAsString(params);
        String key = mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/link")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBody))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        mockMvc.perform(MockMvcRequestBuilders.get("/" + key))
                .andExpect(MockMvcResultMatchers.status().isFound())
                .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.LOCATION, testUrl));

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/link/" + key))
                .andExpect(MockMvcResultMatchers.status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/" + key))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
