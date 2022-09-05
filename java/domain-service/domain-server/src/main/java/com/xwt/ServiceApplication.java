package com.xwt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;


/**
 * 启动类
 *
 * @author xiwentao
 * @date: 2021-07-25
 */
@SpringBootApplication(exclude = {
        ErrorMvcAutoConfiguration.class
})
public class ServiceApplication {

    public static void main(String[] args) {

        SpringApplication springApplication = new SpringApplication(ServiceApplication.class);
        springApplication.run(args);
    }

}
