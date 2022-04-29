package com.wf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


import lombok.Data;


/**
 * SpringBoot应用启动类
 */
//@SpringBootApplication()
@Data
@EnableSwagger2
//@EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})
@ComponentScan(basePackages = {"com.wf.controller"})
@SpringBootApplication(scanBasePackages="com.wf.controller",exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ShortUrlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortUrlApplication.class, args);
    }

}
