package com.domain;

import com.domain.controller.DomainController;
import com.domain.model.DomainDTO;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author: xielongfei
 * @date: 2022/01/10
 * @description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DomainControllerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private DomainController domainController;

    public MockMvc mockMvc;

    @BeforeMethod
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(domainController).build();
    }

    /**
     * {@link com.domain.controller.DomainController#getShortDomain(DomainDTO)}
     */
    @Test
    public void testGetShortDomain() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getShortDomain")
                        .contentType(MediaType.TEXT_HTML_VALUE)
                        .queryParam("longUrl", "https://www.baidu.com"))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }

    /**
     *{@link com.domain.controller.DomainController#getLongDomain(DomainDTO)}
     */
    @Test(dependsOnMethods = {"testGetShortDomain"})
    public void testGetLongDomain() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/getLongDomain")
                .contentType(MediaType.TEXT_HTML_VALUE)
                .queryParam("shortUrl", "http://do.com/l61PIviH"))
                .andExpect(status().isOk())
                .andExpect(content().string(notNullValue()));
    }
}
