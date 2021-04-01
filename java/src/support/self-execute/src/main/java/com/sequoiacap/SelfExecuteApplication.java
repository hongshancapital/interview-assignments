package com.sequoiacap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({
	"/WEB-INF/config/spring-beans.xml",
	"/WEB-INF/config/spring-web.xml"
})
public class SelfExecuteApplication {

	public static void main(String[] args) {
		SpringApplication.run(SelfExecuteApplication.class, args);
	}

}
