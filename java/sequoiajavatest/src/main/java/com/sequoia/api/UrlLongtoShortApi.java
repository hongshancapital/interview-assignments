package com.sequoia.api;


import org.springframework.stereotype.Service;

@Service
public interface UrlLongtoShortApi {
	
	public   String   transLongtoShortUrl(String longUrl);  
	
	public   String   transShorttoLongUrl(String url);  

}
