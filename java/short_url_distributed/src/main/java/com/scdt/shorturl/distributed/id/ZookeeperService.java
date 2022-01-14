package com.scdt.shorturl.distributed.id;

import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;

@Data
public class ZookeeperService {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperService.class);
    private String connectString;
    private String sequencePath;

    // 重试休眠时间
    private final int SLEEP_TIME_MS = 1000;
    // 最大重试1000次
    private final int MAX_RETRIES = 1000;
    //会话超时时间
    private final int SESSION_TIMEOUT = 30 * 1000;
    //连接超时时间
    private final int CONNECTION_TIMEOUT = 3 * 1000;

    //创建连接实例
    private CuratorFramework client = null;

    public ZookeeperService(String connectString, String sequencePath) {
        this.connectString = connectString;
        this.sequencePath = sequencePath;
    }

    @PostConstruct
    public void init() throws Exception {
        this.client = CuratorFrameworkFactory.builder()
                .connectString(this.getConnectString())
                .connectionTimeoutMs(CONNECTION_TIMEOUT)
                .sessionTimeoutMs(SESSION_TIMEOUT)
                .retryPolicy(new ExponentialBackoffRetry(SLEEP_TIME_MS, MAX_RETRIES)).build();
        this.client.start();
    }

    /**
     * 生成SEQ
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Long sequence(String name) {
        try {
            String path = this.sequencePath + name;
            ZkSequence seq = new ZkSequence(path, this.client);
            if (seq != null) {
                return seq.sequence();
            }
        } catch (Exception e) {
            logger.error("获取[{}]Sequence错误:{}", name, e);
        }
        return null;
    }
}