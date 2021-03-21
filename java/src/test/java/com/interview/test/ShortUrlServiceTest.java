package com.interview.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;

import com.interview.exception.BizException;
import com.interview.exception.ExceptionCodeEnum;
import com.interview.service.ShortUrlService;

/**
 * 
 * @类名称   ShortUrlServiceTest.java
 * @类描述   <pre>service层测试类</pre>
 * @作者     zhangsheng
 * @创建时间 2021年3月21日下午8:49:37
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本           修改人 		    修改日期 		           修改内容描述
 *     ------------------------------------------------------
 *     1.00 	 zhangsheng 	2021年3月21日下午8:49:37             
 *     ------------------------------------------------------
 * </pre>
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ShortUrlServiceTest {
	
	//短域名前缀
	@Value("${short.url.prefix}")
	private String shortUrlPrefix;
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	//短域名缓存key前缀
	private static final  String KeyPrefix = "short_url_hash_md5_key_";
	
	//长域名缓存key前缀
    private static final  String longUrlKeyPrefix = "long_url_hash_key_";
	
	@Autowired
    private ShortUrlService shortUrlService;
	
	/**
	 * 
	 * @方法名称 testStoreShortUrl
	 * @功能描述 <pre>测试storeShortUrl方法</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午9:46:24
	 * @param longUrl
	 * @return
	 */
	@ParameterizedTest
	@ValueSource(strings = {"www.baidu.com", "www.baidu.com","www.sohu.com"})
	public void testStoreShortUrl(String longUrl) {
		String shortUrl = shortUrlService.storeShortUrl(longUrl);
		Assertions.assertTrue(shortUrl != null);
		Assertions.assertTrue(shortUrl.startsWith(shortUrlPrefix));
		int pos = shortUrl.indexOf(shortUrlPrefix);
		if(pos==-1) {
			throw new BizException(ExceptionCodeEnum.SHORT_URL_FORMAT_ERROR.getCode(), ExceptionCodeEnum.SHORT_URL_FORMAT_ERROR.getMessage());
		}
		String shortString = shortUrl.substring(pos + shortUrlPrefix.length());
		//从缓存中读出实际存储的字符串，与原值比较，确定相等
		String cacheLongUrl = stringRedisTemplate.opsForValue().get(longUrlKeyPrefix + shortString);
		Assertions.assertTrue(longUrl.equals(cacheLongUrl));
	}
	
	/**
	 * 
	 * @方法名称 testQueryLongUrlByShortUrl
	 * @功能描述 <pre>测试testQueryLongUrlByShortUrl方法</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午9:47:04
	 * @param shortUrl
	 * @return
	 */
	@ParameterizedTest
	@ValueSource(strings = {"http://00000002","00000002","http://00000002","http://00000005"})
	public void testQueryLongUrlByShortUrl(String shortUrl) {
		try {
			String longUrl = shortUrlService.queryLongUrlByShortUrl(shortUrl);
			Assertions.assertTrue(longUrl != null);
		} catch (BizException ex) {
			if(!shortUrl.startsWith(shortUrlPrefix)) {
				Assertions.assertTrue(ex.getErrorCode()==ExceptionCodeEnum.SHORT_URL_FORMAT_ERROR.getCode());
			}
		}
	}
}
