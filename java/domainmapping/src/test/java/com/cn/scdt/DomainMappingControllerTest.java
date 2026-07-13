package com.cn.scdt;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DomainMappingControllerTest {

    @Autowired
    private WebApplicationContext context;


    private MockMvc mockMvc;

    @Before
    public void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * 获取短地址
     * @throws Exception
     */
    @Test
    public void getShortUrl() throws Exception {
        StringBuffer url = new StringBuffer("http://www.baidu.com");
      /*  for(int i=0;i<10000000;i++){
            url.append("/").append(i);*/
            mockMvc.perform(MockMvcRequestBuilders.get("/getShortUrl")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .param("longUrl" , url.toString()))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
      //  }

    }


    @Test
    public void getShortUrlByEmptyLongUrl() throws Exception {
            mockMvc.perform(MockMvcRequestBuilders.get("/getShortUrl")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                    .param("longUrl" , ""))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(MockMvcResultHandlers.print());
    }

    /**
     * 获取长地址
     * @throws Exception
     */
    @Test
    public void getLongUrl() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/getLongUrl")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.parseMediaType("application/json;charset=UTF-8"))
                .param("shortUrlKey" , "dvMCx3xD"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}
