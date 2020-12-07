package com.bruce.shorturl.service.impl;

import com.bruce.shorturl.exception.ErrorCode;
import com.bruce.shorturl.exception.GenericException;
import com.bruce.shorturl.exception.GenericRuntimeException;
import com.bruce.shorturl.service.IShortUrlService;
import com.bruce.shorturl.storage.IShortUrlStorage;
import com.bruce.shorturl.util.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.MurmurHash3;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


/**
 * 短域名 service
 * @author bruce
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements IShortUrlService, InitializingBean {


	/** 冲突时的key */
	private static final String CONFLICT_KEY = "_CONFLICT";

	@Value("${shorturl.scheme}")
	private String scheme;
	@Value("${shorturl.host}")
	private String host;
	@Value("${shorturl.maxTryTimes}")
	private int maxTryTimes;


	@Autowired
	private IShortUrlStorage shortUrlStorage;

	@Override public void afterPropertiesSet() throws Exception {
		Assert.notNull(shortUrlStorage, "shortUrlStorage can't be null");
	}

	/**
	 * 长链接转短链接
	 *
	 *
	 * @param fullUrl
	 * @return
	 */
	@Override public String generateShortUrl(String fullUrl) {
		return generateShortUrl(fullUrl, null, 0);
	}


	/**
	 * 短域名
	 * @param fullUrl
	 * @param salt
	 * @param triedTimes
	 * @return
	 */
	@Override
	public String generateShortUrl(String fullUrl, String salt, int triedTimes) {
		log.debug("[ShortUrlServiceImpl][generateShortUrl]enter: {}", fullUrl, salt, triedTimes);

		if(StringUtils.isBlank(fullUrl) || !StringUtils.startsWith(fullUrl , "http")){
			//参数错误
			throw new GenericRuntimeException(ErrorCode.ERROR_CODE_SHORTURL_PARAM);
		}

		if(triedTimes>maxTryTimes){
			//超过次数
			throw new GenericRuntimeException(ErrorCode.ERROR_CODE_SHORTURL);
		}

		String plainText = StringUtils.isBlank(salt)?fullUrl: fullUrl+salt;

		long hashValue = MurmurHash3.hash32x86(plainText.getBytes());
		String hashText = MathUtils._10_to_spec(hashValue);

		//判断hashText是否存在
		if(shortUrlStorage.notExists(hashText)){
			//hash不存在，则写入
			shortUrlStorage.putValueByHash(hashText, fullUrl);
		}else{
			//hash存在，还需判断是否存在hash冲突
			String existsFullUrl = shortUrlStorage.loadValueByHash(hashText);
			if(!StringUtils.equals(existsFullUrl, fullUrl)){

				//有冲突，尝试增加salt，重新hash
				salt = StringUtils.isBlank(salt)?CONFLICT_KEY:salt + CONFLICT_KEY;
				return generateShortUrl(fullUrl, salt, triedTimes+1);
			}
		}
		String result = populateShortUrl(hashText);
		log.info("[ShortUrlServiceImpl][generateShortUrl]result:{}, {}", result, fullUrl);
		return result;
	}

	/**
	 * 短链hash转长链接
	 *
	 * @param hashText
	 * @return
	 */
	@Override public String loadFullUrlByHash(String hashText) throws GenericException {
		//判断hashText是否存在
		if(shortUrlStorage.notExists(hashText)){
			throw new GenericException(ErrorCode.ERROR_CODE_SHORTURL_NOT_EXISTS, "短域名不存在");
		}
		String result = shortUrlStorage.loadValueByHash(hashText);
		return result;
	}


	/**
	 * 组装为短链接地址
	 * @param hashText
	 * @return
	 */
	private String populateShortUrl(String hashText){

		if(!StringUtils.equalsIgnoreCase(scheme, "http") || !StringUtils.equalsIgnoreCase(scheme, "https")){
			scheme = "http";
		}

		String prefix = scheme + "://"+ host +"/";
		String result = prefix + hashText;
		return result;
	}
}
