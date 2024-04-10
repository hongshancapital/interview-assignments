package city.yyds.job.hsdemo.filter;

import city.yyds.job.hsdemo.service.ShortUrlService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

import javax.servlet.ServletException;
import java.io.IOException;

@SpringBootTest
class ShortUrlFilterTest {
    private static final Logger log = LoggerFactory.getLogger(ShortUrlFilterTest.class);

    @Autowired
    private ShortUrlFilter filter;

    private MockHttpServletRequest request;
    private MockHttpServletResponse response;
    private MockFilterChain chain;
    private MockHttpSession session;
    @Autowired
    ShortUrlService shortUrlService;
    @Value("${short.url.prefix}")
    private String shortUrlPrefix;

    /*
    * 模拟请求：http://wds.fit
    * 本服务器域名（wds.fit）后为空的情况
    * */
    @Test
    public void urlIsBull() {
        try{
            filter.doFilter(request, response, chain);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ServletException e){
            e.printStackTrace();
        }
    }
    /*
     * 模拟请求：http://wds.fit/shortUrl/getShortUrl
     * 本服务器域名（wds.fit）后为获取短码的地址，但是请求参数为空的情况
     * */
    @Test
    public void urlIsToShort() {
        request.setRequestURI("/shortUrl/getShortUrl");
        try{
            filter.doFilter(request, response, chain);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ServletException e){
            e.printStackTrace();
        }
    }

    /*
     * 模拟请求：http://wds.fit/S/1
     * 本服务器域名（wds.fit）后为获取长码的参数，此时应该能跳转到长码
     */
    @Test
    public void redirectToLongDns() {
        String url ="https://m.gmw.cn/2021-11/07/content_35292677.htm";
        String result = shortUrlService.getShortUrl(url);
        String shortUrl = result.substring(shortUrlPrefix.length());
        request.setRequestURI(shortUrl);
        try{
            filter.doFilter(request, response, chain);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ServletException e){
            e.printStackTrace();
        }
    }

    /*
     * 模拟请求：http://wds.fit/S/123456789
     * 本服务器域名（wds.fit）后为获取长码的参数，但是短码超过8位，是无效的请求
     */
    @Test
    public void urlIsNotExist() {
        request.setRequestURI("/S/123456789");
        try{
            filter.doFilter(request, response, chain);
        }catch (IOException e){
            e.printStackTrace();
        }catch (ServletException e){
            e.printStackTrace();
        }
    }
    @BeforeEach
    void setUp() {
        log.info("test setUp");
        request = new MockHttpServletRequest();
        request.setCharacterEncoding("UTF-8");
        response = new MockHttpServletResponse();
        session=new MockHttpSession();
        request.setSession(session);
        chain = new MockFilterChain();
    }

    @AfterEach
    void tearDown() {
    }

}