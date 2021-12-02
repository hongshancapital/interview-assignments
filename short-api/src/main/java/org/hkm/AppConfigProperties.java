package org.hkm;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
@Data
public class AppConfigProperties {

    private Integer length;
    private Long expireSec;
    private String domain;

}
