package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * Hello world!
 *
 */
@ServletComponentScan
@SpringBootApplication
@EnableConfigurationProperties
public class MainClass {

    public static void main( String[] args ) {
        SpringApplication.run(MainClass.class, args);
    }
}
