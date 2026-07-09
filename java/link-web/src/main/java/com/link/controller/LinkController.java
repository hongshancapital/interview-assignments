package com.link.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.link.service.LinkService;
import com.link.utils.ResultUtils;
import io.swagger.annotations.ApiOperation;
/**
 * <p>Long short address translation
 * @author lizeqiang
 */
@RestController
@RequestMapping("/link")
public class LinkController {
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private LinkService LinkService;

	@ApiOperation("Get short url by long url")
	@GetMapping("/getShortUrl")
	public Object getShortUrl(String longUrl) {
		if (StringUtils.isEmpty(longUrl)) {
			return ResultUtils.fail("The parameter “longUrl” cannot be empty!");
		} else {
			return ResultUtils.successData(LinkService.getShortUrl(longUrl));
		}
	}

	@ApiOperation("Get long url by short url")
	@GetMapping("/getLongUrl")
	public Object getLongUrl(String shortUrl) {
		if (StringUtils.isEmpty(shortUrl)) {
			return ResultUtils.fail("The parameter “shortUrl” cannot be empty!");
		} else {
			return ResultUtils.successData(LinkService.getLongUrl(shortUrl));

		}
	}

}
