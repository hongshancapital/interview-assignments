package com.xinerde.demo.shortlink.impl;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.util.StringUtils;

import com.google.common.hash.BloomFilter;
import com.xinerde.demo.shortlink.core.utils.EhCacheUtil;
import com.xinerde.demo.shortlink.core.utils.Long62Util;
import com.xinerde.demo.shortlink.service.ShortLinkService;

/**
 * 域名服务实现类
 * 
 * @since 2022年5月19日
 * @author guihong.zhang
 * @version 1.0
 */
public class ShortLinkServiceImpl implements ShortLinkService {
	private final static String DOMAIN_PATTERN = "^((http://)|(https://))?([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}(/)";
    private static EhCacheUtil ehCacheUtil = EhCacheUtil.getInstance();

    
	private AtomicLong idSeq = new AtomicLong();
	@AutoWired
	private BloomFilter<String> bloomFilter;

	@Override
	public String fetchShortLink(String longLink) {
		if(StringUtils.isEmpty(longLink) || !verify(longLink)){
            throw new IllegalArgumentException("域名参数不合法");
        }
        Long seq = idSeq.getAndIncrement();
        String shortLink = Long62Util.to62String(seq);
        ehCacheUtil.put(EhCacheUtil.MY_CACHE,shortLink,longLink);
        bloomFilter.put(shortLink);
        return shortLink;
	}

	@Override
	public String fetchLongLink(String shortLink) {
		if(StringUtils.isEmpty(shortLink) ){
            throw new IllegalArgumentException("域名参数不合法");
        }
		if(!bloomFilter.mightContain(shortLink)) {
			throw new RuntimeException("查询的链接不存在");
		}
		String longLink = (String) ehCacheUtil.get(EhCacheUtil.MY_CACHE, shortLink);
		if(StringUtils.isEmpty(shortLink)) {
			throw new RuntimeException("查询的链接不存在");
		}
		return longLink;
	}
	
	/**
	 * 检查长链接合法性
	 * @param longLink
	 * @return
	 */
   private boolean verify(String longLink){
       Pattern pattern = Pattern.compile(DOMAIN_PATTERN);
       Matcher matcher = pattern.matcher(longLink);
       return matcher.find();
   }

}
