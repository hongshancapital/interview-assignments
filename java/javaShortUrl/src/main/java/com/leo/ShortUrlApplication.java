package com.leo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.leo.store.MemoryDeamon;

import springfox.documentation.oas.annotations.EnableOpenApi;

/**
 * 短url项目启动入口
 * @author LeoZhang
 *
 */
@SpringBootApplication
@EnableOpenApi
public class ShortUrlApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortUrlApplication.class, args);
		
		//启动主cache守护进程
		Thread memoryDeamonThread = new Thread(new MemoryDeamon());
		memoryDeamonThread.setDaemon(true);
		memoryDeamonThread.start();
	}

}
