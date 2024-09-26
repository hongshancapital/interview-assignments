package org.example.shorturl.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * id生成器配置
 *
 * @author bai
 * @date 2021/6/19 0:46
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "sequence")
public class IdGeneratorProperty {
    /**
     * 基础版生成器所需的WorkNode参数值。仅当advanceIdGenerator为false时生效。
     */
    private Integer snowflakeWorkNode = 1;
}
