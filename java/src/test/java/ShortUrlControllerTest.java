import com.example.assignment.Application;
import com.example.assignment.common.dto.ResponseEnum;
import com.example.assignment.common.dto.Result;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ShortUrlControllerTest {

    private Gson GSON = new Gson();

    @Resource
    private MockMvc mvc;

    @Test
    public void testGenerateAndParseWithNormalUrl() throws Exception {
        String url = "http://localhost:8080/api/short-url/generate?originalUrl=";
        String parseUrl = "http://localhost:8080/api/short-url/parse?shortUrl=";
        String longUrl = "http://www.baidu.com";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200, status);
        Result<String> result = GSON.fromJson(response.getContentAsString(), new TypeToken<Result<String>>() {
        }.getType());
        assertEquals(ResponseEnum.SUCCESS.getCode(), result.getCode());
        String shortCode = result.getData();

        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get(parseUrl + shortCode)).
                andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();
        int status2 = response2.getStatus();
        assertEquals(200, status2);
        Result<String> result2 = GSON.fromJson(response2.getContentAsString(), new TypeToken<Result<String>>() {
        }.getType());
        assertEquals(ResponseEnum.SUCCESS.getCode(), result2.getCode());
        assertEquals(longUrl, result2.getData());
    }

    @Test
    public void testGenerateWithSameUrl() throws Exception {
        String url = "http://localhost:8080/api/short-url/generate?originalUrl=";
        String longUrl = "http://www.baidu.com";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200, status);
        Result<String> result = GSON.fromJson(response.getContentAsString(), new TypeToken<Result<String>>() {
        }.getType());
        assertEquals(ResponseEnum.SUCCESS.getCode(), result.getCode());

        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();
        int status2 = response2.getStatus();
        assertEquals(200, status2);
        Result<String> result2 = GSON.fromJson(response2.getContentAsString(), new TypeToken<Result<String>>() {
        }.getType());
        assertEquals(ResponseEnum.SUCCESS.getCode(), result2.getCode());
        assertEquals(result.getData(), result2.getData());
    }

    @Test
    public void testGenerateWithOverLengthUrl() throws Exception {
        String url = "http://localhost:8080/api/short-url/generate?originalUrl=";
        String longUrl = "http://www.baidu.com/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test/test";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200, status);
        Result<String> result = GSON.fromJson(response.getContentAsString(), new TypeToken<Result<String>>() {
        }.getType());
        assertEquals(ResponseEnum.ILLEGAL_URL.getCode(), result.getCode());
    }

    @Test
    public void testGenerateWithIllegalUrl() throws Exception {
        String url = "http://localhost:8080/api/short-url/generate?originalUrl=";
        String longUrl = "test";
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200, status);
        Result<String> result = GSON.fromJson(response.getContentAsString(), new TypeToken<Result<String>>() {
        }.getType());
        assertEquals(ResponseEnum.ILLEGAL_URL.getCode(), result.getCode());
    }

    @Test
    public void testGenerateWithExcessUrl() throws Exception {
        String url = "http://localhost:8080/api/short-url/generate?originalUrl=";
        String longUrl = "http://www.baidu.com/";
        int i = 0;
        while (i <= 1000) {
            mvc.perform(MockMvcRequestBuilders.post(url + longUrl + i)).
                    andReturn();
            i++;
        }
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(url + longUrl + i)).
                andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        int status = response.getStatus();
        assertEquals(200, status);
        Result<String> result = GSON.fromJson(response.getContentAsString(), new TypeToken<Result<String>>() {
        }.getType());
        assertEquals(ResponseEnum.SHORT_CODE_USE_OUT.getCode(), result.getCode());
    }

    @Test
    public void testParseWithIllegalCode() throws Exception {
        String parseUrl = "http://localhost:8080/api/short-url/parse?shortUrl=";
        String shortCode = "aaa**aaa";

        MvcResult mvcResult2 = mvc.perform(MockMvcRequestBuilders.get(parseUrl + shortCode)).
                andReturn();
        MockHttpServletResponse response2 = mvcResult2.getResponse();
        int status2 = response2.getStatus();
        assertEquals(200, status2);
        Result<String> result2 = GSON.fromJson(response2.getContentAsString(), new TypeToken<Result<String>>() {
        }.getType());
        assertEquals(ResponseEnum.ILLEGAL_SHORT_CODE.getCode(), result2.getCode());
    }
}
