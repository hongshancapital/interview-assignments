package com.bighero.demo.shortdns.domain.service;

import org.springframework.stereotype.Service;

import com.bighero.demo.shortdns.domain.entity.LongDN;
import com.bighero.demo.shortdns.domain.entity.ShortDN;
import com.bighero.demo.shortdns.domain.utils.MD5Utils;

import lombok.extern.slf4j.Slf4j;
/**
 * MD5域名计算策略
 * @author bighero
 */
@Service("byMD5")
@Slf4j
public class TransformShortDNbyMD5 implements ITransformShortDN {


	@Override
	public ShortDN transform(LongDN longDN) {
		log.info("transform by md5!");
		ShortDN shortDN = new ShortDN(MD5Utils.ShortText(longDN.getUrl()));
		return shortDN;
	}

}
