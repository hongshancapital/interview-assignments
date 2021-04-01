package com.sequoiacap.web.shorturl.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sequoiacap.data.Response;
import com.sequoiacap.shorturl.model.SUrl;
import com.sequoiacap.shorturl.service.SUrlManager;
import com.sequoiacap.web.shorturl.data.SUrlData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import com.sequoiacap.errorcode.ErrorCode;
import com.sequoiacap.errorcode.ErrorCodeStr;
import com.sequoiacap.exception.BusinessException;

import jodd.util.StringUtil;
import net.jhorstmann.i18n.I18N;

@Controller
@RequestMapping("/s")
@Api(value = "ShorturlController | The API for shorturl")
public class ShorturlController
{
	@Resource(name = "redisTemplate")
	private ValueOperations<String, String> valOps;
	
	@Autowired
	private SUrlManager sUrlManager;

	@ResponseBody
	@RequestMapping(value = "/set", method = RequestMethod.GET)
    @ApiOperation(value = "Generate a short url of the url given.", httpMethod = "GET")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "url",
        	value = "The url be converted into a shorturl. the length of url should not exceed 65535.",
        	required = true, dataTypeClass = String.class),
        @ApiImplicitParam(paramType="query", name = "type",
        	value = "The type of request.",
        	allowableValues = "temporary - temporary short url. short life, short_period - short url is recent effective in 3 days, permanent - short url is never invalid",
        	required = false, dataTypeClass = String.class),
        @ApiImplicitParam(paramType="query", name = "lang",
        	value = "The language of response",
        	allowableValues = "en - english, zh_CN - simplified chinese",
        	required = false, dataTypeClass = String.class)
    })
	@ApiResponses({
		@ApiResponse(code = 200, response = SUrlData.class, message = "success"),
		@ApiResponse(code = ErrorCode.TOO_LONG_URL, message = "The url is too long, exceeed 65535"),
		@ApiResponse(code = ErrorCode.MANY_REQUEST_SAME_URL, message = ErrorCodeStr.STR_MANY_REQUEST_SAME_URL),
		@ApiResponse(code = ErrorCode.INVALID_TYPE, message = ErrorCodeStr.STR_INVALID_TYPE),
		@ApiResponse(code = ErrorCode.NOT_FOUND, message = ErrorCodeStr.STR_NOT_FOUND),
		@ApiResponse(code = ErrorCode.INVALID_URL, message = ErrorCodeStr.STR_INVALID_URL),
		@ApiResponse(code = ErrorCode.FAILED_SURL, message = ErrorCodeStr.STR_FAILED_SURL)
	})
	public Response<SUrlData> set(String url, String type, HttpServletRequest _request)
	{
		Response<SUrlData> response = new Response<SUrlData>();
		
		if (StringUtils.isBlank(url))
		{
			throw new BusinessException(
				ErrorCode.INVALID_URL, I18N.tr(ErrorCodeStr.STR_INVALID_URL));
		}

		SUrl.Type _type = SUrl.Type.temporary;
		
		if (StringUtil.isNotBlank(type))
		{
			try
			{
				_type = SUrl.Type.valueOf(type);
			} catch(Exception e)
			{
				throw new BusinessException(
					ErrorCode.INVALID_TYPE, I18N.tr(ErrorCodeStr.STR_INVALID_TYPE));
			}
		}
		
		String ip = _request.getRemoteAddr();

		response.setResult(SUrlData.valueOf(sUrlManager.set(url, _type, ip)));

		return response;
	}
	
	@ResponseBody
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	@ApiOperation(value = "Request the original url of the short url given.", httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "surl",
        	value = "The short url given to find out original url.",
        	required = true, dataTypeClass = String.class),
        @ApiImplicitParam(paramType="query", name = "lang",
        	value = "The language of response",
        	allowableValues = "en - english, zh_CN - simplified chinese",
        	required = false, dataTypeClass = String.class)
    })
	@ApiResponses({
		@ApiResponse(code = 200, response = SUrlData.class, message = "success"),
		@ApiResponse(code = ErrorCode.NOT_FOUND, message = ErrorCodeStr.STR_NOT_FOUND)
	})
	public Response<SUrlData> get(String surl)
	{
		Response<SUrlData> response = new Response<SUrlData>();

		response.setResult(SUrlData.valueOf(sUrlManager.get(surl)));
		if (response.getResult() == null)
		{
			throw new BusinessException(
				ErrorCode.NOT_FOUND, I18N.tr(ErrorCodeStr.STR_NOT_FOUND));
		}
		
		return response;
	}
	
	@RequestMapping(value = "/r/{surl}", method = RequestMethod.GET)
	@ApiOperation(value = "Redirect the original url of the short url given.", httpMethod = "GET")
	@ApiImplicitParams({
        @ApiImplicitParam(paramType="path", name = "surl",
        	value = "The short url given to find out original url.",
        	required = true, dataTypeClass = String.class)
    })
	@ApiResponses({
		@ApiResponse(code = 403, message = "Inform that client's browser redirect to the original url of the short url given."),
		@ApiResponse(code = ErrorCode.NOT_FOUND, message = ErrorCodeStr.STR_NOT_FOUND)
	})
	public String redirect(@PathVariable("surl") String surl)
	{
		Response<SUrlData> response = get(surl);

		return "redirect:" + response.getResult().getUrl();
	}
}
