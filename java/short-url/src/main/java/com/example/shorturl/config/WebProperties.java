package com.example.shorturl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "web")
public class WebProperties {

    private String domain;

    private String slash;

    private String base;

    private String notFoundURL;

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getSlash() {
        return slash;
    }

    public void setSlash(String slash) {
        this.slash = slash;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getNotFoundURL() {
        return notFoundURL;
    }

    public void setNotFoundURL(String notFoundURL) {
        this.notFoundURL = notFoundURL;
    }
}
