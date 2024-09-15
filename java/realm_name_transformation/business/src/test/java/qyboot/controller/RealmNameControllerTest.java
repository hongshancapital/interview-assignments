package qyboot.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.assertTrue;

/**
 * @author zk
 */
@SpringBootTest(classes = {WebApplicationContext.class})
@ComponentScan(basePackages = "com.qyboot")
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class RealmNameControllerTest {


    @Autowired
    private MockMvc mvc;

    private ObjectMapper objectMapper = new ObjectMapper();


    @Test
    public void testGetShortUrl() throws Exception {
        String url = "/api/short/url?url=";
        String longUrl = "http://www.baidu.com/dasd/dasdasdasdwq312?dasdasd=10";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        String result = objectMapper.readValue(response.getContentAsString(), new TypeReference<String>() {
        });
        assertTrue(result != null);
    }

    @Test
    public void shortUrlToLongUrl() throws Exception {
        String url = "/api/realm/name?url=";
        String longUrl = "http://www.baidu.com/dasd/dasdasdasdwq312?dasdasd=10";
        MvcResult shortResult = mvc.perform(MockMvcRequestBuilders.get("/api/short/url?url=" + longUrl)).
                andReturn();
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + shortResult.getResponse().getContentAsString())).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertTrue(status == HttpStatus.OK.value());
        String result = response.getContentAsString();
        assertTrue(result != null);
        mvcResult = mvc.perform(MockMvcRequestBuilders.get(url + shortResult.getResponse().getContentAsString())).
                andReturn();
        response = mvcResult.getResponse();
        String result1 = response.getContentAsString();
        assertTrue(result.equals(result1));
    }
}
