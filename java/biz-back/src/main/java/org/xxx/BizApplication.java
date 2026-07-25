package org.xxx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;


@ComponentScan(basePackages = "org.xxx")
@EnableTransactionManagement
@SpringBootApplication
@EnableAutoConfiguration
@EnableAsync 
public class BizApplication {

	public static void main(String[] args) {
		SpringApplication.run(BizApplication.class, args);		
	}
	
	@Autowired  
    private RestTemplateBuilder builder;  
	  
    // 使用RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例  
	@Bean  
	public RestTemplate restTemplate() {  
	    return builder.build();  
	}  
    	
}
