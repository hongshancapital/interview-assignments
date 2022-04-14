package com.sequoiacap.util;

import org.apache.http.HttpResponse;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.sequoiacap.annotation.Generated;
import com.sequoiacap.model.UrlInfo;

/**
 * 
 * @author zoubin
 *
 */
public class Util {

	private Util() {}
	
	@Generated
	public static UrlInfo handleResp(HttpResponse resp) {
		try {
			if(resp == null) {
				return new UrlInfo(500, null, "resp can not be null");
			}
			if (resp.getStatusLine().getStatusCode() == 200) {
				return JSON.parseObject(EntityUtils.toString(resp.getEntity() , "UTF-8"), UrlInfo.class);
		    }
			return new UrlInfo(resp.getStatusLine().getStatusCode(), null, "");
		} catch (Exception e) {
			return new UrlInfo(resp.getStatusLine().getStatusCode(), null, String.format("Failed to forward,err:%s", e.getMessage()));
		}
		finally {
			if(resp != null) {
				HttpClientUtils.closeQuietly(resp);
			}
		}
	}
}
