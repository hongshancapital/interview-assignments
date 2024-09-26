package shorturl.server;

import com.alibaba.fastjson.JSON;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import shorturl.server.server.application.dto.UrlRequest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment =SpringBootTest.WebEnvironment.MOCK,classes = ShortUrlServerApplication.class)
@AutoConfigureMockMvc

public class ShortUrlControllerTest {

    @Autowired
    private MockMvc mockMvc;

    public ShortUrlControllerTest() {
    }

    private String shortUrl = null;

    @Test
    @DisplayName("测试长链接接口")
    public void test() throws Exception {
        UrlRequest url = new UrlRequest();
        url.setLongUrl("https://blog.csdn.net/linsongbin1/article/details/83574619");
        System.out.println(url.getLongUrl().length());


        MvcResult mvcResult = mockMvc.perform(
                post("/shortUrlServer/getShortUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(url)))
                .andReturn();

        //shortUrl = mvcResult.getResponse().getContentType().
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    @DisplayName("测试长链接接口")
    public void testShort() throws Exception {
        UrlRequest url = new UrlRequest();
        url.setShortUrl("http://t.cn/eRKFcl9M");
        System.out.println(url.getShortUrl().length());


        MvcResult mvcResult = mockMvc.perform(
                get("/shortUrlServer/getLongUrl")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JSON.toJSONString(url)))
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

}
