package com.example.shortUrl;

import com.example.shortUrl.dao.UrlMaps;
import com.example.shortUrl.pojo.Result;
import com.example.shortUrl.service.UrlHandlerService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.CountDownLatch;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShortUrlApplication.class )
public class ShortUrlApplicationTest {

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    UrlHandlerService urlHandlerService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void getUrls() throws Exception {
        Result<String> shortResult = urlHandlerService.getShortUrl("www.aifabu.com/loginwx?channel=yimei");
        System.out.println("转换为短链接后的结果为："+shortResult.toString());
        Result<String> longResult = urlHandlerService.getLongUrl(shortResult.getData().get(0));
        System.out.println("短链接对应的长链接结果为："+longResult.toString());
        Result<String> short2Result = urlHandlerService.getShortUrl("www.aifabu.com/loginwx?channel=yimei");
        System.out.println("再次获取短链接结果为："+short2Result.toString());
    }

    @Test
    public void  testDoubleCheck() throws Exception {
        int maxThread = 10000;
        CountDownLatch startState=new CountDownLatch(1);
        CountDownLatch endState = new CountDownLatch(maxThread);
        for(int i=0;i<maxThread;i++){
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        startState.await();
                        Result<String> shortResult = urlHandlerService.getShortUrl("www.aifabu.com/loginwx?channel=yimei");
                        UrlMaps.md5Urls.clear();
                        UrlMaps.shortUrls.clear();
                        System.out.println(Thread.currentThread().getName()+"转换为短链接后的结果为："+shortResult.toString());
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally {
                        endState.countDown();
                    }
                }
            },"thread-"+i);
            t.start();
        }
        long startTime = System.nanoTime();
        System.out.println("模拟大量并发请求同时涌入.....");
        startState.countDown();
        endState.await();
        long endTime = System.nanoTime();
        long time=endTime-startTime;
        System.out.println("大量并发请求运行结束，耗时："+time);

    }


    @Test
    public void queryShortUrls() throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/urlMap/getShortUrl") // 构建get方式来调用接口
                        .param("longUrl", "www.baidu.com/query=1234")// 构建请求参数
        ).andExpect(MockMvcResultMatchers.status().isOk())//添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确
                .andReturn();//最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理。
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.println("获取短链接执行后的mvcResult:------" + mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void queryLongUrls()throws Exception {
        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders.get("/urlMap/getLongUrl") // 构建get方式来调用接口
                        .param("shortUrl", "C4yUfwI")// 构建请求参数
        ).andExpect(MockMvcResultMatchers.status().isOk())//添加ResultMatcher验证规则，验证控制器执行完成后结果是否正确
                .andReturn();//最后返回相应的MvcResult；然后进行自定义验证/进行下一步的异步处理。
        mvcResult.getResponse().setCharacterEncoding("UTF-8");
        System.out.println("获取长链接执行后的mvcResult:------" + mvcResult.getResponse().getContentAsString());
    }



}

