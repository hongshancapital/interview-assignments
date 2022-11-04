package com.scdtchina.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author hai.yuan
 * 系统配置类
 */
@Configuration
@ConfigurationProperties(prefix = "system")
public class SystemConfig {

    /**
     * 系统最大容量
     */
    private Long maxKey = 218340105584895L;

    /**
     * 生成的短域名最大长度
     */
    private int codeLength = 8;


    public Long getMaxKey() {
        return maxKey;
    }

    public void setMaxKey(Long maxKey) {
        this.maxKey = maxKey;
    }

    public int getCodeLength() {
        return codeLength;
    }

    public void setCodeLength(int codeLength) {
        this.codeLength = codeLength;
    }


}
