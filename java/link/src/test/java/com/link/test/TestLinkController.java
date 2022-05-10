package com.link.test;

import com.link.LinkApplication;
import com.link.web.LinkController;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

/**
 * @auth 十三先生
 * @date 2022-04-18
 * @desc
 */

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = LinkApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TestLinkController {
    @Autowired
    private LinkController linkController;
    private MockMvc mockMvc;

    @BeforeAll
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(linkController).build();
    }

    @Test
    public void generateShortLinkByLongLink() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/link/generateShortLinkByLongLink.action")
                .param("longLink", "https://wenku.baidu.com/view/5e6b5028bdd5b9f3f90f76c66137ee06eff94ec8.html?id=1234"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void generateShortLinkByLongLinkParamNull() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/link/generateShortLinkByLongLink.action")
                .param("longLink", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        log.info(mvcResult.getResponse().getContentAsString());
    }


    @Test
    public void queryLongLinkByShortLink() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/link/generateShortLinkByLongLink.action")
                .param("longLink", "https://wenku.baidu.com/view/5e6b5028bdd5b9f3f90f76c66137ee06eff94ec8.html?id=1234"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String shortLink = mvcResult.getResponse().getContentAsString();
        log.info("shortLink={}", shortLink);
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/link/queryLongLinkByShortLink.action")
                .param("shortLink", "11222"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }
    @Test
    public void queryLongLinkByShortLinkParamNull() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/link/generateShortLinkByLongLink.action")
                .param("longLink", "https://wenku.baidu.com/view/5e6b5028bdd5b9f3f90f76c66137ee06eff94ec8.html?id=1234"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        String shortLink = mvcResult.getResponse().getContentAsString();
        log.info("shortLink={}", shortLink);
        mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/link/queryLongLinkByShortLink.action")
                .param("shortLink", ""))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }
}
