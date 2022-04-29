package com.hszb.shorturl;

import com.hszb.shorturl.manager.storage.ShortUrlStorage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        //初始化存储
        ShortUrlStorage.getInstance();
    }

}
