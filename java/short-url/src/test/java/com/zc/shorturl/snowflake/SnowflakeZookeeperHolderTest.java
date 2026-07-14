package com.zc.shorturl.snowflake;

import com.zc.shorturl.snowflake.config.SnowflakeIdGeneratorProperties;
import com.zc.shorturl.utils.IpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.annotation.Resource;
import java.io.File;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Slf4j
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = SnowflakeIdGeneratorProperties.class)
@TestPropertySource("classpath:application.properties")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SnowflakeZookeeperHolderTest {
    @Resource
    private SnowflakeIdGeneratorProperties snowflakeIdGeneratorProperties;

    @BeforeAll
    public void SetUp() {
        deleteDir(new File(snowflakeIdGeneratorProperties.getLocalNodeCacheDir()));
    }

    @Test
    public void testInitWithOutForeverPath() {
        final String ip = IpUtils.getIp();
        SnowflakeZookeeperHolder snowflakeZookeeperHolder = new SnowflakeZookeeperHolder(snowflakeIdGeneratorProperties, ip);
        String pathForever = snowflakeZookeeperHolder.getPathForever();
        // 测试没有根节点的情况 且没有本地缓存
        deleteDir(new File(snowflakeIdGeneratorProperties.getLocalNodeCacheDir()));
        CuratorFramework curator = curatorFramework(snowflakeIdGeneratorProperties.getZkConnectionString());
        try {
            curator.start();
            curator.delete().deletingChildrenIfNeeded().forPath(pathForever);
            curator.close();
        }catch (Exception e){
            log.error(e.getMessage());
        }

        snowflakeZookeeperHolder.init();
    }

    @Test
    public void testInit() {
        final String ip = IpUtils.getIp();
        SnowflakeZookeeperHolder snowflakeZookeeperHolder = new SnowflakeZookeeperHolder(snowflakeIdGeneratorProperties, ip);
        assertThat(snowflakeZookeeperHolder.init(), equalTo(true));

        // 1.无本地缓存，创建并初始化成功
        SnowflakeZookeeperHolder snowflakeZookeeperHolder1 = new SnowflakeZookeeperHolder(snowflakeIdGeneratorProperties, ip);
        deleteDir(new File(snowflakeIdGeneratorProperties.getLocalNodeCacheDir()));
        assertThat(snowflakeZookeeperHolder1.init(), equalTo(true));

        // 2.zk上有根节点但没有workerId节点
        SnowflakeZookeeperHolder snowflakeZookeeperHolder2 = new SnowflakeZookeeperHolder(snowflakeIdGeneratorProperties, ip);
        CuratorFramework curator = curatorFramework(snowflakeIdGeneratorProperties.getZkConnectionString());
        try {
            curator.start();
            curator.delete().deletingChildrenIfNeeded().forPath(snowflakeZookeeperHolder.getPathForever() + "/" + snowflakeZookeeperHolder.getWorkerNodeKey());
            curator.close();
        }catch (Exception e){
            log.error(e.getMessage());
        }
        snowflakeZookeeperHolder2.init();

        // 3.初始化失败，且读取本地缓存节点失败
        SnowflakeIdGeneratorProperties snowflakeIdGeneratorProperties1
                = new SnowflakeIdGeneratorProperties(1234,
                "testZookeeperInitFailed",
                "1.1.1.1:2181",
                "D:/tmp"
        );
        SnowflakeZookeeperHolder snowflakeZookeeperHolder3 = new SnowflakeZookeeperHolder(snowflakeIdGeneratorProperties1, ip);
        assertThat(snowflakeZookeeperHolder3.init(), equalTo(false));

        // 4.初始化失败，且读取本地缓存节点成功
        SnowflakeIdGeneratorProperties snowflakeIdGeneratorProperties2
                = new SnowflakeIdGeneratorProperties(snowflakeIdGeneratorProperties.getServicePort(),
                snowflakeIdGeneratorProperties.getServiceName(),
                "1.1.1.1:2181",
                snowflakeIdGeneratorProperties.getLocalNodeCacheDir()
        );
        SnowflakeZookeeperHolder snowflakeZookeeperHolder4 = new SnowflakeZookeeperHolder(snowflakeIdGeneratorProperties2, ip);
        assertThat(snowflakeZookeeperHolder4.init(), equalTo(true));
    }

    @Test
    public void testEndpoint() {
        SnowflakeZookeeperHolder.Endpoint endpoint = new SnowflakeZookeeperHolder.Endpoint();
        endpoint.setIp("1.1.1.1");
        log.info(endpoint.toString());
    }

    private CuratorFramework curatorFramework(String connectionString) {
        return CuratorFrameworkFactory.builder().connectString(connectionString)
                .retryPolicy(new RetryUntilElapsed(1000, 4))
                .connectionTimeoutMs(10000)
                .sessionTimeoutMs(6000)
                .build();
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     * @param dir 将要删除的文件目录
     * @return boolean Returns "true" if all deletions were successful.
     *                 If a deletion fails, the method stops attempting to
     *                 delete and returns "false".
     */
    private static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();

            //递归删除目录中的子目录下
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }
        // 目录此时为空，可以删除
        return dir.delete();
    }
}
