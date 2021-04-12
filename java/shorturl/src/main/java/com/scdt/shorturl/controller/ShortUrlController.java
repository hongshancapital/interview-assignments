package com.scdt.shorturl.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import com.scdt.shorturl.service.ShortUrlService;
import com.scdt.shorturl.util.NumericConvertUtil;

/**
 * 长地址转短地址的控制器
 * @author 周小建
 */
@Controller
@RequestMapping("/shorturl")
@Api(value="ShortUrlController|长地址转短地址的控制器")
public class ShortUrlController{
	@Autowired private ShortUrlService shortUrlService;
	
	private String render(String msg){
		HttpServletResponse response=((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getResponse();
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");
		try{
			PrintWriter out=response.getWriter();
			out.write(msg);
			out.flush();
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}
	
	@ResponseBody
	@RequestMapping(value="/long2short",method=RequestMethod.GET)
	@ApiOperation(value="长地址转短地址",notes="originalUrl必填且须是较长的地址（不带前缀），workerId和serviceId选填但不能超过阈值。请求时类似这样http://localhost:8080/shorturl/long2short?shortUrl=aaaaaaaaaaaaaa，其中aaaaaaaaaaaaaa表示长地址")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="originalUrl",value="长地址",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="workerId",value="机器id",required=false,dataType="Long"),
		@ApiImplicitParam(paramType="query",name="serviceId",value="服务id",required=false,dataType="Long")
	})
	public String long2short(@RequestParam("originalUrl")String originalUrl,@RequestParam(value="workerId",required=false)Long workerId,@RequestParam(value="serviceId",required=false)Long serviceId){
		String shortUrl=shortUrlService.long2short(originalUrl,workerId,serviceId);
		//render("{\"flag\":\"query\",\"msg\":\"长地址转短地址\",\"长地址\":"+originalUrl+",\"短地址\":"+shortUrl+"}");
		return shortUrl;
	}

	@ResponseBody
	@RequestMapping(value="/example/{originalUrl}",method=RequestMethod.GET)
	@ApiOperation(value="访问短地址跳转到长地址",notes="某个有效短地址被过滤器拦截并转发到对应的长地址，请求时类似这样http://localhost:8080/shorturl/t/bbbbbbbb，必须有/t/前缀，bbbbbbbb表示短地址")
	@ApiImplicitParams({
		@ApiImplicitParam(paramType="query",name="originalUrl",value="长地址",required=true,dataType="String"),
		@ApiImplicitParam(paramType="query",name="shortUrl",value="短地址",required=false,dataType="String")
	})
	public String example(@PathVariable("originalUrl")String originalUrl,@RequestParam(required=false)String shortUrl){
		Long serialNumeric=ShortUrlService.long2shortMap.get(originalUrl);
		if(serialNumeric==null){//直接访问无效长地址
			return render("{\"flag\":\"error\",\"msg\":\"无效长地址\",\"data\":"+originalUrl+"}");
		}
		HttpServletRequest request=((ServletRequestAttributes)(RequestContextHolder.currentRequestAttributes())).getRequest();
		String url=request.getRequestURL().toString();
		String shortUrlName;
		if(shortUrl==null||!(shortUrlName=shortUrl.substring(shortUrl.lastIndexOf("/")+1)).equals(NumericConvertUtil.toOtherNumber(serialNumeric))){//如果当前短地址不存在或与真实短地址不一致，则属于直接访问有效长地址。
			return render("{\"flag\":\"query\",\"现路径\":"+url+",\"长地址\":"+originalUrl+"}");
		}
		//先访问有效短地址，被过滤器拦截转为有效长地址后跳转至此。
		return render("{\"flag\":\"forward\",\"msg\":\"短地址转长地址\",\"原路径\":"+shortUrl+",\"现路径\":"+url+",\"短地址\":"+shortUrlName+",\"长地址\":"+originalUrl+"}");
	}
}