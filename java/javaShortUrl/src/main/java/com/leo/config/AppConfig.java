package com.leo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;



/**
 * 自定义的配置项
 * 注意：不要轻易执行set写操作
 * @author LeoZhang
 *
 */
//@Data
@Component
@ConfigurationProperties(prefix = "app-config")
public class AppConfig {

	/**
	 * 此系统接收的原始url最大长度，超出后不转换
	 */
	@Getter @Setter
	private int urlMaxLength;
}
