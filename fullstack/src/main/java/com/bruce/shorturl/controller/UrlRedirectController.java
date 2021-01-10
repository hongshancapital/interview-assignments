package com.bruce.shorturl.controller;

import com.bruce.shorturl.exception.GenericException;
import com.bruce.shorturl.service.IShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 短链接跳转controller
 * @author bruce
 */
@Slf4j
@Controller
public class UrlRedirectController implements InitializingBean {

	@Autowired
	private IShortUrlService shortUrlService;

	@Override public void afterPropertiesSet() throws Exception {
		Assert.notNull(shortUrlService, "shortUrlService can't be null");
	}

	/**
	 * 长链接转短链接api
	 * @param hash
	 * @return
	 */
	@RequestMapping("/{hash}")
	public String hashRedirect(@PathVariable String hash){

		String fullUrl = null;
		try {
			fullUrl = shortUrlService.loadFullUrlByHash(hash);
		} catch (GenericException e) {
			return "error/shorturl";
		}
		return "redirect:"+fullUrl;
	}


}
