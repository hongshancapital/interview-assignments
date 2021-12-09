package com.example.domain.conf;

import com.example.domain.service.entity.Domain;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @title: DomainStorageConf
 * @Author DH
 * @Date: 2021/12/6 16:37
 */
@Configuration
public class DomainStorageConf {


    public static volatile Map<String, Domain> domainMap = new LinkedHashMap<String, Domain>(10000,0.75F,true);

    /**
     * 域名存储服务的map key shortDomain字符串 val domain实体
     * @return
     */
    @Bean
    public Map domainMap(){
        return domainMap;
    }
}
