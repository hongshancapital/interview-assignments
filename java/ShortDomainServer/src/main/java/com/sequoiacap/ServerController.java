package com.sequoiacap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpResponse;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.sequoiacap.annotation.Generated;
import com.sequoiacap.model.UrlInfo;
import com.sequoiacap.util.HostUtil;
import com.sequoiacap.util.HttpClientUtil;
import com.sequoiacap.util.NumericConvertUtil;
import com.sequoiacap.util.Util;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author zoubin
 *
 */
@Api(value = "短域名服务接口", tags = "域名信息接口类")
@RestController
public class ServerController {
	private final int num = 62;
	private final int maxUrlLenth = 8;
	private final LoadingCache<String, String> longToShortCache = CacheBuilder.newBuilder().maximumSize(100000)
			.expireAfterAccess(3600, TimeUnit.SECONDS).concurrencyLevel(2).recordStats()
			.build(new CacheLoader<String, String>() {
				@Override
				public String load(String longUrl) throws Exception {
					String shortUrl = NumericConvertUtil.toOtherNumberSystem(seed.getAndAdd(step), num);
					if (shortUrl.length() > maxUrlLenth) {
						throw new IllegalArgumentException("ShortUrl is too long");
					}
					shortToLongMap.putIfAbsent(shortUrl, longUrl);
					return shortUrl;
				}
			});

	private final Map<String, String> shortToLongMap = new ConcurrentHashMap<>();

	private final String[] serverClusterAddr = buildServerCluster();
	private String currentServerAddr  = HostUtil.getLocalAddress() + ":" +Integer.parseInt(System.getProperty("serverPort", "8080"));
	
	private final int step = serverClusterAddr.length;
	private final AtomicLong seed = buildSeed();
	
	@PostMapping("/urlParse")
	@ApiOperation("接受长域名信息，返回短域名信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "longUrl", value = "长域名", required = true, paramType = "body") })
	@ApiResponses({ @ApiResponse(code = 408, message = "指定业务得报错信息，返回客户端"),
			@ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对") })
	public UrlInfo store(@RequestBody String longUrl) {
		if (longUrl == null) {
			return new UrlInfo(400, null, "长域名不能为空");
		}
		String targetServerUrl = serverClusterAddr[Math.abs(longUrl.hashCode()) % serverClusterAddr.length];
		if (currentServerAddr.equals(targetServerUrl)) {
			return getShortUrlFromCache(longUrl);
		} else {
			return forWard(targetServerUrl, HttpMethod.POST, longUrl);
		}
	}

	@Generated
	private UrlInfo getShortUrlFromCache(String longUrl) {
		try {
			return new UrlInfo(200, longToShortCache.get(longUrl));
		} catch (ExecutionException e) {
			return new UrlInfo(500, null, "获取长域名出错：" + e.getMessage());
		}
	}

	private String[] buildServerCluster() {
		String clusterStr = System.getProperty("serverCluster", HostUtil.getLocalAddress() + ":" +System.getProperty("serverPort", "8080"));
		return clusterStr.split(",");
	}

	public AtomicLong buildSeed() {
		for(int i = 0; i< serverClusterAddr.length; i++) {
			if(currentServerAddr.equals(serverClusterAddr[i])) {
				return new AtomicLong(i);
			}
		}
		throw new IllegalArgumentException("currentServerAddr can not match any configed server");
	}

	// TODO use grpc to instead http forward to enhance performance
	@Generated
	private UrlInfo forWard(String targetServerUrl, HttpMethod httpMethod, String url) {
		String baseUrl = String.format("http://%s/urlParse", targetServerUrl);
		HttpResponse resp = null;
		switch (httpMethod) {
		case GET:
			String fullUrl = new StringBuilder(baseUrl) //
					.append("/") //
					.append(url).toString();
			try {
				resp = HttpClientUtil.getUrl(fullUrl);
				return Util.handleResp(resp);
			} catch (Exception e) {
				String err = String.format("Failed to forward,err:%s", e.getMessage());
				return new UrlInfo(resp == null? 500: resp.getStatusLine().getStatusCode(), null,
						err);
			}
		case POST:
			try {
				resp = HttpClientUtil.postUrl(baseUrl, url);
				return Util.handleResp(resp);
			} catch (Exception e) {
				return new UrlInfo(resp == null? 500:resp.getStatusLine().getStatusCode(), null,
						String.format("Failed to forward,err:%s", e.getMessage()));
			}
		default:
			throw new UnsupportedOperationException("Unknown httpMethod:"+ httpMethod);
		}
	}

	@GetMapping("/urlParse/{shortUrl}")
	@ApiOperation("接受短域名信息，返回长域名信息")
	@ApiImplicitParams({ @ApiImplicitParam(name = "shortUrl", value = "短域名", required = true, paramType = "path") })
	@ApiResponses({ @ApiResponse(code = 408, message = "指定业务得报错信息，返回客户端"),
			@ApiResponse(code = 400, message = "请求参数没填好"), @ApiResponse(code = 404, message = "请求路径没有或页面跳转路径不对") })
	public UrlInfo get(@PathVariable("shortUrl") String shortUrl) {
		if (shortUrl == null) {
			return new UrlInfo(400, null, "短域名不能为空");
		}
		String targetServerUrl = serverClusterAddr[(int) NumericConvertUtil.toDecimalNumber(shortUrl, num)
				% serverClusterAddr.length];
		if (currentServerAddr.equals(targetServerUrl)) {
			if(shortToLongMap.containsKey(shortUrl)) {
				return new UrlInfo(200, shortToLongMap.get(shortUrl));
			}
			return new UrlInfo(404, null, "没找到相应的短域名"); 
		} else {
			return forWard(targetServerUrl, HttpMethod.GET, shortUrl);
		}
	}

	public AtomicLong getSeed() {
		return seed;
	}
}
