package io.github.cubesky.scdtjava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class ScdtJavaApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ScdtJavaApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder applicationBuilder){
		return applicationBuilder.sources(ScdtJavaApplication.class);
	}
}
