package com.bruce.shorturl.service;

import com.bruce.shorturl.exception.GenericException;

/**
 * 短域名接口
 * @author bruce
 */
public interface IShortUrlService {

	/**
	 * 长链接转短链接
	 *
	 * @param fullUrl
	 * @return
	 */
	String generateShortUrl(String fullUrl);

	/**
	 *
	 * @param fullUrl
	 * @param salt
	 * @param triedTimes
	 * @return
	 */
	String generateShortUrl(String fullUrl, String salt, int triedTimes);

	/**
	 * 短链接转长链接
	 * @param hashText
	 * @return
	 * @throws GenericException
	 */
	String loadFullUrlByHash(String hashText) throws GenericException;


}
