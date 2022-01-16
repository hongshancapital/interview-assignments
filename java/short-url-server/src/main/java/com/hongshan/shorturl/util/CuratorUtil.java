package com.hongshan.shorturl.util;

import lombok.Setter;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Objects;

/**
 * @author: huachengqiang
 * @date: 2022/1/16
 * @description:
 */
@Setter
@Component
@ConfigurationProperties("zookeeper")
public class CuratorUtil {
    private static CuratorFramework client;
    private String connections;
    private String path;
    private int retryCount;
    private int baseSleepTimeMs;
    private int sessionTimeout;

    /**
     * 初始化客户端
     */
    @PostConstruct
    public void init() {
        client = CuratorFrameworkFactory.builder()
                .connectString(connections)
                .retryPolicy(new ExponentialBackoffRetry(baseSleepTimeMs, retryCount))
                .sessionTimeoutMs(sessionTimeout)
                .namespace(path)
                .build();
        client.start();
    }

    /**
     * @param
     * @Description: 关闭连接
     * @Date: 2020-08-22 15:15
     */
    public void closeConnection() {
        if (null != client) {
            client.close();
        }
    }

    /**
     * 创建临时节点
     *
     * @param path
     * @return
     * @throws Exception
     */
    public String createNode(String path) throws Exception {
        return client.create()
                .creatingParentsIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath(path);
    }

    /**
     * 判断节点是否存在
     *
     * @param path
     * @return
     * @throws Exception
     */
    public boolean exists(String path) throws Exception {
        Stat stat = client.checkExists().forPath(path);
        return Objects.nonNull(stat);
    }
}