package com.bighero.demo.shortdns.domain.service;

import com.bighero.demo.shortdns.domain.entity.LongDN;
import com.bighero.demo.shortdns.domain.entity.ShortDN;

/**
 * 短域名计算策略接口
 * @author bighero
 */
public interface ITransformShortDN {
	public ShortDN transform(LongDN longDN);
}
