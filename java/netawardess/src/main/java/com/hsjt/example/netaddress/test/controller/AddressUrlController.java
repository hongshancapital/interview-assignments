package com.hsjt.example.netaddress.test.controller;


import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hsjt.example.netaddress.common.bean.ResultData;
import com.hsjt.example.netaddress.common.bean.UserKeyIdVO;
import com.hsjt.example.netaddress.common.bean.WebShotUrlVO;
import com.hsjt.example.netaddress.common.check.CheckUtils;
import com.hsjt.example.netaddress.common.utils.SysUtils;
import com.hsjt.example.netaddress.test.service.AddressUrlService;
import com.hsjt.example.netaddress.test.util.ShortUrlUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * 
 * @author dazzling
 *
 */
@RestController
@RequestMapping("/addressUrl")
@Api(tags = "长短网址转换控制层")
public class AddressUrlController {
	
	
	@Autowired
	private AddressUrlService addressUrlService;
	
	// 模拟创建用户
	@PostMapping(value="addUser")
	@ApiOperation(value = "模拟创建用户", notes = "创建用户", produces = "application/json")
	@ApiImplicitParam(name = "addUser", value = "用户对象", paramType = "body", required = true, dataType = "UserKeyIdVO")
	public UserKeyIdVO creatUser(@RequestBody UserKeyIdVO user) {
		if(user == null ) {
			user = new UserKeyIdVO();
		}
		if(CheckUtils.isEmpty(user.getId())){
			user.setId(UUID.randomUUID().toString());
		}
		if(CheckUtils.isEmpty(user.getKeyId())){
			user.setKeyId(SysUtils.sysKey);
		}
		ShortUrlUtils.userMap.put(user.getId(), user);
		return user;
	 }
	
	
	/**
     * 短路径转长路径 
     * @param shortUrl
     * @return
     */
	@PostMapping(value="getLongurl")
	@ApiOperation(value = "短路径转长路径", notes = "备注信息", produces = "x-www-form-urlencoded")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "shortUrl", value = "短网址", paramType = "body", required = true, dataType = "String"),
	    @ApiImplicitParam(name = "userId",value="用户ID",required=false,paramType="body", dataType = "String")
	})
	@ApiResponses({
	    @ApiResponse(code=602,message="没有找到长网址"),
	    @ApiResponse(code=601,message="用户失效"),
	    @ApiResponse(code=600,message="请求网址校验不对")
	})
    public ResultData getLongurl(@RequestParam String shortUrl,@RequestParam(required=false,name="userId") String userId) {
		if(CheckUtils.isUrl(shortUrl)) {
			 UserKeyIdVO vo = null;
			 if(CheckUtils.isEmpty(userId)) {
				  vo = ShortUrlUtils.userMap.get(SysUtils.userId);
			 }else {
				  vo = ShortUrlUtils.userMap.get(userId);
				 if(CheckUtils.isEmpty(vo)) {
					 return ResultData.error("601","没有找到用户");
					// throw new RuntimeException("没有找到用户");
				 }
			 }
			 String uid = vo.getId()+":";
			 WebShotUrlVO longurl = ShortUrlUtils.urlMap.get(ShortUrlUtils.SHORTURL+uid+shortUrl);
	       	 if(StringUtils.isEmpty(longurl)) {
	       		return  ResultData.error("602","没有找到长网址");
	   		 }
	       	return ResultData.ok(longurl.getLongUrl());
		} else {
			  return ResultData.error("600","请输入正确的网址");
		}
    	   
    }
	
	 
	/**
	 * 长网址转短网址
	 * @param longUrl
	 * @return
	 */
	@PostMapping(value="getShortUrl")
	@ApiOperation(value = "长网址转短网址", notes = "备注信息", produces = "x-www-form-urlencoded")
	@ApiImplicitParams({
		@ApiImplicitParam(name = "longUrl", value = "长网址", paramType = "body", required = true, dataType = "String"),
	    @ApiImplicitParam(name = "userId",value="用户ID",required=false,paramType="body", dataType = "String")
	})
	@ApiResponses({
	    @ApiResponse(code=700,message="生成短网址失败"),
	    @ApiResponse(code=601,message="用户失效"),
	    @ApiResponse(code=600,message="请求网址校验不对")
	})
	public ResultData getShortUrl(@RequestParam  String longUrl,@RequestParam(required=false) String userId) {
		if(CheckUtils.isUrl(longUrl)) {
			return ResultData.ok(addressUrlService.getShortUrl(longUrl, userId));
		} else {
			  return ResultData.error("600","请输入正确的网址");
		}
	}
	  

}
