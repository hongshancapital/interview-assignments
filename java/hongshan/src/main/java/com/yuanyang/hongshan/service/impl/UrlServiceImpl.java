package com.yuanyang.hongshan.service.impl;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.google.common.util.concurrent.RateLimiter;
import com.yuanyang.hongshan.common.CommonConstant;
import com.yuanyang.hongshan.entity.RequestDTO;
import com.yuanyang.hongshan.entity.Result;
import com.yuanyang.hongshan.entity.ResultCode;
import com.yuanyang.hongshan.service.UrlService;
import com.yuanyang.hongshan.util.ConversionUtils;
import com.yuanyang.hongshan.util.SequenceGenerator;
import com.yuanyang.hongshan.util.UrlCacheUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.nio.charset.Charset;

/**
 * @Description: 短链接映射业务层实现
 * @Author: yuanyang
 * @Date: 2021-12-15
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

	private final SequenceGenerator sequenceGenerator;

	@Value("${urlMap.ratelimit}")
	private Integer qps;

	@Autowired
	private UrlCacheUtil urlCacheUtil;

	private static RateLimiter longUrlLimiter;

	private static RateLimiter shortUrlLimiter;

	private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), 100000, 0.03);

	@PostConstruct
	public void init(){
		longUrlLimiter = RateLimiter.create(qps);
		shortUrlLimiter = RateLimiter.create(qps);
	}

	private final UrlValidator urlValidator = new UrlValidator(new String[]{CommonConstant.HTTP_PROTOCOL,
			CommonConstant.HTTPS_PROTOCOL});

	@Override
	public Result<String> generateShortURL(RequestDTO requestDTO) {
		String longUrl = requestDTO.getLongUrl();
		if(!urlValidator.isValid(longUrl)){
			return Result.newErrorResult(ResultCode.ILLEGAL_PARAM);
		}
		longUrlLimiter.acquire();
		//根据雪花算法生成唯一的id，进行62进制转换，拼接成唯一的短链，然后存储在JVM内存中。
		String shortUrl = generateShortURLBySnake(longUrl);
		urlCacheUtil.put(shortUrl,longUrl);
		bloomFilter.put(shortUrl);
		return new Result<>(shortUrl);
	}

	private String generateShortURLBySnake(String longURL) {
		String shortUrl = getAvailableCompressCode();
		return shortUrl;
	}

	private String getAvailableCompressCode() {
		String code ="";
		long sequence = sequenceGenerator.generate();
		code = ConversionUtils.X.encode62(sequence);
		return code.substring(code.length() - 6);
	}

	@Override
	public Result<String> getLongUrlByShortUrl(RequestDTO requestDTO) {
		String shortURL = requestDTO.getShortUrl();
		boolean mightContain = bloomFilter.mightContain(shortURL);
		if(!mightContain){
			log.error("please check url,maybe not exist:"+shortURL);
			return Result.newErrorResult(ResultCode.ILLEGAL_DATA);
		}
		shortUrlLimiter.acquire();
		String longUrl = urlCacheUtil.get(shortURL);
		if(StringUtils.isEmpty(longUrl)){
			return Result.newErrorResult(ResultCode.ILLEGAL_DATA_NO_VALID);
		}
		return new Result<>(longUrl);
	}
}
