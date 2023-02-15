package com.scdt.china.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

/**
 * @author sujiale
 */
@SpringBootApplication(scanBasePackages = { "com.scdt.china.interview.controller" },
        exclude = { DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class InterviewJavaApplication {

    public static void main(String[] args) {
        SpringApplication.run(InterviewJavaApplication.class, args);
    }
}
