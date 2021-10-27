package com.bighero.demo.shortdns.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.bighero.demo.shortdns.domain.assmbler.IAssembly;
import com.bighero.demo.shortdns.domain.entity.DNRelEntity;
import com.bighero.demo.shortdns.domain.exception.BusinessException;
import com.bighero.demo.shortdns.domain.repository.DNRelEntityRepo;
/**
 * 长短域名核心业务服务
 * @author bighero
 */
@Service
public class DNService {
	@Autowired
	@Qualifier("byMD5")
	ITransformShortDN transformShortDN ;
	
	@Autowired
	DNRelEntityRepo repo;
	
	/**
	 * 存储域名实体提对象
	 * @param ass 装配对象
	 * @return 域名实体对象
	 */
	public DNRelEntity save(IAssembly<DNRelEntity> ass) {
		DNRelEntity entity=new DNRelEntity();
		entity=ass.toAssembly(entity);
		if(!(entity.getLongDN().verify())) {
			throw new BusinessException("demo00003", "长域名格式验证失败！");
		}
		DNRelEntity obj=repo.getByMD5(entity.getLongDN());
		if(obj !=null) {
			return obj;
		}
		entity.setShortDN(transformShortDN.transform(entity.getLongDN()));
		repo.save(entity);
		return entity;
	}
	
	/**
	 * 读取域名实体对象
	 * @param ass  装配对象
	 * @return 域名实体对象
	 */
	public DNRelEntity get(IAssembly<DNRelEntity> ass) {
		DNRelEntity entity=new DNRelEntity();
		entity=ass.toAssembly(entity);
		if(!(entity.getShortDN().verify())) {
			throw new BusinessException("demo00004", "短域名路径验证失败！请检查是否在8个字符以内！");
		}
		entity=repo.get(entity.getShortDN());
		if(entity==null) {
			throw new BusinessException("demo00005", "无长域名信息！");
		}
		return entity;
	}
	
}
