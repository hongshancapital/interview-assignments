package com.scdt.shorturl.config;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="snowflake")
public class SnowflakeConfig{
	@Value("${serviceIdBits:8}")
	private Long serviceIdBits;//业务线标识id所占的位数
	public Long getServiceIdBits(){return serviceIdBits;}
	public void setServiceIdBits(Long serviceIdBits){this.serviceIdBits=serviceIdBits;}
	
	@Value("${serviceId:0}")
	private Long serviceId;
	public Long getServiceId(){return serviceId;}
	//public void setServiceId(Long serviceId){this.serviceId=serviceId;}
	
	@Value("${workerIdBits:10}")
	private Long workerIdBits;//机器id所占的位数
	public Long getWorkerIdBits(){return workerIdBits;}
	public void setWorkerIdBits(Long workerIdBits){this.workerIdBits=workerIdBits;}
	
	@Value("${workerId:0}")
	private Long workerId;
	//public Long getWorkerId(){return workerId;}
	public void setWorkerId(Long workerId){this.workerId=workerId;}
	
	@Value("${sequenceBits:7}")
	private Long sequenceBits;//序列在id中占的位数
	public Long getSequenceBits(){return sequenceBits;}
	public void setSequenceBits(Long sequenceBits){this.sequenceBits=sequenceBits;}
	
	@Value("${twepoch:2021}")
	private Long twepoch;//开始时间戳
	public Long getTwepoch(){return twepoch;}
	public void setTwepoch(Long twepoch){this.twepoch=twepoch;}
}