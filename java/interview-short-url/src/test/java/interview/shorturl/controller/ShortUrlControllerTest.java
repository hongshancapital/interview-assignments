package interview.shorturl.controller;

import com.alibaba.fastjson.JSON;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * controller测试
 *
 * @author: ZOUFANQI
 **/
@SpringBootTest
public class ShortUrlControllerTest {
    @Autowired
    private WebApplicationContext context;

    @Test
    void testApi() throws Exception {
        final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        // 正常数据
        final ResultActions result =
                mockMvc.perform(MockMvcRequestBuilders.post("/convertUrl?url=http").accept(MediaType.APPLICATION_JSON))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("shortUrl")));
        // 正常获取数据
        result.andDo(r -> {
            final String shortUrl = JSON.parseObject(r.getResponse().getContentAsString()).getJSONObject("data").getJSONObject("info").getString("shortUrl");
            mockMvc.perform(MockMvcRequestBuilders.get("/url/" + shortUrl).accept(MediaType.APPLICATION_JSON))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("realUrl")));
        });

        // 无效参数获取
        mockMvc.perform(MockMvcRequestBuilders.post("/convertUrl").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("400")));
        mockMvc.perform(MockMvcRequestBuilders.post("/convertUrl?url=abc").accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("400")));

        // 内存溢出测试
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                try {
                    mockMvc.perform(MockMvcRequestBuilders.post("/convertUrl?url=http").accept(MediaType.APPLICATION_JSON))
                            .andExpect(MockMvcResultMatchers.status().isOk());
                } catch (Exception e) {
                }
            }).start();
        }
    }
}
