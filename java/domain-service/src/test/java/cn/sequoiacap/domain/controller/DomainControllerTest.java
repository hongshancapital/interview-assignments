package cn.sequoiacap.domain.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.Charset;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 单元测试
 * @author liuhao
 * @date 2021/12/11
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DomainControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }


    @Test
    public void whenGeneratorSuccess() throws Exception {
        String result = mockMvc.perform(post("/domain").contentType(MediaType.APPLICATION_JSON)
                        .content("http://localhost:8080/test/adad?ddd=123&dff=234234"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result);
    }

    @Test
    public void whenGetLongLinkSuccess() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(post("/domain").contentType(MediaType.APPLICATION_JSON)
                        .content("http://localhost:8080/test/adad?ddd=123&dff=234234"))
                .andExpect(status().isOk())
                .andReturn().getResponse();
        ObjectMapper objectMapper = new ObjectMapper();
        Map map = objectMapper.readValue(response.getContentAsString(Charset.defaultCharset()), Map.class);
        String data = (String) map.get("data");
        String result2 = mockMvc.perform(get("/domain").param("url", data))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result2);
    }

    @Test
    public void whenGetLongLinkFail1() throws Exception {
        String data = "http://localhost/";
        String result2 = mockMvc.perform(get("/domain").param("url", data))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result2);
    }

    @Test
    public void whenGetLongLinkFail2() throws Exception {
        String data = "http://localhost/test";
        String result2 = mockMvc.perform(get("/domain").param("url", data))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(Charset.defaultCharset());
        System.out.println(result2);
    }
}
