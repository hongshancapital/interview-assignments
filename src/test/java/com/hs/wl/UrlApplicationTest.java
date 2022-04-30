package com.hs.wl;

import com.hs.wl.controller.UrlController;
import com.hs.wl.manager.UrlTransferManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlApplicationTest {
	@Autowired
	private UrlController urlController;

	@Test
	public void testParseLongUrl(){
		String str = urlController.parseLongUrlToShortUrl("http://www.baidu.com");
		System.out.println(str);

		String url = urlController.getLongUrlByShortUrl(str);
		System.out.println(url);

		url = urlController.getLongUrlByShortUrl("xxx");
		System.out.println(url);

		List<String> keys = new ArrayList<>();
		for (int i = 0; i < 1005; i++) {
			String str1 = urlController.parseLongUrlToShortUrl("http://www.baidu.com"+i);
			System.out.println(str1);
			keys.add(str1);
		}

		System.out.println(keys.size());
		for (String key : keys) {
			String curUrl = urlController.getLongUrlByShortUrl(key);
			System.out.println(curUrl);
		}

	}

}
