package com.sunjinghao.shorturl.gateway;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author sunjinghao
 */
@ComponentScan(value = {"com.sunjinghao.shorturl.*"})
@EnableCaching
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class ShortUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortUrlApplication.class, args);
	}

}
