package com.interview.api.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.interview.api.ShortUrlApi;
import com.interview.dto.ShortUrlDto;
import com.interview.exception.BizException;
import com.interview.exception.ExceptionCodeEnum;
import com.interview.response.RestResponse;
import com.interview.service.ShortUrlService;

@RestController
@RequestMapping("/shorturl")
public class ShortUrlApiImpl implements ShortUrlApi {
	
	public final int ARG_IS_NULL = 11;
	
	public final int ARG_FORMAT_ERROR = 12;
	
	public final int TOTAL_EXTEND_LIMIT = 13;
	
	//短域名前缀
	@Value("${short.url.prefix}")
	private String shortUrlPrefix;
	
	@Autowired
	public ShortUrlService ShortUrlService;

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
	@PostMapping("/store")
	public RestResponse<ShortUrlDto> storeShortUrl(@RequestBody ShortUrlDto dto) {
		if(StringUtils.isEmpty(dto.getLongUrl())) {
			return RestResponse.fail(ARG_IS_NULL, "参数不能为空！");
		}
		String shortUrl = null;
		try {
			shortUrl = ShortUrlService.storeShortUrl(dto.getLongUrl());
		} catch (BizException e) {
			if(ExceptionCodeEnum.COMPRESS_NUM_TOO_LONG.getCode().equals(e.getErrorCode())) {
				return RestResponse.fail(TOTAL_EXTEND_LIMIT, "数量已超系统上限！");
			}
			throw e;
		}
		dto.setShortUrl(shortUrl);
		return RestResponse.ok(dto);
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
	@PostMapping("/find")
	public RestResponse<ShortUrlDto> queryLongUrlByShortUrl(@RequestBody ShortUrlDto dto) {
		String shortUrl = dto.getShortUrl();
		if(StringUtils.isEmpty(shortUrl)) {
			return RestResponse.fail(ARG_IS_NULL, "参数不能为空！");
		}
		int pos = shortUrl.indexOf(shortUrlPrefix);
		if(pos==-1) {
			return RestResponse.fail(ARG_FORMAT_ERROR, "参数格式错误！");
		}
		String longUrl = null;
		try {
			longUrl = ShortUrlService.queryLongUrlByShortUrl(shortUrl);
		} catch (BizException e) {
			if(ExceptionCodeEnum.SHORT_URL_FORMAT_ERROR.getCode().equals(e.getErrorCode())) {
				return RestResponse.fail(ARG_FORMAT_ERROR, "参数格式错误！");
			}
			throw e;
		}
		dto.setLongUrl(longUrl);
		return RestResponse.ok(dto);
	}

}
