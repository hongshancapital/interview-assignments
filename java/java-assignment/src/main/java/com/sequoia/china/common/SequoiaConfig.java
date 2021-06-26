package com.sequoia.china.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Description 自定义配置
 * @Author helichao
 * @Date 2021/6/26 10:31 上午
 */
@Configuration
@ConfigurationProperties(prefix = "sequoia")
public class SequoiaConfig {

    /**
     * id（短域名）递归最大次数，防止栈溢出
     */
    private int idRecursiveMaxCount;

    public int getIdRecursiveMaxCount() {
        return idRecursiveMaxCount;
    }

    public void setIdRecursiveMaxCount(int idRecursiveMaxCount) {
        this.idRecursiveMaxCount = idRecursiveMaxCount;
    }
}
