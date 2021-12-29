package com.sequoia.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.sequoia.api.UrlLongtoShortApi;
import com.sequoia.imp.UrlLongShortimp;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {UrlLongShortimp.class})
public class TaskTest {
	
	 @Autowired  
	 private UrlLongtoShortApi LongtoShortApi;
	 
	
	@Test
    public void transLongtoShortUrlTest() {
		System.out.println("长链接转短地址测试开始");
		String  Short  =   LongtoShortApi.transLongtoShortUrl("http://localhost:1055/testurl/urllongtoshrot");
        System.out.println("Short="+Short);
    }

	@Test
    public void transShorttoLongUrlTest() {
		String  LongUrl  =   LongtoShortApi.transShorttoLongUrl("http://s.t/84YZNH");
        System.out.println("LongUrl="+LongUrl);
    }
	



}
