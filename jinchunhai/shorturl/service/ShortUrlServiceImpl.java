package com.jinchunhai.shorturl.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jinchunhai.shorturl.mapper.ShortUrl;
import com.jinchunhai.shorturl.mapper.ShortUrlMapper;

/**
 * @Description 短域名服务实现类
 * @version 1.0
 * @author JinChunhai
 * @time 2021年03月19日
 */

@Service
public class ShortUrlServiceImpl implements ShortUrlService{
	
	private static Logger logger = LoggerFactory.getLogger(ShortUrlService.class.getName());
	
	private static final char FULL_CHAR [] = {'0','1','2','3','4','5','6','7','8','9'
			,'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'
			,'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'
			};
	
	private static final int FULL_CHAR_LENGTH = FULL_CHAR.length;
	
	private static final int SHORT_ID_LENGTH = 8;
	
	private static final String CHAR_SET = "utf-8";
	
	@Autowired
	private ShortUrlMapper shortUrlMapper;
		
	@Override
	public String createShortUrl(String longUrl) {
		
		String id = getShortId(SHORT_ID_LENGTH);
		ShortUrl shortUrl = new ShortUrl();
		shortUrl.setId(id);
		shortUrl.setCreateTime(new Date());
		shortUrl.setLongUrl(replaceByUrl(longUrl));
		try {
			shortUrlMapper.insertSelective(shortUrl);
		} catch (Exception e) {
			Document insertDoc = new Document();
			insertDoc.put("longUrl", longUrl);
			insertDoc.put("id", id);
			insertDoc.put("errMag", "失败：" + e.getMessage());
			logger.error(e.getMessage(), e, insertDoc);
			e.printStackTrace();
		}
		return "https://m.jinchunhai/s/" + id;
	}
	
	@Override
	public String getLongUrl(String id) {
		ShortUrl shortUrl = shortUrlMapper.selectByPrimaryKey(id);
		if (null == shortUrl) {
			throw new Exception("短域名无效");
		}
		// 替换链接中的汉字
		String url = replace(shortUrl.getLongUrl());
		return url;
	}
	
	/**
	 * 随机生成n位短id
	 * @return
	 */
	public static String getShortId(int n) {
		
		StringBuffer sBuffer = new StringBuffer();
		for(int i = 0; i < n; i++) {
			sBuffer.append(FULL_CHAR[(int)(Math.random()*(FULL_CHAR_LENGTH))]);
		}
		return sBuffer.toString();
	}
	
	public static String replaceByUrl(String longUrl) {
		
		try {
			URL url = new URL(longUrl);
			StringBuffer sBuffer = new StringBuffer();
			sBuffer.append(url.getProtocol());
			sBuffer.append("://");
			sBuffer.append(url.getHost());
			int port = url.getPort();
			if (port >= 0) {
				sBuffer.append(":" + port);
			}
			String path = url.getPath();
			
			String[] split = path.split("/");
			for (String str : split) {
				if (StringUtils.isNotBlank(str)) {
					sBuffer.append("/");
					sBuffer.append(urlDecodeAndEncoder(str));
				}
			}
			boolean firstPutQueryParams = true;
			String query = url.getQuery();
			if (StringUtils.isNotBlank(query)) {
				String[] split2 = query.split("&");
				if (split2 != null && split2.length > 0) {
					for (String str2 : split2) {
						if (StringUtils.isNotBlank(str2)) {
							String[] split3 = str2.split("=");
							if (split3 != null && split3.length > 0) {
								if (firstPutQueryParams) {
									sBuffer.append("?");
									firstPutQueryParams = false;
								}else {
									sBuffer.append("&");
								}
								sBuffer.append(urlDecodeAndEncoder(split3[0]));
								sBuffer.append("=");
								if (split3.length > 1) {
									sBuffer.append(urlDecodeAndEncoder(split3[1]));
								}
							}
						}
					}
				}
			}
			
			String ref = url.getRef();
			if (StringUtils.isNotBlank(ref)) {
				sBuffer.append("#");
				sBuffer.append(urlDecodeAndEncoder(ref));
			}
			
			return sBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return longUrl;
	}
	
	public static String replace(String longUrl) {
		if (StringUtils.isBlank(longUrl)) {
			return "";
		}
		String regex = "[\u4e00-\u9fa5\\s\u4dae\uE863\u3002\uff1f\uff01\uff0c\u3001\uff1b\uff1a\u201c\u201d\u2018\u2019\uff08\uff09\u300a\u300b\u3008\u3009\u3010\u3011\u300e\u300f\u300c\u300d\ufe43\ufe44\u3014\u3015\u2026\u2014\uff5e\ufe4f\uffe5\u2013\uFF0E]+";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(longUrl);
		while (matcher.find()) {
			String group = matcher.group(0);
			try {
				longUrl = longUrl.replaceFirst(group, URLEncoder.encode(group, "utf-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return longUrl;
	}
	
	public static String urlDecodeAndEncoder(String str) throws UnsupportedEncodingException {
		if (StringUtils.isBlank(str)) {
			return "";
		}
		return URLEncoder.encode(URLDecoder.decode(str, CHAR_SET), CHAR_SET);
	}
	
	// 短域名测试
	public static void main(String[] args) throws UnsupportedEncodingException, MalformedURLException {
		String id = getShortId(SHORT_ID_LENGTH);
		System.out.println(id);
		
		String str1 =  "https://planbook.kbao123.com/oss/insProLib/prod/product/PRO20180830000005/term/3. 华夏附加质子重离子医疗保险条款.pdf?Expires=2170495480&OSSAccessKeyId=LTAIEHf85LFXeegV&Signature=rPAeujZn6fGGHlY%2Fco9G4sIjtrA%3D&";
		String str2 =  "https://pm.uat.kbao123.com/app/?#/genHostingCodeNew?customerId=1805&customerName=%E5%AE%89%E5%A6%AE&from=wx&encryptedUserData=mjSk9xk8aoMeuvJ81ic9Qm9MhL6RZcONWn7ewub8WrRqemgqEyVf4N4SZMei51ScGavp3NgKIqmbZaS6i+vUX5Le0pEPgoXlROMpNTE+pQ2Di9lGzEcv0V805MnxF725LV3KHF3HAbB2np91gFiI2FGbpqPJ1nwiC1XbK3swkAWl+ROaF4mY+nB4t5yf2SED1BslxQZhECmwVzOlXuA1PC+3P8CdYhfhAzdd2QCm/53jlFJGxdxlG5p8+CKeP4kWwASSRPwxzPoAlxLiZ/3frvySxDFquM1V6WfQXSrKidxZ2P7G2OSWfyZXcYcsYR1tH633XubbOqxsLLk3gkwFaQ";
		String str3 =  ".．-";
		System.out.println(replace(str1));
		System.out.println(replaceByUrl(str2));
	}
}
