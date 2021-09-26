package com.example.shorturl.controller;

import com.example.shorturl.dao.URLMapping;
import com.example.shorturl.dao.URLMappingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class URLControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private URLMappingRepository repository;

    @Test
    void redirect_illegalArgumet() throws Exception {
        String shortURL = "1234@";
        String errorMsg = "短链接字符码只能由字母、数字、中划线、下划线组成，长度是8个字符。";
        mockMvc.perform(get("/" + shortURL))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(errorMsg)));
    }

    @Test
    void redirect_notFound() throws Exception {
        String shortURL = "1234abcd";
        String errorMsg = "找不到该短链接";
        mockMvc.perform(get("/" + shortURL))
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(errorMsg)));
    }

    @Test
    void createShortURL_illegalArgument() throws Exception {
        String lurl = "abcd";
        String json = getPostJson(lurl);
        String errorMsg = "长链接必须是有效的URL";
        mockMvc.perform(post("/lurls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString(errorMsg)));
    }

    @Test
    void createShortURL_and_redirect_correctCase() throws Exception {
        // First time insert
        String lurl = "https://new.qq.com/omn/20210913/20210913A01JME00.html";
        String json = getPostJson(lurl);
        String expectedBase64Code = "ygmEVhKz";
        String expectedFullShortURL = "http://a.cn/" + expectedBase64Code;
        mockMvc.perform(post("/lurls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(expectedFullShortURL)));

        // redirect short url
        mockMvc.perform(get("/" + expectedBase64Code))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(header().stringValues("Location", lurl));

        // create case for hash collision
        Long lurlHash = 275756487988607L;
        URLMapping um = repository.findByLurlHash(lurlHash);
        String modifiedLurl = "https://www.sina.com/123.html";
        um.setLurl(modifiedLurl);
        URLMapping umNew = repository.save(um);

        //base64URL for lurl+ctrlA
        String lurlWithCtrlABase64URL="ekGaYzqI";
        mockMvc.perform(post("/lurls")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(lurlWithCtrlABase64URL)));
    }

    private String getPostJson(String url) {
        return "{\"lurl\":\"" + url + "\"}";
    }
}