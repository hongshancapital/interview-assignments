package pers.dongxiang.shorturl.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @ProjectName short-url
 * @Package pers.dongxiang.shorturl.config
 * @ClassName HostConfig
 * @Description host前缀配置
 * @Company lab
 * @Author dongxiang
 * @Date 10/31/2021 9:10 PM
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
@Configuration
@Data
public class HostConfig {

    @Value("${shortUrl.host}")
    private String host;

}
