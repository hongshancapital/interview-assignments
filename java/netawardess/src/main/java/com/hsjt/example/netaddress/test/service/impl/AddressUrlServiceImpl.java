package com.hsjt.example.netaddress.test.service.impl;


import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.hsjt.example.netaddress.common.bean.UserKeyIdVO;
import com.hsjt.example.netaddress.common.bean.WebShotUrlVO;
import com.hsjt.example.netaddress.common.check.CheckUtils;
import com.hsjt.example.netaddress.common.utils.SysUtils;
import com.hsjt.example.netaddress.common.utils.TimeUtils;
import com.hsjt.example.netaddress.test.service.AddressUrlService;
import com.hsjt.example.netaddress.test.util.ShortUrlUtils;

import lombok.extern.slf4j.Slf4j;
/**
 * service 方法实现类
 * @author dazzling
 *
 */
@Slf4j
@Service
public class AddressUrlServiceImpl  implements AddressUrlService{
	
    /**
     * @param urlLong 原长网址
     * @return
     */
	 public String getShortUrl(String urlLong,String userId) {
		 UserKeyIdVO vo = null;
		 // 未有用户取默认用户
		 if(CheckUtils.isEmpty(userId)) {
			  vo = ShortUrlUtils.userMap.get(SysUtils.userId);
		 }else {
			  vo = ShortUrlUtils.userMap.get(userId);
			 if(CheckUtils.isEmpty(vo)) {
				 throw new RuntimeException("601");
			 }
		 }
		 String shortUrl = generateShortUrlVo(urlLong,vo);
	     return shortUrl;
	    }
	 
	 /**
	  * 获取8位短网址
	  * @param urlLong
	  * @return
	  */
	 private String generateShortUrlVo(String urlLong,UserKeyIdVO vo) {
		 String key = vo.getKeyId();
		 String uid = vo.getId()+":";
		 // 查询长网址有没有短网址
		 WebShotUrlVO longurl = ShortUrlUtils.urlMap.get(ShortUrlUtils.LONGURL+uid+urlLong);
		 if(CheckUtils.isNotEmpty(longurl)) {
			 return longurl.getShortUrl();
	 	  }
		 //生成的短网址
	     String shortUrls = ShortUrlUtils.shortUrl(urlLong,key);
	     // 当前短网址 有没有对应的 长网址
	     WebShotUrlVO longurl2 = ShortUrlUtils.urlMap.get(ShortUrlUtils.SHORTURL+uid+shortUrls);
	     // 当前短路径有长路径 并且长路径 传入的 长路径不相等
	     if(CheckUtils.isNotEmpty(longurl2) && !urlLong.equals(longurl2.getLongUrl())) {
	    	 // 同一用户 不同的2个url 生成短链接相同，取另一个key去生成 此时重复概率小
	    	  shortUrls = ShortUrlUtils.shortUrl(urlLong,vo.getKeyId2());
	    	  longurl2 = ShortUrlUtils.urlMap.get(ShortUrlUtils.SHORTURL+uid+shortUrls);
	    	  if(CheckUtils.isNotEmpty(longurl2) && !urlLong.equals(longurl2.getLongUrl())) {
	    		  throw new RuntimeException("700"); // "生成短网址失败" 生成的短网址重复 log.error("户生成的短网址重复，请更换用户key"); 
	    	  }
	     }
	    	// 存放数据
    	   longurl2 = new WebShotUrlVO(UUID.randomUUID().toString(),vo.getId(),vo.getKeyId(),urlLong,shortUrls);
    	   ShortUrlUtils.urlMap.put(ShortUrlUtils.LONGURL+uid+urlLong, longurl2);
    	   ShortUrlUtils.urlMap.put(ShortUrlUtils.SHORTURL+uid+shortUrls, longurl2);
	     return longurl2.getShortUrl();
	 }
	 
	 /**
	  * 为测代码覆盖率时间1秒执行
	  * 定时任务删除过期数据
	  */
	 @Scheduled(cron = "0/1 * * * * ?")
     public void scheduled(){
		 for(Map.Entry<String, WebShotUrlVO> entry : ShortUrlUtils.urlMap.entrySet()) { 
			 WebShotUrlVO wvo =  entry.getValue();
			 // 当前时间 
			 if(CheckUtils.isNotEmpty(wvo.getOverTime())) {
				 // 当前时间是否大于超时时间
				boolean isover = TimeUtils.isOver(LocalDateTime.now(), TimeUtils.getTimeByStr(wvo.getOverTime()));
				if(isover) {
					// 清除过期数据
				   String key = entry.getKey();
				   ShortUrlUtils.urlMap.remove(key);
				}
			 }
		}
		/*
		// 测代码覆盖率注释掉 内存不足时 删除数据
		int mb = 1024*1024;
		// 最大内存 
		long maxMemory = Runtime.getRuntime().maxMemory() / mb ;
		// 可使用内存
		long totalMemory = Runtime.getRuntime().totalMemory() / mb;
		// 剩余内存
		long freeMemory = Runtime.getRuntime().freeMemory() / mb;
		// 可用内存小于10 清楚 map
		if(freeMemory < 10) {
			ShortUrlUtils.urlMap.clear();
		}
		 */
		 
    }
 
}
