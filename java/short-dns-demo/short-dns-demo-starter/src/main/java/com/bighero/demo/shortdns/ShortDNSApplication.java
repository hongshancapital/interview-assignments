package com.bighero.demo.shortdns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 短域名服务启动类
 * @author bighero
 */
@SpringBootApplication
@EnableSwagger2
public class ShortDNSApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortDNSApplication.class, args);
		
	}

}
