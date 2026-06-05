package com.meihua.shorturl.config;


import com.meihua.shorturl.cmdb.impl.IDGeneratorShortUrlHandler;

import com.meihua.shorturl.cmdb.impl.Md5GeneratorShortUrlHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author meihua
 * @version 1.0
 * @date 2021/10/12
 */
@Configuration
public class CommonConfig {

    @Bean(initMethod = "init",destroyMethod = "destroy" )
    public IDGeneratorShortUrlHandler idGeneratorShortUrl(){
        return  new IDGeneratorShortUrlHandler();
    }

    @Bean(initMethod = "init",destroyMethod = "destroy" )
    public Md5GeneratorShortUrlHandler md5GeneratorShortUrlHandler(){
        return  new Md5GeneratorShortUrlHandler();
    }
}
