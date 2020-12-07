package com.bruce.shorturl.controller;

import com.bruce.shorturl.exception.ErrorCode;
import com.bruce.shorturl.exception.GenericRuntimeException;
import com.bruce.shorturl.model.ResultStruct;
import com.bruce.shorturl.service.IShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 短链接api controller
 * @author bruce
 */
@Slf4j
@RestController
@RequestMapping("/api")
public class ShortUrlApiController implements InitializingBean {

	@Autowired
	private IShortUrlService shortUrlService;

	@Override public void afterPropertiesSet() throws Exception {
		Assert.notNull(shortUrlService, "shortUrlService can't be null");
	}

	/**
	 * 长链接转短链接api
	 * @param fullUrl
	 * @return
	 */
	@RequestMapping("/generateShortUrl")
	public ResultStruct<String> generateShortUrl(String fullUrl) {
		if(StringUtils.isBlank(fullUrl) || !StringUtils.startsWithIgnoreCase(fullUrl , "http")){
			//参数错误
			throw new GenericRuntimeException(ErrorCode.ERROR_CODE_SHORTURL_PARAM);
		}

		String shortUrl = shortUrlService.generateShortUrl(fullUrl);
		ResultStruct<String> result = ResultStruct.buildSucessResult(shortUrl);
		return result;
	}

}
