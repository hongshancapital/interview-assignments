package com.cicc.domain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cicc.domain.services.TinyURL;
import com.cicc.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;

@Api(value = "实现短域名服务")
@RestController
@RequestMapping("api")
public class TinyURLController {
	@Autowired
	private TinyURL tinyURL;
	
	private Result result =new Result();

	@ApiOperation(value = "短域名存储接口", notes = "接受长域名信息，返回短域名信息；保存短域名信息在JVM内存")
	@ApiImplicitParams({ 
       @ApiImplicitParam(name = "longUrl", value = "长域名信息", required = true, dataType = "String"),
	   @ApiImplicitParam(name = "tinyLen", value = "生成的短域名长度（最长为8位）", required = true, dataType = "int")
       })
	@ApiModelProperty(value = "成功：返回短域名信息 失败：'创建短域名失败'")
	@RequestMapping(value = "/create", method = RequestMethod.GET)
    @ResponseBody
	public Result createTinyUrl(@RequestParam(name = "longUrl")  String longUrl,@RequestParam(name = "tinyLen")  int tinyLen) {
		
		if (StringUtils.isEmpty(longUrl) ) {
			result.setCode("801");
			result.setMessage("请求参数不能为空");
		}
		if (longUrl.length()>2038 ) {
			result.setCode("801");
			result.setMessage("长域名长度最大为2038个字符");
		}
		if (tinyLen>8 ) {
			result.setCode("801");
			result.setMessage("短域名长度最大为8个字符");
		}
		//转换短域名
		String tinyUrlSeq = tinyURL.createTinyUrl(longUrl,tinyLen);
		//短域名创建失败
		if (StringUtils.isEmpty(tinyUrlSeq)) {
			result.setCode("901");
			result.setMessage("生成短域名失败");
		}else{
			//生成短域名成功
			result.setCode("0");
			result.setMessage(tinyUrlSeq);
		}
		
		return result;
	}
	
	@ApiOperation(value="短域名读取接口", notes="接受短域名信息，返回长域名信息；从JVM内存中获取长域名信息")
	@ApiImplicitParam(name = "tinyUrl", value = "短域名信息", required = true, dataType = "String")
	@ApiModelProperty(value = "长域名信息 或 'Not Found'")
	@RequestMapping(value = "/get", method = RequestMethod.GET)
    @ResponseBody
	public Result getTinyUrl(@RequestParam(name = "tinyUrl")  String tinyUrl) {
		
		if (StringUtils.isEmpty(tinyUrl)) {
			result.setCode("801");
			result.setMessage("请求参数不能为空");
		}
		
		//转换短域名
		String longUrlSeq = tinyURL.getLongUrl(tinyUrl);
		//获取短域名失败
		if (StringUtils.isEmpty(longUrlSeq)) {
			result.setCode("404");
			result.setMessage("Not Found");
		}else{
			//获取长短域名成功
			result.setCode("0");
			result.setMessage(longUrlSeq);
		}
	
		return result;
	}

}
