package com.scdt.interview.leon;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@SpringBootApplication
@Slf4j
@EnableCaching
public class HomeWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomeWebApplication.class, args);
	}

	@Component
	@Order(9999)
	public static class ApplicationSrartup implements ApplicationRunner {
		@Override
		public void run(ApplicationArguments args) {
			log.info("========== 应用启动成功 ==========");
		}
	}
}
