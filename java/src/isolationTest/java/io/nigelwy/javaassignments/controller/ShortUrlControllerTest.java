package io.nigelwy.javaassignments.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nigelwy.javaassignments.api.response.GenerateShorturlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static io.nigelwy.javaassignments.Constants.ORIGIN_URL;
import static io.nigelwy.javaassignments.Constants.SHORT_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class ShortUrlControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void should_return_bad_request_given_invalid_url() throws Exception {
        mockMvc.perform(post("/")
                        .param("originUrl", "bad"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    void should_generate_short_url() throws Exception {
        mockMvc.perform(post("/")
                        .param("originUrl", ORIGIN_URL))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.originUrl").value(ORIGIN_URL))
                .andExpect(jsonPath("$.shortUrl").isNotEmpty());
    }

    @Test
    void should_return_origin_url_given_mapping_exists() throws Exception {
        var mvcResult = mockMvc.perform(post("/")
                        .param("originUrl", ORIGIN_URL))
                .andReturn();
        var contentAsString = mvcResult.getResponse().getContentAsString();
        var response = new ObjectMapper().readValue(contentAsString, GenerateShorturlResponse.class);

        mockMvc.perform(get("/{shortUrl}", response.getShortUrl()))
                .andExpect(status().isFound())
                .andExpect(header().string(HttpHeaders.LOCATION, ORIGIN_URL));
    }

    @Test
    void should_return_not_found_given_mapping_not_exists() throws Exception {
        mockMvc.perform(get("/{shortUrl}", SHORT_URL))
                .andExpect(status().isNotFound());
    }

}