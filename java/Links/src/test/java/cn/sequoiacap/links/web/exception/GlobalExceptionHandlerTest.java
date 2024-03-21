package cn.sequoiacap.links.web.exception;

import cn.sequoiacap.links.entities.Link;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Liushide
 * @date :2022/4/6 15:48
 * @description : web框架全局异常处理测试类
 */
@Slf4j
@SpringBootTest
class GlobalExceptionHandlerTest {

    /**
     * moke对象
     */
    MockMvc mockMvc;

    String urlPath = "/link";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @BeforeEach
    public void beforeEach(){
        log.info("beforeEach 初始化 mockMvc");
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();  // 初始化MockMvc对象
    }

    @Test
    @DisplayName("测试 Web全局异常 处理 json 请求参数的异常 方法")
    void jsonParamsException() {
        //测试数据
        ObjectMapper objectMapper = new ObjectMapper();

        Link link = new Link();
        link.setLongLink("abcdfegagagd.dsss");

        try {
            String paramContent = objectMapper.writeValueAsString(link);
            MockHttpServletResponse response = generateResponse(urlPath, HttpMethod.POST ,paramContent);

            int status = response.getStatus();                 //得到返回代码
            //
            String charEncoding = response.getCharacterEncoding();
            log.debug("charEncoding={}", charEncoding);
            String content = response.getContentAsString();    //得到返回结果
            log.info("status: {},content: {}", status, content);
            assertEquals(200, status);
        } catch (Exception e) {
            log.error("jsonParamsException error ");
        }
    }

    @Test
    @DisplayName("测试 Web全局异常 处理 未知异常捕获 方法")
    void unNoException() {
        //测试数据
        Link Link = new Link();
        Link.setLongLink("https://www.abc.com/aa/bb/cc");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String paramContent = objectMapper.writeValueAsString(Link);
            MockHttpServletResponse  response = generateResponse(urlPath, HttpMethod.DELETE ,paramContent);

            int status = response.getStatus();                 //得到返回代码
            String charEncoding = response.getCharacterEncoding();
            log.debug("charEncoding={}", charEncoding);
            String content = response.getContentAsString();    //得到返回结果
            log.info("status: {},content: {}", status, content);
            assertEquals(200, status);
        } catch (Exception e) {
            log.error("unNoException error ");
        }
    }

    /**
     * 生成Response对象
     * @param url
     * @param paramContent
     * @return
     */
    private MockHttpServletResponse generateResponse (String url, HttpMethod httpMethod,  String paramContent ) {
        MockHttpServletResponse response = null;
        try {
            //
            MockHttpServletRequestBuilder request = null;

            if(HttpMethod.GET.equals(httpMethod)) {
                request = MockMvcRequestBuilders.get(url);
            } else if (HttpMethod.POST.equals(httpMethod)) {
                request = MockMvcRequestBuilders.post(url);
            } else if (HttpMethod.DELETE.equals(httpMethod)) {
                request = MockMvcRequestBuilders.delete(url);
            }
            //设置
            request.accept(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8)//字符集
                    .contentType(MediaType.APPLICATION_JSON);  //请求类型 JSON
            //参数
            if(paramContent != null) {
                request .content(paramContent);
            }

            MvcResult mvcResult = mockMvc.perform(request)
                    .andDo(MockMvcResultHandlers.print())                 //添加ResultHandler结果处理器，比如调试时 打印结果(print方法)到控制台
                    .andReturn();                                         //返回验证成功后的MvcResult；用于自定义验证/下一步的异步处理；

            response = mvcResult.getResponse();
            response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));//字符集
        } catch (Exception e) {
            log.error("generateResponse error", e);
        }

        return response;
    }

}