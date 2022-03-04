package com.hongshan.homework;


import com.hongshan.homework.control.URLConvertController;
import com.hongshan.homework.persist.S2LURLMapperRepository;
import com.hongshan.homework.pojo.S2LURLMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
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

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public class URLConvertTest {

    @Autowired
    private URLConvertController urlConvertController;

    @Autowired
    private S2LURLMapperRepository s2LURLMapperRepository;

    private MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(urlConvertController).build();
    }

    @Test
    public void short2longTestCase() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/url/getLongURL")
                                        .param("shortURL","InyqEr"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    //InyqEr
    @Test
    public void short2LongTestCase2() throws Exception {

        S2LURLMapper s2LURLMapper = new S2LURLMapper();
        s2LURLMapper.setLongURL("www.baidu.com");
        s2LURLMapper.setShortURL("InyqEr");
        s2LURLMapperRepository.save(s2LURLMapper);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/url/getLongURL")
                        .param("shortURL","InyqEr"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());

        s2LURLMapperRepository.deleteById("InyqEr");
    }

    @Test
    public void long2shortTestCase() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/url/getShortURL")
                        .param("longURL","www.baidu.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void long2shortTestCase2() throws Exception {
        S2LURLMapper s2LURLMapper = new S2LURLMapper();
        s2LURLMapper.setLongURL("www.baidu.com");
        s2LURLMapper.setShortURL("InyqEr");
        s2LURLMapperRepository.save(s2LURLMapper);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/url/getShortURL")
                        .param("longURL","www.baidu.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());

        s2LURLMapperRepository.deleteById("InyqEr");
    }

    @Test
    public void long2shortTestCase3() throws Exception {
        S2LURLMapper s2LURLMapper = new S2LURLMapper();
        s2LURLMapper.setLongURL("www.baidu2.com");
        s2LURLMapper.setShortURL("InyqEr");
        s2LURLMapperRepository.save(s2LURLMapper);
        s2LURLMapper.setLongURL("www.baidu3.com");
        s2LURLMapper.setShortURL("JzYRfu");
        s2LURLMapperRepository.save(s2LURLMapper);
        s2LURLMapper.setLongURL("www.baidu4.com");
        s2LURLMapper.setShortURL("vuUjYf");
        s2LURLMapperRepository.save(s2LURLMapper);
        s2LURLMapper.setLongURL("www.baidu5.com");
        s2LURLMapper.setShortURL("BbYNju");
        s2LURLMapperRepository.save(s2LURLMapper);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/api/url/getShortURL")
                        .param("longURL","www.baidu.com"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());

        s2LURLMapperRepository.deleteById("InyqEr");
    }

}
