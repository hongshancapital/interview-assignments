package hongshan.demo;

import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hongshan.demo.beans.Result;
import hongshan.demo.config.WebMvcConfiguration;
import hongshan.demo.controller.DomainController;
import hongshan.demo.storage.DomainCache;
import hongshan.demo.storage.fvn.FNV1a32;
import hongshan.demo.utils.TextUtils;
import springfox.documentation.spring.web.plugins.Docket;

@SpringBootTest
class DemoTests {

	private static final String URL = "http://www.baidu.com";

	@Test
	void checkLongToShortMethod() {
		DomainController obj = new DomainController();

		try {
			String shortUrl = DomainCache.longToShort(URL);

			Result result = obj.longToShort(URL);
			Assertions.assertEquals(shortUrl, result.getDomain(), "获取的短域名不一致");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Test
	void checkshortToLongMethod() {
		DomainController obj = new DomainController();

		try {
			String shortUrl = DomainCache.longToShort(URL);

			Result result = obj.shortToLong(shortUrl);
			Assertions.assertEquals(URL, result.getDomain(), "获取的长域名不一致");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Test
	void checkAllMethod() {
		DomainController obj = new DomainController();

		Result result1 = obj.longToShort(URL);

		Result result2 = obj.shortToLong(result1.getDomain());

		Assertions.assertEquals(result2.getDomain(), URL, "失败！长域名：" + URL + "；短域名：" + result1.getDomain());

	}

	@Test
	void checkEmpty() {
		String z="test it";
		Assertions.assertFalse(TextUtils.empty(z));
		z=null;
		Assertions.assertTrue(TextUtils.empty(z));
		
	}
	
	@Test
	void checkResultBean() {
		Result result= Result.error();
		Assertions.assertTrue(result.getErrorCode()==Result.FAILURE);
		result= Result.error("Error");
		Assertions.assertTrue(result.getErrorCode()==Result.FAILURE && result.getDescript().equals("Error"));
		result= Result.success();
		Assertions.assertTrue(result.getErrorCode()==Result.SUCCESS);
		result= Result.success(URL);
		Assertions.assertTrue(result.getErrorCode()==Result.SUCCESS && result.getDomain().equals(URL));
		
		result = new Result();
		result.setErrorCode(Result.SUCCESS);
		Assertions.assertTrue(result.getErrorCode()==Result.SUCCESS);
		result.setDescript("OK");
		Assertions.assertTrue(result.getErrorCode()==Result.SUCCESS);
		result.setDomain(URL);
		Assertions.assertTrue(result.getDomain().equals(URL));
	}
	
	@Test
	void checkFNV() {
		FNV1a32 fnv1= new FNV1a32();
		fnv1.init(URL);
		FNV1a32 fnv2= new FNV1a32();
		byte[] bytes= URL.getBytes();
		fnv2.init(bytes,0,bytes.length);
		Assertions.assertTrue(fnv1.getHash()==fnv2.getHash());
		
		fnv1= new FNV1a32();
		fnv1.update(URL);
		fnv2= new FNV1a32();
		fnv2.update(bytes,0, bytes.length);
		Assertions.assertTrue(fnv1.getHash()==fnv2.getHash());
		
	}
	
	@Test
	void checkConfig() {
		ApplicationContext app=new AnnotationConfigApplicationContext(WebMvcConfiguration.class);
		
		Object d= app.getBean(Docket.class);

		Assertions.assertTrue(d instanceof Docket);
		
	}
}
