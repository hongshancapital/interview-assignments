package com.interview.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.interview.dto.ShortUrlDto;
import com.interview.response.RestResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @类名称   com.interview.api.ShortUrlApi.java
 * @类描述   <pre>短域名处理api接口类</pre>
 * @作者     zhangsheng
 * @创建时间 2021年3月21日下午12:33:29
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本           修改人 		    修改日期 		           修改内容描述
 *     ------------------------------------------------------
 *     1.00 	 zhangsheng 	2021年3月21日下午12:33:29             
 *     ------------------------------------------------------
 * </pre>
 */
@Api(value = "短域名接口",tags = "ShortUrlApi")
@RequestMapping("/shorturl")
public interface ShortUrlApi {
	
	/**
	 * 
	 * @方法名称 storeShortUrl
	 * @功能描述 <pre>将长域名转换为短域名并存储</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午12:34:57
	 * @param longUrl:长域名
	 * @return 生成短域名
	 */
	@ApiOperation( value = "将长域名转换为短域名并存储", httpMethod = "POST", code = 0 )
	@PostMapping("/store")
	public RestResponse<ShortUrlDto> storeShortUrl(@RequestBody ShortUrlDto dto);
	
	/**
	 * 
	 * @方法名称 queryLongUrlByShortUrl
	 * @功能描述 <pre>根据输入的短域名转换为长域名，并返回</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午12:36:53
	 * @param shortUrl:短域名
	 * @return 长域名
	 */
	@ApiOperation( value = "通过短域名找到对应长域名", httpMethod = "POST", code = 0 )
	@PostMapping("/find")
	public RestResponse<ShortUrlDto> queryLongUrlByShortUrl(ShortUrlDto dto);

}
