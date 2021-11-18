package pers.jenche.convertdomain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.net.URLDecoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@WebAppConfiguration
class ConvertdomainApplicationTests {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;

    //需要转换的源地址
    private final static String originalUri = "https://www.baidu.com/s?wd=%E4%B8%AD%E5%9B%BD%E8%B5%B0%E5%90%91%E6%88%90%E5%8A%9F&rsv_spt=1&rsv_iqid=0xa90f9661000cacb9&issp=1&f=8&rsv_bp=1&rsv_idx=2&ie=utf-8&rqlang=cn&tn=baiduhome_pg&rsv_enter=0&rsv_dl=tb&oq=%25E4%25B8%25AD%25E5%259B%25BD%25E8%25B5%25B0%25E5%2590%2591%25E6%2588%2590%25E5%258A%259F&rsv_btype=t&rsv_t=091fgHcaqU2ZngBry3UHvocCxMU2o1rT%2ByogDP88tOlYzWe5tnuv7%2FfUDGnGiPlhFI9%2B&rsv_pq=c00f74a7000097fc&rsv_sug3=51&rsv_sug1=29&rsv_sug7=100&rsv_sug4=701";

    private static String shortURI;

    @BeforeEach
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @DisplayName("测试将一个原始的URI转换成短链")
    @Test
    void convertToShortTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/toshort")
                        .content(originalUri)
                        .accept(MediaType.ALL))
                //等同于Assert.assertEquals(200,status);
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        //得到返回结果
        String result = mvcResult.getResponse().getContentAsString();
        JSONObject o = JSON.parseObject(result);
        shortURI = o.getString("data");
        System.out.println("得到的短链为:" + shortURI);
        //期待得到的值 断言
        assertEquals(200,mvcResult.getResponse().getStatus());
    }

    @DisplayName("测试将一个短URI转换成原始连接")
    @Test
    void convertToOriginalTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/api/tooriginal")
                        .content(shortURI)
                        .accept(MediaType.ALL))
                //等同于Assert.assertEquals(200,status);
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        //得到返回结果
        String result = mvcResult.getResponse().getContentAsString();
        JSONObject o = JSON.parseObject(result);
        System.out.println("得到的原始链为:" + URLDecoder.decode(o.getString("data"),"UTF-8"));
        assertEquals(200,mvcResult.getResponse().getStatus());
    }
}
