package com.interview.service;

/**
 * 
 * @类名称   com.interview.service.ShortUrlService.java
 * @类描述   <pre>短域名处理服务类</pre>
 * @作者     zhangsheng
 * @创建时间 2021年3月21日下午4:08:25
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本           修改人 		    修改日期 		           修改内容描述
 *     ------------------------------------------------------
 *     1.00 	 TODO 	2021年3月21日下午4:08:25             
 *     ------------------------------------------------------
 * </pre>
 */
public interface ShortUrlService {
	
	/**
	 * 
	 * @方法名称 storeShortUrl
	 * @功能描述 <pre>将长域名转换为短域名并存储</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午12:34:57
	 * @param longUrl:长域名
	 * @return 生成短域名
	 */
	public String storeShortUrl(String longUrl);
	
	/**
	 * 
	 * @方法名称 queryLongUrlByShortUrl
	 * @功能描述 <pre>根据输入的短域名转换为长域名，并返回</pre>
	 * @作者     zhangsheng
	 * @创建时间 2021年3月21日 下午12:36:53
	 * @param shortUrl:短域名
	 * @return 长域名
	 */
	public String queryLongUrlByShortUrl(String shortUrl);

}
