package homework.shorturl.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import homework.shorturl.model.dto.UrlTransDTO;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class UrlTransControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void transShortTest() throws Exception {
        //转短链-无参数
        UrlTransDTO dto = new UrlTransDTO();
        ObjectMapper mapper = new ObjectMapper();
        MvcResult mvcResult1 = mockMvc.perform(MockMvcRequestBuilders.post("/urlTrans/transShort")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        //转短链-设置url参数
        dto.setUrl("xxx.xxx.xxx/xxx/xxxx");
        MvcResult mvcResult2 = mockMvc.perform(MockMvcRequestBuilders.post("/urlTrans/transShort")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        //查长链-无参数
        MvcResult mvcResult3 = mockMvc.perform(MockMvcRequestBuilders.post("/urlTrans/transLong")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        //查长链-设置有效shortUrl参数
        dto.setShortUrl((mapper.convertValue(mvcResult2.getResponse().getContentAsString(), JSONObject.class).getJSONObject("data").getString("shortUrl")));
        MvcResult mvcResult4 = mockMvc.perform(MockMvcRequestBuilders.post("/urlTrans/transLong")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        //查长链-设置无效shortUrl参数
        dto.setShortUrl("xxxxx");
        MvcResult mvcResult5 = mockMvc.perform(MockMvcRequestBuilders.post("/urlTrans/transLong")
                        .content(mapper.writeValueAsString(dto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
    }
}
