package com.hsjt.example.netaddress;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.hsjt.example.netaddress.common.bean.ResultData;
import com.hsjt.example.netaddress.common.bean.UserKeyIdVO;
import com.hsjt.example.netaddress.common.utils.SysUtils;
import com.hsjt.example.netaddress.common.utils.TimeUtils;
import com.hsjt.example.netaddress.test.controller.AddressUrlController;

import springfox.documentation.oas.annotations.EnableOpenApi;


@SpringBootApplication(scanBasePackages={"com.hsjt.example.netaddress"})
@EnableOpenApi
@EnableScheduling
@SpringBootTest
//@SpringBootTest
//@RunWith(SpringRunner.class)
@DisplayName("测试类")
class HsjtNetaddressApplicationTests {
	
	@Autowired
	private AddressUrlController addressUrlService; 
    
	@Test
	@DisplayName("获取短链接")
	public void contextShort() throws InterruptedException {
		 String url ="https://new.qq.com/omn/20210726/20210726A0BJLO00.html";
		 addressUrlService.getShortUrl(url, null);
		 addressUrlService.getShortUrl(url, null);
		 addressUrlService.getShortUrl(url, SysUtils.userId);
		 addressUrlService.getShortUrl("asd", "ewre");
		 int f = 300000;
		 for(int i=0;i<f;i++) {
            	String t = i+""; 
            	//TimeUtils.dateTimeHmStrFormatter.format(LocalDateTime.now());
            	System.out.println(t+"---shortUrl---"+url+"A"+i);
            	ResultData shortUrl = addressUrlService.getShortUrl(url+"A"+i, null);
            	System.out.println("---shortUrl2---"+shortUrl.getData());
            	//Thread.sleep(1000*10);
				String t2 = TimeUtils.dateTimeHmStrFormatter.format(LocalDateTime.now());
				System.out.println(t+"---shortUrl---"+url+"B"+i);
				ResultData shortUrl2 = addressUrlService.getShortUrl(url+"B"+i, null);
				System.out.println("---shortUrl---"+shortUrl2.getData());
		 }
		  
		  
	}
	
	@Test
	@DisplayName("测试获取长链接")
	void contextLong() {
		ResultData longU = addressUrlService.getLongurl("asxsas", null);
		ResultData longUrl = addressUrlService.getLongurl("https://github.com/sas/jkasjdnianadsas", null);
		addressUrlService.getLongurl("https://github.com/sas/jkasjdnianadsas", SysUtils.userId);
		System.out.println("---longUrl---"+longUrl.getData());
		ResultData longUrl2 = addressUrlService.getLongurl("https://github.com/sas/jkasjdndaianadsas", null);
		System.out.println("---longUrl---"+longUrl2.getData());
		ResultData shortUrl = addressUrlService.getShortUrl("https://github.com/sas/jkasjdnianadsas", null);
		longUrl = addressUrlService.getLongurl(shortUrl.getData().toString(), null);
		 try {
			 addressUrlService.getLongurl(shortUrl.getData().toString(), "afsd");
		 } catch (Exception e) {
			 
		 }
		 
	}
	
	@Test
	@DisplayName("测试注册用户")
	void contextAddUser() {
		UserKeyIdVO user = addressUrlService.creatUser(null);
		UserKeyIdVO vo = new UserKeyIdVO();
		UserKeyIdVO user2 = addressUrlService.creatUser(vo);
		vo.setId("li");
		vo.setKeyId("dwerwef");
		ResultData longU = addressUrlService.getLongurl("li", "li");
		try {
			addressUrlService.getShortUrl("https://github.com/sas/jkasjdnis", vo.getId());
		} catch (Exception e) {
		}
	}
	 
}
