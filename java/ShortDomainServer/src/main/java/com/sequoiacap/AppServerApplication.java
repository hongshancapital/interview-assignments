package com.sequoiacap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;

import com.sequoiacap.annotation.Generated;

import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 
 * @author zoubin
 */
@EnableOpenApi
@SpringBootApplication
@Generated
public class AppServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AppServerApplication.class, args);
	}
	
	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer() {
		return new HighPerformanceWebServerFactoryCustomizer();
	}
}