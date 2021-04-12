package com.scdt.shorturl;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.CoreMatchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={ShortUrlApplication.class},webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ShortUrlControllerTest{
	@Autowired private TestRestTemplate restTemplate;	
    @LocalServerPort private int port;//使用@LocalServerPort将端口注入进来
    
    private String originalUrl="abcdefghijklmn";//测试用例，长地址
    private String shortUrl;
    
    /**
     * 工具方法，执行请求的模板
     * @param urlSuffix
     * @return ResponseEntity<String>
     */
    private ResponseEntity<String>getResponseEntity(String urlSuffix){
    	ResponseEntity<String>responseEntity=restTemplate.exchange("http://localhost:"+port+"/shorturl/"+urlSuffix,HttpMethod.GET,null,String.class);
    	assertThat(responseEntity.getStatusCode(),equalTo(HttpStatus.OK));
    	return responseEntity;
    }
    
    /**
     * 测试长地址转短地址
     * @author 周小建
     * @throws Exception
     */
    @Test public void testLong2short()throws Exception{
    	ResponseEntity<String>responseEntity=getResponseEntity("long2short?originalUrl="+originalUrl+"&workerId=0&serviceId=0");
    	shortUrl=responseEntity.getBody();
    }
    
    /**
     * 测试直接访问无效的长地址
     * @author 周小建
     * @throws Exception
     */
    @Test public void testExampleInvalid()throws Exception{
    	ResponseEntity<String>responseEntity=getResponseEntity("example/aaaaaaaaaaaaaa");
    	assertThat(responseEntity.getBody(),CoreMatchers.containsString("\"msg\":\"无效长地址\""));
    }
    
    /**
     * 测试直接访问有效的长地址_不带短地址
     * @author 周小建
     * @throws Exception
     */
    @Test public void testExampleValid_emptyShortUrl()throws Exception{
    	testLong2short();
    	ResponseEntity<String>responseEntity=getResponseEntity("example/"+originalUrl);
    	assertThat(responseEntity.getBody(),CoreMatchers.containsString("\"flag\":\"query\""));
    }
    /**
     * 测试直接访问有效的长地址_带有无效短地址
     * @author 周小建
     * @throws Exception
     */
    @Test public void testExampleValid_invlidShortUrl()throws Exception{
    	testLong2short();
    	ResponseEntity<String>responseEntity=getResponseEntity("example/"+originalUrl+"?shortUrl=aaaa");
    	assertThat(responseEntity.getBody(),CoreMatchers.containsString("\"flag\":\"query\""));
    }
    
    /**
     * 测试访问无效的短地址，中途停止不进行跳转。
     * @author 周小建
     * @throws Exception
     */
    @Test public void testShort2longInvalid()throws Exception{
    	ResponseEntity<String>responseEntity=getResponseEntity("t/aaaaaaaa");
    	assertThat(responseEntity.getBody(),CoreMatchers.containsString("\"msg\":\"无效短地址\""));
    }
    /**
     * 测试访问有效的短地址，转为长地址并自动跳转
     * @author 周小建
     * @throws Exception
     */
    @Test public void testShort2longValid()throws Exception{
    	testLong2short();
    	ResponseEntity<String>responseEntity=getResponseEntity("t/"+shortUrl);
    	assertThat(responseEntity.getBody(),CoreMatchers.containsString("\"msg\":\"短地址转长地址\""));
    }
}