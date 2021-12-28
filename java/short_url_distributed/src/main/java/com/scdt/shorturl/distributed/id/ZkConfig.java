package com.scdt.shorturl.distributed.id;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * zookeeper 服务连接
 */
@Data
@Configuration
@ConfigurationProperties(prefix="spring.cloud.zookeeper")
public class ZkConfig {

    String connectString;
    String sequencePath;

    @Bean
    public ZookeeperService zookeeperClient(){
        return new ZookeeperService(this.connectString,this.sequencePath);
    }

}