package com.scdt.shorturl;

import com.scdt.shorturl.lrucache.LRUCache;
import com.scdt.shorturl.service.Hashids;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


/**
 * 	红杉面试作业题，短域名服务  分布式高可用集群版本(zookeeper实现CP)
 *
 * @author Tommy Zhang
 * @email zdp118@gmail.com
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class DistributedApp {
	public static void main(String[] args) {
		SpringApplication.run(DistributedApp.class, args);
		log.info("windows终端如果显示中文乱码，可以尝试命令 'chcp 65001' ，或以日志为准~");
	}

	/**
	 * 存储长域名和短域名映射关系，每次存储都是两条记录 长域名-短域名和短域名-长域名
	 * 基于ConcurrentHashMap的LRU缓存
	 * @param lruCacheSize 缓存大小
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public LRUCache<String,String> lruCache(@Value("${scdt.lru-cache-size}") int lruCacheSize){
		return new LRUCache<>(lruCacheSize);
	}

	/**
	 * 根据数字id生成唯一字符串id
	 * @param salt
	 * @param minLength 短域名始终是8位字符
	 * @return
	 */
	@Bean
	@ConditionalOnMissingBean
	public Hashids hashids(@Value("${scdt.hashids-salt}") String salt,@Value("${scdt.short-url-id-max-byte-length}") int minLength){
		return new Hashids(salt,minLength);
	}

	/**
	 * 供缓存同步用
	 * @return
	 */
	@Bean
	RestTemplate restTemplate() {return new RestTemplate();}

	/**
	 * swagger 服务描述
	 * @param appVersion project.version
	 * @return
	 */
	@Bean
	public OpenAPI customOpenAPI(@Value("${springdoc.version}") String appVersion) {
		return new OpenAPI()
				.info(new Info().title("短域名服务 API").version(appVersion));
	}

	/**
	 * swagger api文档
	 * @return
	 */
	@Bean
	public GroupedOpenApi shortUrlOpenApi() {
		String[] paths = { "/short-urls/api/v1/**" };
		return GroupedOpenApi.builder().group("short-urls").pathsToMatch(paths)
				.build();
	}


}
