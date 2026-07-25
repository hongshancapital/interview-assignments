package com.sequoia.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sequoia.api.UrlLongtoShortApi;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/* 类注解 */
@Api(value = "desc of class")
@RestController
public class TaskController {
	
	 @Autowired  
	 private UrlLongtoShortApi LongtoShortApi;


    /* 方法注解 */
    @ApiOperation(value = "desc of method", notes = "")
    @GetMapping(value="/urllongtoshrot")
    public Object longtoshrot( /* 参数注解 */ @ApiParam(value = "desc of param" , required=true ) @RequestParam String name) {
    	String url = "";
		try {
			url = LongtoShortApi.transLongtoShortUrl(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return url;
    }
    
    /* 方法注解 */
    @ApiOperation(value = "desc of method", notes = "")
    @GetMapping(value="/getLongUrl")
    public Object getShortUrltoLong( /* 参数注解 */ @ApiParam(value = "desc of param" , required=true ) @RequestParam String name) {
    	String url = "";
		try {
			url = LongtoShortApi.transShorttoLongUrl(name);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return url;
    }
    
}
