
/* Auto create by ListCode.CN at 20 Jun 2021 20:32:08 */
package com.leo.rest;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.leo.config.AppConfig;
import com.leo.store.MemStore;
import com.leo.utils.ShortHash;
import com.leo.utils.UrlUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;


/**
 * 短url系统Rest接口
 * @author LeoZhang
 *
 */
@Controller
@lombok.extern.slf4j.Slf4j
@Api(tags = "api for short url system")
public class ShortUrlController {

	@Autowired
	private AppConfig appConfig;
	

    @RequestMapping( value="/short2normal",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiOperation("short url to normal url")
    public String shortUrlToNormal(HttpServletRequest request,HttpServletResponse response, @RequestParam(name="shortUrl", required = true) String shortUrl) {
        log.info("shortUrlToNormal...");
      //0. 空检查，可能告知失败
        if (shortUrl == null || shortUrl.isBlank()) {
        	wirteResponseOnError(response, 406, "input shortUrl is empty");
        	return null;
        }
        shortUrl = shortUrl.trim();
        if (shortUrl.length() != 7 && shortUrl.length() != 8) {
        	wirteResponseOnError(response, 406, "shortUrl size must be 7 or 8");
        	return null;
        }
        
        String url = MemStore.queryByShort(shortUrl);
        if (url == null) {
        	if (url == null || url.isBlank()) {
        		wirteResponseOnError(response, 404, null);
            	return null;
            }
        }
        return url;
    }
    
    private void wirteResponseOnError(HttpServletResponse response, int statusCode, String message) {
    	response.setStatus(statusCode);
    	try {
    		if (message != null) {
    			response.getWriter().write(message);
    		}
    		response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    @RequestMapping( value="/normal2short",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiOperation("normal url to short url")
    public String normalUrlToShort(HttpServletRequest request,HttpServletResponse response, @RequestParam(name="url", required = true) String url) {
        log.info("normalUrlToShort..." + this.appConfig.getUrlMaxLength());
        //0. 空检查，可能告知失败
        if (url == null || url.isBlank()) {
        	wirteResponseOnError(response, 406, "input url is empty");
        	return null;
        	
        }
        url = url.trim();
        //1. 检查url是否包含协议头，没有的话，自动补齐http://
        if (!UrlUtils.hasProtocal(url)) {
        	url = "http://" + url;
        }
        //2. 长度检查，可能告知失败
        if (url.length() > this.appConfig.getUrlMaxLength()) {
        	wirteResponseOnError(response, 406, "input url is too long");
        	return null;
        }
        
        //3. url整体格式检查，可能告知失败 
        if (!UrlUtils.isURI(url)) {
        	//格式错误
        	wirteResponseOnError(response, 406, "input content is not a url");
        	return null;
        }
        //4. 协议头与域名转小写
        url = UrlUtils.urlToLowerCase(url);
        //5. 写入，并获得短url 
        String shortHash = ShortHash.toShortHash(url);
        String shortUrl = MemStore.store(shortHash, url);
        return shortUrl;
    }


	
}
