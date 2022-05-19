package com.xinerde.demo.shortlink.service;

import com.xinerde.demo.shortlink.bo.LinkBO;

/**
 * 短域名服务接口
 * 
 * @since 2022年5月19日
 * @author guihong.zhang
 * @version 1.0
 */
public interface ShortLinkService {
	/**
	 * 根据长链接获取短链接
	 * @param longLink 长链接
	 * @return 短链接
	 */
	String fetchShortLink(String longLink);
	
	/**
	 * 根据短链接获取长链接
	 * @param shortLink 短链接
	 * @return 长链接
	 */
	String fetchLongLink(String shortLink);
}
