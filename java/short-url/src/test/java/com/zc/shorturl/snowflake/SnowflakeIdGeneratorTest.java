package com.zc.shorturl.snowflake;

import com.zc.shorturl.snowflake.common.IdStatus;
import com.zc.shorturl.snowflake.config.SnowflakeIdGeneratorProperties;
import com.zc.shorturl.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = SnowflakeIdGeneratorProperties.class)
@TestPropertySource("classpath:application.properties")
public class SnowflakeIdGeneratorTest {
    @Resource
    private SnowflakeIdGeneratorProperties snowflakeIdGeneratorProperties;

    @Test
    public void testSnowflakeIdGeneratorWithIllegalStateException() {
        // testZookeeperInitFailed
        SnowflakeIdGeneratorProperties snowflakeIdGeneratorProperties1
                = new SnowflakeIdGeneratorProperties(1111,
                "testZookeeperInitFailed",
                "1.1.1.1:2181",
                "D:/short-url/tmp"
                );

        assertThrows(IllegalStateException.class, () -> {
            new SnowflakeIdGenerator(snowflakeIdGeneratorProperties1);
        }, "Zookeeper initialized failed!");

        // 测试初始化zk失败，读取本地缓存文件，读取的workId非法
        final String ip = IpUtils.getIp();
        SnowflakeZookeeperHolder snowflakeZookeeperHolder = new SnowflakeZookeeperHolder(snowflakeIdGeneratorProperties, ip);
        snowflakeZookeeperHolder.updateLocalCacheWorkerId(-1);
        SnowflakeIdGeneratorProperties snowflakeIdGeneratorProperties2
                = new SnowflakeIdGeneratorProperties(snowflakeIdGeneratorProperties.getServicePort(),
                snowflakeIdGeneratorProperties.getServiceName(),
                "1.1.1.1:2181",
                snowflakeIdGeneratorProperties.getLocalNodeCacheDir()
        );

        Throwable exception= assertThrows(IllegalStateException.class, () -> {
            new SnowflakeIdGenerator(snowflakeIdGeneratorProperties2);
        });
        assertThat(exception.getMessage(), startsWith("Zookeeper initialized successfully , but work id"));

        // 测试workId > MAX_WORKER_ID 分支
        snowflakeZookeeperHolder.updateLocalCacheWorkerId((int)SnowflakeIdGenerator.getMaxWorkerId() + 1);
        Throwable exception1 = assertThrows(IllegalStateException.class, () -> {
            new SnowflakeIdGenerator(snowflakeIdGeneratorProperties2);
        });
        assertThat(exception1.getMessage(), startsWith("Zookeeper initialized successfully , but work id"));
    }

    @Test
    public void testNextId() {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(snowflakeIdGeneratorProperties);
        assertThat(snowflakeIdGenerator.nextId().getIdStatus(), equalTo(IdStatus.SUCCESS));

        snowflakeIdGenerator.setLastTimestamp(System.currentTimeMillis() + 1000);
        assertThat(snowflakeIdGenerator.nextId().getIdStatus(), equalTo(IdStatus.EXCEPTION));

        // 测试 相同时间戳产生序列号 以及 在可接受回拨范围继续等待产生id
        // 较难估计时间误差及线程调度，多次测试，保证大概率测试成功
        for (int i = 0; i < 3; i++){
            snowflakeIdGenerator.setLastTimestamp(System.currentTimeMillis());
            snowflakeIdGenerator.setSequence(snowflakeIdGenerator.getMaxSequence());
            assertThat(snowflakeIdGenerator.nextId().getIdStatus(), equalTo(IdStatus.SUCCESS));

            snowflakeIdGenerator.setLastTimestamp(System.currentTimeMillis());
            snowflakeIdGenerator.setSequence(snowflakeIdGenerator.getMaxSequence() - 1);
            assertThat(snowflakeIdGenerator.nextId().getIdStatus(), equalTo(IdStatus.SUCCESS));

            snowflakeIdGenerator.setLastTimestamp(System.currentTimeMillis() + 1);
            assertThat(snowflakeIdGenerator.nextId().getIdStatus(), equalTo(IdStatus.SUCCESS));
        }
    }
}
