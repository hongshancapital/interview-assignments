package com.zc.shorturl.snowflake.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;


/**
 * SnowflakeIdGenerator配置属性
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "snowflake", ignoreInvalidFields = true)
public class SnowflakeIdGeneratorProperties {
    // servicePort
    @Value("${server.port}")
    private Integer servicePort;

    // 服务名，默认为 {spring.application.name}
    @Value("${spring.application.name}")
    private String serviceName;

    // zookeeper address
    @Setter
    private String zkConnectionString;

    // 本地节点缓存的目录
    @Setter
    private String localNodeCacheDir;
}

