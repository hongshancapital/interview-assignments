package xiejin.java.interview;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import xiejin.java.interview.controller.DomainController;
import xiejin.java.interview.dto.request.LongUrlRequestDTO;
import xiejin.java.interview.dto.request.ShortUrlRequestDTO;

/**
 * @author xiejin
 * @date 2021/3/20 14:16
 */
@Slf4j
public class DomainControllerTest extends InterviewJavaRanguoApplicationTests {
    private MockMvc mockMvc;

    @Autowired
    private DomainController domainController;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(domainController).build();
//        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void compress() throws Exception {

        LongUrlRequestDTO requestDTO = new LongUrlRequestDTO();
        requestDTO.setOriginalUrl("http://www.sina.com.cn");


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/domain/compress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());

    }


    @Test
    public void uncompress() throws Exception {
        ShortUrlRequestDTO requestDTO = new ShortUrlRequestDTO();
        requestDTO.setShortUrl("http://reABbe");


        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/domain/uncompress")
                .contentType(MediaType.APPLICATION_JSON)
                .content(JSONObject.toJSONString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        log.info(mvcResult.getResponse().getContentAsString());
    }

}
