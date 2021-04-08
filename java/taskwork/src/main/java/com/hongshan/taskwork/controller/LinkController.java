package com.hongshan.taskwork.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hongshan.taskwork.dao.DefaultDomainMapper;
import com.hongshan.taskwork.dao.LinkMapper;
import com.hongshan.taskwork.model.Link;
import com.hongshan.taskwork.service.LinkService;
import com.hongshan.taskwork.util.IDGenerator;
import com.hongshan.taskwork.util.URLShortener;


@RestController
@RequestMapping("link")
public class LinkController {

    @Autowired
    LinkService linkService;
    @Autowired
    LinkMapper linkMapper;
    @Autowired
    DefaultDomainMapper defaultDomainMapper;
    
    @GetMapping(value="/getShortUrl")
    public String getShortUrl(String longUrl, String code) {
    	if(StringUtils.isEmpty(longUrl) || longUrl.trim().isEmpty())
    		return "Err, because long url must be not null";
    	
    	String id = IDGenerator.generate(longUrl);
    	String shortedUrl = null;
    	shortedUrl = linkMapper.queryById(id);
    	if(!StringUtils.isEmpty(shortedUrl))
    		return shortedUrl;
    	
    	
    	if(StringUtils.isEmpty(code) || code.trim().isEmpty() || code.trim().length() !=2 )
    		return "Err, because null code or code lenth not 2 is not allowed!";
    	
    	String domainName = defaultDomainMapper.queryDomainNameByCode(code);
    	if(StringUtils.isEmpty(domainName)) {
    		return "Err, because null domain name is not allowed!";
    	}
    	URLShortener shortener = new URLShortener(8, domainName);
    	shortedUrl = shortener.shortenURL(longUrl);
    	
    	if(StringUtils.isEmpty(shortedUrl)) {
    		return "Err, because shortedUrl compute wrong!";
    	}
    	
    	Link link = new Link();
    	link.setId(id);
    	link.setLongUrl(longUrl);
    	link.setShortUrl(shortedUrl);
    	link.setPrefixDomain(domainName);
    	link.setPrefixDomainType(code);
    	link.setShortUrlSuffix(shortedUrl.substring(shortedUrl.lastIndexOf("/")+1));
    	linkService.saveLink(link);
    	
    	return shortedUrl;
    }
    
    @GetMapping(value="/getOriginalUrlByShortUrl")
    public String getLongUrl(String shortUrl) {
    	if(StringUtils.isEmpty(shortUrl) || shortUrl.trim().isEmpty())
    		return "Err, because short url must be not null";
    	shortUrl = URLShortener.sanitizeURL(shortUrl.trim());
    	String domainName = shortUrl.substring(0, shortUrl.lastIndexOf("/"));
    	String prefixDomainType = defaultDomainMapper.queryCodeByDomainName(domainName);
    	if(prefixDomainType.isEmpty()) {
    		return "Err, because short url is not valid!";
    	}
    	String shortUrlSuffix = shortUrl.substring(shortUrl.lastIndexOf("/")+1);
    	String returnLongUrl = linkMapper.queryLongUrlByShortUrl(prefixDomainType, shortUrlSuffix);
    	return returnLongUrl;
    }
}