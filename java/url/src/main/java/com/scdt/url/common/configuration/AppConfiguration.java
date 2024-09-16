package com.scdt.url.common.configuration;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 应用配置类
 * @author easten
 * @date 2021/12/09
 */
@Configuration
@Getter
public class AppConfiguration {
    /**
     * 短链接长度
     */
    @Value("${config.tinyurl-length}")
    private Integer tinyUrlLength;

    /**
     * 短链接存储空间大小
     */
    @Value("${config.tinyurl-storage-capacity}")
    private Integer tinyUrlStorageCapacity;
}
