package com.bighero.demo.shortdns.domain.repository;

import com.bighero.demo.shortdns.domain.entity.DNRelEntity;
import com.bighero.demo.shortdns.domain.entity.LongDN;
import com.bighero.demo.shortdns.domain.entity.ShortDN;
import com.bighero.demo.shortdns.domain.exception.AdapterException;
/**
 * 存储服务接口
 * @author bighero
 */
public interface DNRelEntityRepo{
	/**
	 * 存储短域名服务
	 * @param entity
	 * @throws AdapterException
	 */
	public void save(DNRelEntity entity) throws AdapterException;
	/**
	 * 通过MD5读取域名实体
	 * @param longDN
	 * @return
	 * @throws AdapterException
	 */
	public DNRelEntity getByMD5(LongDN longDN) throws AdapterException;
	/**
	 * 通过短域名读取长域名
	 * @param shortDN
	 * @return
	 * @throws AdapterException
	 */
	public DNRelEntity get(ShortDN shortDN) throws AdapterException;
}
