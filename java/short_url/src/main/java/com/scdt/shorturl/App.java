package com.scdt.shorturl;

import com.scdt.shorturl.lrucache.LRUCache;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import com.scdt.shorturl.service.Hashids;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;


/**
 * 	红杉面试作业题，短域名服务
 * @author Tommy Zhang
 * @email zdp118@gmail.com
 */
@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
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
