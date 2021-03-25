package com.wjup.shorturl.config;


import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public Runnable createRunnable(){
        return ()->{
            System.out.println("This is a test Runnable bean...");
        };
    }

}
