package com.jk.shorturl.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 获取系统配置信息
 */
@AutoConfigurationPackage
@Configuration
@PropertySource(value = {"classpath:config/application-dev.properties"}, encoding = "UTF-8")
@Component
public class ConfigMainUtil {

    @Value("${shorturl.domain.url}")
    public String domain;

    /**
     * 从短域名中获取短码
     *
     * @param shortURL
     * @return
     */
    public static String getCodeFromURL(String shortURL) {
        //String shortURL = "http://t.cn/xyxy/?a=1";
        if (shortURL.endsWith("/"))
            shortURL = shortURL.substring(0, shortURL.length() - 1);
        if (shortURL.indexOf("/") > 0)
            shortURL = shortURL.substring(shortURL.lastIndexOf("/") + 1);
        //System.out.println(shortURL);
        return shortURL;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

}
