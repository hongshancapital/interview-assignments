package com.zhouhongbing.shorturl;

import com.zhouhongbing.shorturl.enm.LRUCacheInstance;
import com.zhouhongbing.shorturl.utils.LRUCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @version 1.0
 * @Author 海纳百川zhb
 **/
@SpringBootApplication
public class MyShortUrlApp {
    public static void main(String[] args) {
        SpringApplication.run(MyShortUrlApp.class, args);
    }

    @Bean
    public LRUCache getLRUCache() {
        return LRUCacheInstance.LRU_CACHE_INSTANCE.LRUCacheInstance();
    }
}
