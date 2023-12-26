package com.eagle.urlTransformer.service;


/** 
 * @ClassName: UrlTransformService 
 * @Description: 长、短链接转换服务接口
 * @author Eagle
 * @date 2022年1月18日 下午6:29:49 
 *  
 */
public interface UrlTransformService {
	
	/** 
	 * @Title: saveLongUrl 
	 * @Description: 存储长链接至缓存并返回短链接
	 * @param @param longUrl 要存储的长链接
	 * @return String    返回相应的短链接 
	 * @throws 
	 */ 
	public String saveLongUrl(String longUrl);
	
	/** 
	 * @Title: getLongUrlByShortUrl 
	 * @Description: 根据短链接获取对应的长链接
	 * @param @param shortUrl  短链接
	 * @return String    返回短链接对应的长链接
	 * @throws 
	 */ 
	public String getLongUrlByShortUrl(String shortUrl);
}
