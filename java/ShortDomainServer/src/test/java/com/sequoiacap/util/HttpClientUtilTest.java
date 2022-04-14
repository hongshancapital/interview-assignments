package com.sequoiacap.util;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.Test;

public class HttpClientUtilTest {

	@Test
	public void testGetHttpClient() {
	}
	
	@Test
	public void testGetUrl() {
		try {
			HttpResponse resp = HttpClientUtil.getUrl("http://www.baidu.com");
			if (resp.getStatusLine().getStatusCode() == 200) {
				System.out.println(EntityUtils.toString(resp.getEntity()));
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testPostUrl() {
		try {
			HttpResponse resp = HttpClientUtil.postUrl("http://www.baidu.com", "xxx");
			if (resp.getStatusLine().getStatusCode() == 200) {
				System.out.println(EntityUtils.toString(resp.getEntity()));
		    }else {
		    	System.out.println(resp.getStatusLine().getStatusCode());
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
