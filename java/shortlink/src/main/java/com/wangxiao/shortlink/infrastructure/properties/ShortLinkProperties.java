package com.wangxiao.shortlink.infrastructure.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("shortlink")
@Getter
@Setter
public class ShortLinkProperties {
    private Long storeLimit;
    private String machineId;
}
