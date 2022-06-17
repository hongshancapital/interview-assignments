package com.mhy;

import com.mhy.constant.DomainConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication(scanBasePackages = "com.mhy")
@Slf4j
public class Main {

    public static void main(String[] args) {
        ConfigurableApplicationContext application = SpringApplication.run(Main.class, args);
        Environment env = application.getEnvironment();
        log.info("\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Doc: \t http://" + DomainConstant.DOMAIN_NAME + "/doc.html \n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"));
    }

}