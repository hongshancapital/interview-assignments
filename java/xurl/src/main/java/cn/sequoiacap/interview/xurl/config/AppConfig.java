package cn.sequoiacap.interview.xurl.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "xurl")
public class AppConfig {
  private String shortDomain;
  private byte machineNo = 0;
}
