package com.interview.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.interview.exception.BizException;
import com.interview.exception.ExceptionCodeEnum;
import com.interview.service.ShortUrlService;
import com.interview.util.CompressTools;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {
	
	//短域名前缀
	@Value("${short.url.prefix}")
    private String shortUrlPrefix;
	
	//短域名在缓存中的超时时间
	@Value("${short.url.timeout}")
	private Long shortUrlTimeout;
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;
	
	//短域名缓存key前缀
	private static final  String KeyPrefix = "short_url_hash_md5_key_";
	
	//长域名缓存key前缀
    private static final  String longUrlKeyPrefix = "long_url_hash_key_";
 
    //序列号缓存key
    private static final  String serialNumberKey = "short_url_serial_number";
	
    /**
	 * 
	 * @方法名称 storeShortUrl
	 * @功能描述 <pre>将长域名转换为短域名并存储</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午12:34:57
	 * @param longUrl:长域名
	 * @return 生成短域名
	 */
	@Override
    public String storeShortUrl(String longUrl) {
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
        final String md5 = DigestUtils.md5DigestAsHex(longUrl.getBytes());
        String shortUrlKey = KeyPrefix + md5;
        String shortUrl = operations.get(shortUrlKey);
        if (shortUrl == null){
        	//加锁，假设单节点环境，分布式环境改用分布式锁
            synchronized (md5.intern()){
                shortUrl = operations.get(shortUrlKey);
                if (shortUrl == null){
                    Long value = stringRedisTemplate.opsForValue().increment(serialNumberKey, 1);
                    String shortString = CompressTools.compressNumber(value);
                    shortUrl = shortUrlPrefix + shortString;
                    //先放到缓存中模拟，真实环境可改到数据库中
                    operations.set(shortUrlKey, shortUrl, shortUrlTimeout, TimeUnit.DAYS);
                    operations.set(longUrlKeyPrefix + shortString, longUrl, shortUrlTimeout, TimeUnit.DAYS);
                }
            }
        }
        return shortUrl;
    }

	/**
	 * 
	 * @方法名称 queryLongUrlByShortUrl
	 * @功能描述 <pre>根据输入的短域名转换为长域名，并返回</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午12:36:53
	 * @param shortUrl:短域名
	 * @return 长域名
	 */
	@Override
	public String queryLongUrlByShortUrl(String shortUrl) {
		ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
		int pos = shortUrl.indexOf(shortUrlPrefix);
		if(pos==-1) {
			throw new BizException(ExceptionCodeEnum.SHORT_URL_FORMAT_ERROR.getCode(), ExceptionCodeEnum.SHORT_URL_FORMAT_ERROR.getMessage());
		}
		String shortString = shortUrl.substring(pos + shortUrlPrefix.length());
		String longUrl = operations.get(longUrlKeyPrefix + shortString);
		return longUrl;
	}

}
