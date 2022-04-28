package com.zj.domainservice.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class DomainControllerTest {

    @Autowired
    private DomainController domainController;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getShort() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/getShortDomain")
                                .param("longDomain", "https://github.com/scdt-china/interview-assignments/tree/master/java"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("https://4MN/NrHRQrbm"));
    }

    @Test
    void getLong() throws Exception {

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/getLongDomain")
                                .param("shortDomain", "https://4MN/NrHRQrbm"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("https://github.com/scdt-china/interview-assignments/tree/master/java"));
    }
}