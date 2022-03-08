package com.zxp.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import lombok.extern.slf4j.Slf4j;
import java.net.InetAddress;
import java.net.UnknownHostException;
@Slf4j
@SpringBootApplication
@ServletComponentScan
public class MyDemoApplication {

    public static void main(String[] args)  throws UnknownHostException {
        ConfigurableApplicationContext context = SpringApplication.run(MyDemoApplication.class, args);
        ConfigurableEnvironment env = context.getEnvironment();

        log.info("\n----------------------------------------------------------\n" +
                        "    Application '{}' is running! Access URLs:\n" +
                        "    Swagger: \t\thttp://{}:{}/demo/api/doc.html\n" +
                        "----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                InetAddress.getLocalHost().getHostAddress(), env.getProperty("server.port"));
    }

}
