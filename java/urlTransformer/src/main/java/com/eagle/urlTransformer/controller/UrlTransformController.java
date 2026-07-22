package com.eagle.urlTransformer.controller;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.eagle.urlTransformer.service.UrlTransformService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;


/** 
 * @ClassName: UrlTransformController 
 * @Description: 长、短链接转换接口 
 * @author Eagle
 * @date 2022年1月18日 下午6:30:15 
 *  
 */
@Api(tags = "长、短链接转换接口")
@RestController
public class UrlTransformController {
	
	@Resource(name = "urlTransformServiceImplNoLock")
	private UrlTransformService urlTransformService;
	
    @ApiOperation(value = "存储长链接", notes="存储长链接并返回对应的短链接")
    @ApiImplicitParams({
                @ApiImplicitParam(name = "longUrl", value = "长链接", dataType = "String", required = true, defaultValue = "")
        })
	@PostMapping("/saveUrl")
	public String saveLongUrl(String longUrl) {
		return urlTransformService.saveLongUrl(longUrl);
		
	}
	
    @PostMapping("/getUrl")
    @ApiOperation(value = "根据短链接获取对应的长链接", notes="根据短链接获取对应的长链接")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "shortUrl", value = "短链接", dataType = "String", required = true, defaultValue = "")
})
	public String getLongUrlByShortUrl(String shortUrl) {
		return urlTransformService.getLongUrlByShortUrl(shortUrl);
	}
	
}
