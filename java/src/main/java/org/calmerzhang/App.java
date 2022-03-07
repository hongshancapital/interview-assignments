package org.calmerzhang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 */
@SpringBootApplication(scanBasePackages = {"org.calmerzhang"})
public class App {
    public static void main( String[] args ) {
        SpringApplication.run(App.class, args);
    }
}
