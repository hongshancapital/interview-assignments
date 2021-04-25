package com.example.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.ShortUrlEntity;
import com.utils.AjaxResult;
import com.utils.StringUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

/**
 * swagger 短域名接口
 * 
 * @author 帅
 */
@Api(value="短域名管理",tags="{短域名管理}")
@RestController
@RequestMapping("/api/url")
public class ApiShortUrlController {

	
	
		private final static Map<String, ShortUrlEntity> longMap = new LinkedHashMap<String, ShortUrlEntity>();
	    {
	    	longMap.put("www.longoooo.com", new ShortUrlEntity(1, "www.longoooo.com", "1020202"));
	    }
	 	private final static Map<String, ShortUrlEntity> shortMap = new LinkedHashMap<String, ShortUrlEntity>();
	    {
	    	shortMap.put("1020202", new ShortUrlEntity(1, "www.short.com", "1020202"));
	    }
	    /**
	     * 存储方式简单模拟
	     * @param longUrl
	     * @return
	     */
	    private ShortUrlEntity saveLongUrl(String longUrl)
	    {
	    	ShortUrlEntity shortObj = new ShortUrlEntity();
	    	boolean b = longMap.containsValue(longUrl);
	    	if(b)
	    	{
	    		return shortMap.get(longMap.get(longUrl));
	    	}
	    	else
	    	{
	    		String hashId = longUrl.hashCode()+"";
	    		shortObj = new ShortUrlEntity(1, "www.short.com",hashId);
	    		shortMap.put(hashId, shortObj);
	    	}
	    	return shortObj;
	    }
	
	
	    @ApiOperation("短域名存储接口")
	    @ApiImplicitParam(name = "longUrl", value = "新增长域名映射", dataType = "String")
	    @PostMapping("/save")
	    public AjaxResult save(String longUrl)
	    {
	        if (StringUtils.isNull(longUrl))
	        {
	            return AjaxResult.error("长链接不能为空");
	        }
	        return AjaxResult.success(saveLongUrl(longUrl));
	    }
	    
	    
	    @ApiOperation("短域名读取接口")
	    @ApiImplicitParam(name = "shortUrl", value = "短域名地址", required = true, dataType = "String", paramType = "path")
	    @GetMapping("/{shortUrl}")
	    public AjaxResult query(@PathVariable String shortUrl)
	    {
	        if (!shortMap.isEmpty() && shortMap.containsKey(shortUrl))
	        {
	            return AjaxResult.success(shortMap.get(shortUrl));
	        }
	        else
	        {
	        	return AjaxResult.error("短链接不能为空");
	        }
	    }
}
