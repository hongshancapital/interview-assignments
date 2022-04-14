package com.sequoiacap.util;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;

import com.sequoiacap.annotation.Generated;

/**
 * 
 * @author zoubin
 */
public class HttpClientUtil {

	private static CloseableHttpAsyncClient client = null;
    private HttpClientUtil() {
    	
    }
    
    @Generated
	private static CloseableHttpAsyncClient getHttpClient() throws IOReactorException {
		if (client == null) {
			synchronized (HttpClientUtil.class) {
				if (client == null) {
					RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(10000)// 连接超时,连接建立时间,三次握手完成时间
							.setSocketTimeout(20000)// 请求超时,数据传输过程中数据包之间间隔的最大时间
							.setConnectionRequestTimeout(20000)// 使用连接池来管理连接,从连接池获取连接的超时时间
							.build();

					// 配置io线程
					IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
							.setIoThreadCount(Runtime.getRuntime().availableProcessors()).setSoKeepAlive(true).build();
					// 设置连接池大小
					ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
					PoolingNHttpClientConnectionManager connManager = new PoolingNHttpClientConnectionManager(
							ioReactor);
					connManager.setMaxTotal(500);// 最大连接数设置1
					connManager.setDefaultMaxPerRoute(500);// per route最大连接数设置

					client = HttpAsyncClients.custom().setConnectionManager(connManager)
							.setDefaultRequestConfig(requestConfig).build();
					client.start();

				}
			}
		}
		return client;
	}

	public static HttpResponse getUrl(String url) throws Exception {
		HttpGet get = new HttpGet();
		get.setURI(new URI(url));
		return getHttpClient().execute(get, null).get(10, TimeUnit.SECONDS);
	}
	
	public static HttpResponse postUrl(String url, String longUrl) throws Exception {
		final HttpPost post = new HttpPost(url);
		StringEntity stringEntity = new StringEntity(longUrl, "UTF-8");
		post.setEntity(stringEntity);
		return getHttpClient().execute(post, null).get(10, TimeUnit.SECONDS);
	}
}
