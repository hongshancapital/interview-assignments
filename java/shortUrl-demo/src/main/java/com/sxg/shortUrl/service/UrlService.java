package com.sxg.shortUrl.service;

import org.springframework.stereotype.Service;
import com.sxg.shortUrl.mapper.UrlMapper;
import com.sxg.shortUrl.model.UrlModel;
import com.sxg.shortUrl.utils.RedisUtil;
import com.sxg.shortUrl.utils.SnowflakeIdWorker;
import com.sxg.shortUrl.utils.UrlUtil;

import io.netty.util.internal.StringUtil;

import java.util.Date;

/**
 * 
 * @author sxg
 *
 */
@Service
public class UrlService {

	private final UrlMapper mapper;
	private final RedisUtil redisUtil;

	public UrlService(UrlMapper mapper, RedisUtil redisUtil) {
		this.mapper = mapper;
		this.redisUtil = redisUtil;
	}

	public UrlModel createShortUrl(String longUrl) {

		SnowflakeIdWorker idWorker = new SnowflakeIdWorker(0, 0);
		long id = idWorker.nextId();
		UrlModel urlModel = new UrlModel();
		// 去Redis中抢占位置  保证原子性
		Boolean lock = redisUtil.setNx(redisUtil.lock + id, String.valueOf(id));
		// 抢到锁了 执行业务
		if (lock) {
			urlModel.setCreateDate(new Date());
			urlModel.setId(id);
			urlModel.setLongUrl(longUrl);
			urlModel.setShortUrl(UrlUtil.convertToStr(id));
			this.mapper.insertUrl(urlModel);
			setCache(urlModel);
			//删除锁
			redisUtil.delete(redisUtil.lock);
		} else {
			// 自旋获取锁
			// 休眠100ms
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return createShortUrl(longUrl);
		}

		return urlModel;
	}

	public UrlModel getUrl(String s) {
		UrlModel urlModel = this.mapper.getUrlByUri(s);
		setCache(urlModel);
		return urlModel;
	}

	private void setCache(UrlModel urlModel) {
		redisUtil.set(RedisUtil.urlLong + urlModel.getLongUrl(), urlModel.getShortUrl());
		redisUtil.set(RedisUtil.urlShort + urlModel.getShortUrl(), urlModel.getLongUrl());
	}
}
