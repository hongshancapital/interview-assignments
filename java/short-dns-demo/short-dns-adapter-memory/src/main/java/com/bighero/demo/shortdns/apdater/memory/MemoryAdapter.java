package com.bighero.demo.shortdns.apdater.memory;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import com.bighero.demo.shortdns.domain.entity.DNRelEntity;
import com.bighero.demo.shortdns.domain.entity.LongDN;
import com.bighero.demo.shortdns.domain.entity.ShortDN;
import com.bighero.demo.shortdns.domain.exception.AdapterException;
import com.bighero.demo.shortdns.domain.repository.DNRelEntityRepo;
import com.bighero.demo.shortdns.domain.utils.MD5Utils;

import lombok.extern.slf4j.Slf4j;
/**
 * 内存适配层
 * @author bighero
 */
@Service
@Primary
@Slf4j
public class MemoryAdapter implements DNRelEntityRepo {
	public final static ConcurrentHashMap<String, DNRelEntity> map=new ConcurrentHashMap<String, DNRelEntity>(16);
	
	/**
	 * 存储
	 */
	@Override
	public void save(DNRelEntity entity) {
		map.putIfAbsent(entity.getShortDN().getPath(),entity);
		map.putIfAbsent(MD5Utils.encodeByMD5(entity.getLongDN().getUrl()),entity);
		log.info("内存保存信息"); 

	}
	
	/**
	 * 读取
	 */
	@Override
	public DNRelEntity get(ShortDN shortDN) {
		log.info("内存获取信息");
		return map.get(shortDN.getPath());
	}

	/**
	 * MD5读取
	 */
	@Override
	public DNRelEntity getByMD5(LongDN longDN) throws AdapterException {
		log.info("MD5内存获取信息");
		return map.get(MD5Utils.encodeByMD5(longDN.getUrl()));
	}

}
