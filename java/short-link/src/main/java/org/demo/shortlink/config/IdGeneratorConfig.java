package org.demo.shortlink.config;

import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.YitIdHelper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author wsq
 * *@date 2022/3/26 10:12
 * *@description:
 */

@Configuration
@Data
@ConfigurationProperties(prefix = "generator")
@Slf4j
public class IdGeneratorConfig {
    private short workId;
    private byte workerIdBitLength;
    private byte seqBitLength;
    private Long baseTime;

    @PostConstruct
    public void init() {
        IdGeneratorOptions options = new IdGeneratorOptions(workId);
        if(workerIdBitLength > 0) {
            options.WorkerIdBitLength = workerIdBitLength;
        }
        if(baseTime == null) {
            options.BaseTime = System.currentTimeMillis();
        }
        YitIdHelper.setIdGenerator(options);
    }

}
