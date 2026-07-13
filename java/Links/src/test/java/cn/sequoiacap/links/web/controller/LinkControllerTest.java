package cn.sequoiacap.links.web.controller;

import cn.sequoiacap.links.entities.Link;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
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

import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author : Liu Shide
 * @date : 2022/4/6 14:28
 * @description : URL(链接) 控制测试类，生成短地址，查询长地址
 */
@Slf4j
@SpringBootTest
class LinkControllerTest {

    static String longLink = "https://www.abc.com/app/tb-source-app/aiguangjiepc/content/index.html?spm=a21bo.jianhua.201870.4.5af911d9CROzgQ&contentId=5600000340593663082&scm=1007.12846.262044.0&pvid=b611e18f-59ab-4745-9f99-860ded0f7602";
    static String shortCode = "EvaUZj";

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

    @AfterEach
    public void afterEach(){
        log.info("afterEach");
    }

    @Test
    @DisplayName("测试 Controller 层的 createShortLink 方法")
    void createShortLink() {
        //测试数据
        Link Link = new Link();
        Link.setLongLink(longLink);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String paramContent = objectMapper.writeValueAsString(Link);
            MockHttpServletResponse response = generateResponse(urlPath, HttpMethod.POST ,paramContent);

            // 得到返回代码
            int status = response.getStatus();
            String charEncoding = response.getCharacterEncoding();
            log.debug("charEncoding={}", charEncoding);
            // 得到返回结果
            String content = response.getContentAsString();
            log.info("status: {},content: {}", status, content);
            assertEquals(200, status);
        } catch (Exception e) {
            log.error("createShortLink error ", e);
        }
    }

    @Test
    @DisplayName("测试 Controller 层的 createShortLink 的新方法")
    void createShortLinkNew() {
        //测试数据
        Link Link = new Link();
        Link.setLongLink("https://www.abc.com/app/tb-source-app/aiguangjiepc/content/index.html?spm=a21bo&user_name=lucas");
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            String paramContent = objectMapper.writeValueAsString(Link);
            MockHttpServletResponse  response = generateResponse(urlPath, HttpMethod.POST ,paramContent);

            int status = response.getStatus();                 //得到返回代码
            String charEncoding = response.getCharacterEncoding();
            log.debug("charEncoding={}", charEncoding);
            String content = response.getContentAsString();    //得到返回结果
            log.info("status: {},content: {}", status, content);
            assertEquals(200, status);
        } catch (Exception e) {
            log.error("createShortLinkNew error ", e);
        }
    }

    @Test
    @DisplayName("测试 Controller 层的 createShortLink 模拟多个 相同的Link并发同时添加")
    void createShortLinkMultipleIdentical() {
        //测试数据
        Link Link = new Link();
        Link.setLongLink("https://www.abc.com/app/tb-source-app/aiguangjiepc/content/index.html?spm=a21bo&user_name=lucas");
        ObjectMapper objectMapper = new ObjectMapper();

        final int N = 20; // 线程数
        ExecutorService executorService = Executors.newFixedThreadPool(N);  //线程池
        CountDownLatch countDownLatch=new CountDownLatch(N);  //初始化计数器

        for(int i=0;i<N;i++) {
            final int tempi = i; // 记录循环的标志，在线程中打印需要设置为 final
            executorService.submit(() -> {
                try {
                    Thread.sleep((long) (Math.random()*1000)); // 设置随机时间
                    log.info("第 {} 个线程执行...", tempi);
                    // 执行的内容
                    String paramContent = objectMapper.writeValueAsString(Link);
                    MockHttpServletResponse  response = generateResponse(urlPath, HttpMethod.POST ,paramContent);

                    int status = response.getStatus();                 //得到返回代码
                    String charEncoding = response.getCharacterEncoding();
                    log.debug("charEncoding={}", charEncoding);
                    String content = response.getContentAsString();    //得到返回结果
                    log.info("status: {},content: {}", status, content);
                    assertEquals(200, status);
                } catch (Exception e) {
                    log.error("线程执行错误！", e);
                } finally{
                    //运动员到达终点,count数减一
                    countDownLatch.countDown();
                }
            }) ;
        }
        try{
            //等待count数变为0,否则会一直处于等待状态
            countDownLatch.await();
        } catch (Exception e) {
            log.error("countDownLatch.await error", e);
        }
        ///执行完毕，关掉线程池
        executorService.shutdown();
    }

    @Test
    @DisplayName("测试 Controller 层的 createShortLink 模拟多个不同的Link并发同时添加")
    void createShortLinkMultipleDifferent() {
        //测试数据
        Link Link = new Link();
        Link.setLongLink("https://www.abc.com/app/tb-source-app/aiguangjiepc/content/index.html?spm=a21bo&user_name=lucas");
        ObjectMapper objectMapper = new ObjectMapper();

        List<Link> linkList = new ArrayList<>();


        final int N = 20; // 线程数
        ExecutorService executorService = Executors.newFixedThreadPool(N);  //线程池
        CountDownLatch countDownLatch=new CountDownLatch(N);  //初始化计数器

        for(int i=0;i<N;i++) {
            final int tempi = i; // 记录循环的标志，在线程中打印需要设置为 final
            executorService.submit(() -> {
                try {
                    //Thread.sleep((long) (Math.random()*1000)); // 设置随机时间
                    log.info("第 {} 个线程执行...", tempi);
                    // 执行的内容
                    String paramContent = objectMapper.writeValueAsString(Link);
                    MockHttpServletResponse  response = generateResponse(urlPath, HttpMethod.POST ,paramContent);

                    int status = response.getStatus();                 //得到返回代码
                    String charEncoding = response.getCharacterEncoding();
                    log.debug("charEncoding={}", charEncoding);
                    String content = response.getContentAsString();    //得到返回结果
                    log.info("status: {},content: {}", status, content);
                    assertEquals(200, status);
                } catch (Exception e) {
                    log.error("线程执行错误！", e);
                } finally{
                    //运动员到达终点,count数减一
                    countDownLatch.countDown();
                }
            }) ;
        }
        try{
            //等待count数变为0,否则会一直处于等待状态,游戏就没法结束了
            countDownLatch.await();
        } catch (Exception e) {
            log.error("countDownLatch.await error", e);
        }
        ///执行完毕，关掉线程池
        executorService.shutdown();
    }

    @Test
    @DisplayName("测试 Controller 层的 getLinkByCode 方法")
    void  getLinkByCode() {
        try {
            MockHttpServletResponse  response = generateResponse(urlPath + "/" + shortCode, HttpMethod.GET ,null);
            int status = response.getStatus();                 //得到返回代码
            String charEncoding = response.getCharacterEncoding();
            log.debug("charEncoding={}", charEncoding);
            String content = response.getContentAsString();    //得到返回结果
            log.info("status: {},content: {}", status, content);
            assertEquals(200, status);
        } catch (Exception e) {
            log.error("getLinkByCode error ", e);
        }
    }

    @Test
    @DisplayName("测试 Controller 层的 getLinkByCode code太长的 方法")
    void getLinkByCodeLongNotFound() {
        try {
            MockHttpServletResponse  response = generateResponse(urlPath + "/aSb32TsY", HttpMethod.GET ,null);
            int status = response.getStatus();                 //得到返回代码
            String charEncoding = response.getCharacterEncoding();
            log.debug("charEncoding={}", charEncoding);
            String content = response.getContentAsString();    //得到返回结果
            log.info("status: {},content: {}", status, content);
            assertEquals(200, status);
        } catch (Exception e) {
            log.error("getLinkByCode error ", e);
        }
    }

    @Test
    @DisplayName("测试 Controller 层的 getLinkByCode code 不存在测试 方法")
    void getLinkByCodeNotFound() {
        try {
            MockHttpServletResponse  response = generateResponse(urlPath + "/h63YEm", HttpMethod.GET ,null);
            int status = response.getStatus();                 //得到返回代码
            String charEncoding = response.getCharacterEncoding();
            log.debug("charEncoding={}", charEncoding);
            String content = response.getContentAsString();    //得到返回结果
            log.info("status: {},content: {}", status, content);
            assertEquals(200, status);
        } catch (Exception e) {
            log.error("getLinkByCode error ", e);
        }
    }

    @Autowired
    LinkController linkController;

    @Test
    @DisplayName("测试 Controller 层的 preventingCachePenetrationAdd 的防止缓存渗透的添加 方法")
    void preventingCachePenetrationAdd() {
        try {
            //测试带有参数的echoRequest(String request)方法
            Method testNoParamMethod = linkController.getClass().getDeclaredMethod("preventingCachePenetrationAdd",String.class, String.class);
            testNoParamMethod.setAccessible(true);

            // 测试内容
            String newShortCode = "NeA2Gy";
            String newLongLink = "https://www.abc.com/aaa/bbb/ccc?a=123&b=456&c=789";

            //调用
            Object result = testNoParamMethod.invoke(linkController, newShortCode, newLongLink);
            //
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("result = {}", objectMapper.writeValueAsString(result));
            //
            assertEquals(Link.class, result.getClass());
        } catch (Exception e) {
            log.error("preventingCachePenetrationAdd error ", e);
        }
    }

    @Test
    @DisplayName("测试 Controller 层的 codeRepeatedJudgment 的code重复的判断 方法")
    void codeRepeatedJudgment() {
        try {
            //测试带有参数的echoRequest(String request)方法
            Method testNoParamMethod = linkController.getClass().getDeclaredMethod("codeRepeatedJudgment",String.class, Link.class);
            testNoParamMethod.setAccessible(true);

            //
            Link link = new Link();
            link.setLongLink("https://www.abc.com/aaa/bbb/ccc?a=123&b=456&c=789");
            link.setShortCode("seA22y");
            //调用
            Object result = testNoParamMethod.invoke(linkController, longLink, link);
            //
            ObjectMapper objectMapper = new ObjectMapper();
            log.info("result = {}", objectMapper.writeValueAsString(result));
            //
            assertEquals(Link.class, result.getClass());
        } catch (Exception e) {
            log.error("codeRepeatedJudgment error ", e);
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
            }
            //设置
            request.accept(MediaType.APPLICATION_JSON)
                    .characterEncoding(StandardCharsets.UTF_8)//字符集
                    .contentType(MediaType.APPLICATION_JSON);  //请求类型 JSON
            //参数
            if(paramContent != null) {
                request.content(paramContent);
            }

            MvcResult mvcResult = mockMvc.perform(request)
                    //.andExpect(MockMvcResultMatchers.status().isOk())     //期望的结果状态 200
                    .andDo(MockMvcResultHandlers.print())                 //添加ResultHandler结果处理器，比如调试时 打印结果(print方法)到控制台
                    .andReturn();                                         //返回验证成功后的MvcResult；用于自定义验证/下一步的异步处理；
            response = mvcResult.getResponse();
            response.setCharacterEncoding(String.valueOf(StandardCharsets.UTF_8));//字符集
        } catch (Exception e) {
            log.error("generateResponse error ", e);
        }
        return response;
    }
}