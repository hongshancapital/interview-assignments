package com.zc.shorturl.snowflake;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zc.shorturl.snowflake.config.SnowflakeIdGeneratorProperties;
import com.zc.shorturl.snowflake.exception.CheckLastTimeException;
import io.swagger.models.auth.In;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryUntilElapsed;
import org.apache.curator.shaded.com.google.common.annotations.VisibleForTesting;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * snowflake id生成相关的zk操作，自动获取workId，解决时钟回拨
 */

@Slf4j
public class SnowflakeZookeeperHolder {
    //节点前缀
    private final String PREFIX_ZK_PATH;

    //持久数据的节点的路径
    private final String PATH_FOREVER;

    //本地缓存的workId文件
    private final String PROP_PATH;

    //ip + servicePort
    private final String listenAddress;

    //服务器某个网卡的ip
    private final String ip;

    //servicePort
    private final int servicePort;

    //zk address
    private final String zkConnectionString;

    //leaf zk持久化节点 : PATH_FOREVER/ip:servicePort-0000000000
    private String zk_AddressNode;

    //workId
    @Getter
    private int workerId;

    //最后一次上传数据的时间
    private long lastUpdateTime;

    //zk 客户端
    private final CuratorFramework curator;

    // 定时上报时间间隔
    private static final int UPLOAD_DATA_INTERVAL = 3;

    private static final String WORKER_ID_NAME = "workerId";

    /**
     * 构造函数，初始化客户端
     * @param snowflakeIdGeneratorProperties  配置属性
     * @param ip  当前服务器的某个网卡的ip
     */
    public SnowflakeZookeeperHolder(SnowflakeIdGeneratorProperties snowflakeIdGeneratorProperties, String ip) {
        this.zkConnectionString = snowflakeIdGeneratorProperties.getZkConnectionString();
        this.servicePort = snowflakeIdGeneratorProperties.getServicePort();
        this.ip = ip;
        this.listenAddress = ip + ":" + servicePort;
        this.curator = curatorFramework(zkConnectionString);
        this.PREFIX_ZK_PATH = "/leaf/snowflake/" + snowflakeIdGeneratorProperties.getServiceName();
        this.PATH_FOREVER = PREFIX_ZK_PATH + "/forever";
        this.PROP_PATH = snowflakeIdGeneratorProperties.getLocalNodeCacheDir() + File.separator + snowflakeIdGeneratorProperties.getServiceName()
                + "/leaf_conf/%d/workerId.properties";
    }

    /**
     * 初始化节点，获取workId，定时更新zk节点
     * @return
     */
    public boolean init() {
        try {
            curator.start();
            Stat stat = curator.checkExists().forPath(PATH_FOREVER);
            // 不存在根节点,机器第一次启动
            if (stat == null) {
                // 创建节点,并上传数据
                zk_AddressNode = createNode();
                //worker id 默认是0
                updateLocalWorkerId(workerId);
                // 定时上报本机时间给forever节点
                scheduledUploadData(zk_AddressNode);
                return true;
            } else {
                // 存在根节点，说明worker机器是重新启动的,先检查是否有属于自己的根节点
                String workerNodeKey = getWorkerNodeKeyFromZk();
                String[] nodeKeys = workerNodeKey.split("-");
                Integer workerIdFromZk = nodeKeys.length > 1 ? Integer.parseInt(nodeKeys[1]) : null;
                //使用的仍然是原来的节点
                if (workerIdFromZk != null) {
                    //有自己的节点,zk_AddressNode=ip:servicePort
                    zk_AddressNode = PATH_FOREVER + "/" + workerNodeKey;
                    //启动worker时使用会使用
                    workerId = workerIdFromZk;
                    if (!checkInitTimeStamp(zk_AddressNode)) {
                        throw new CheckLastTimeException("Init timestamp check error , current server time lt  forever node timestamp !");
                    }
                    // 准备创建临时节点
                    scheduledUploadData(zk_AddressNode);
                    updateLocalWorkerId(workerIdFromZk);
                    log.info("[Old NODE]find forever node have this endpoint ip-{} servicePort-{} worker id-{} child node and start SUCCESS !", ip, servicePort, workerIdFromZk);
                } else {
                    // 创建新的持久节点 ,不用check时间
                    String newNode = createNode();
                    zk_AddressNode = newNode;
                    String[] nodeKey = newNode.split("-");
                    workerId = Integer.parseInt(nodeKey[nodeKey.length - 1]);
                    scheduledUploadData(zk_AddressNode);
                    updateLocalWorkerId(workerId);
                    log.info("[New NODE]can not find node on forever node that endpoint ip-{} servicePort-{} worker id-{},create own node on forever node and start SUCCESS !", ip, servicePort, workerId);
                }
            }
        }
        catch (Exception e) {
            log.error("Capture an exception while initializing zookeeper node ",e);
            try {
                // 初始化zk节点失败后，使用本地缓存的节点
                Properties properties = new Properties();
                properties.load(new FileInputStream(new File(String.format(PROP_PATH,servicePort))));
                workerId = Integer.parseInt(properties.getProperty(WORKER_ID_NAME));
                log.warn("SnowflakeIdGenerator Zookeeper node initialization failed , use local cache node workerId - {}", workerId);
            } catch (IOException ioe) {
                log.error("Capture an exception while reading local cache file " , ioe);
                return false;
            }
        }
        return true;
    }

    /**
     * 获取zk上worker节点key
     * @return
     * @throws Exception
     */
    private String getWorkerNodeKeyFromZk() throws Exception {
        List<String> keys = curator.getChildren().forPath(PATH_FOREVER);
        for (String key : keys) {
            String[] nodeKey = key.split("-");
            if (nodeKey[0].equals(listenAddress)) {
                return key;
            }
        }
        return "";
    }

    /**
     * 定时向zk更新数据，写入最后更新时间等信息，为解决重启后时钟回拨提供信息
     * @param zk_AddressNode 所更新的节点在zk的路径
     */
    private void scheduledUploadData(final String zk_AddressNode) {
        // 每3s上报数据
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1,
                r -> {
                    Thread thread = new Thread(r, "snowflake-idgenerator-schedule-upload-time");
                    thread.setDaemon(true);
                    return thread;
                }, new ThreadPoolExecutor.CallerRunsPolicy());
        scheduledThreadPoolExecutor.setMaximumPoolSize(1);
        scheduledThreadPoolExecutor.scheduleWithFixedDelay(() -> updateNewData(zk_AddressNode), 1L, UPLOAD_DATA_INTERVAL, TimeUnit.SECONDS);
    }

    /**
     * 更新节点数据
     * @param path 所更新的节点路径
     */
    private void updateNewData(String path) {
        try {
            if (System.currentTimeMillis() >= lastUpdateTime) {
                curator.setData().forPath(path, buildData().getBytes(StandardCharsets.UTF_8));
                lastUpdateTime = System.currentTimeMillis();
            }
        } catch (Exception e) {
            log.error("Capture an exception while update node data . node path is {} , error is {}", path, e);
        }
    }

    /**
     * 在节点文件系统上缓存一个work id，当zk失效时，机器重启时保证能够正常启动
     * @param workerId
     */
    private void updateLocalWorkerId(int workerId) {
        File leafConfFile = new File(String.format(PROP_PATH,servicePort));
        //如果缓存文件已存在，则直接缓存
        if (leafConfFile.exists()) {
            try(OutputStream outputStream = openFileOutputStream(leafConfFile)) {
                outputStream.write((WORKER_ID_NAME + "=" + workerId).getBytes(StandardCharsets.UTF_8));
                log.info("Update local file cache workerId is : {}", workerId);
            }
            catch (IOException e) {
                log.error("Capture an exception while updating local file cache  ", e);
            }
        } else {
            // 如果文件不存在，先判断其父目录是否存在，再创建文件并缓存
            File parentFile = leafConfFile.getParentFile();
            boolean flag = parentFile.exists();
            if (!flag) {
                flag = parentFile.mkdirs();
            }
            if (flag) {
                try(OutputStream outputStream = openFileOutputStream(leafConfFile)) {
                    outputStream.write((WORKER_ID_NAME + "=" + workerId).getBytes(StandardCharsets.UTF_8));
                    log.info("Create local file cache workerId is : {}", workerId);
                }
                catch (IOException e) {
                    log.error("Capture an exception while updating local cache file ", e);
                }
            }
            else {
                log.error("An error occurred while creating local cache file parent dir !");
            }
        }
    }

    /**
     * 初始化时，如果节点已存在，说明是重新启动，就需要检查节点的时间戳
     * @param zk_AddressNode
     * @return 检测是否成功
     * @throws Exception
     */
    private boolean checkInitTimeStamp(String zk_AddressNode) throws Exception {
        byte[] bytes = curator.getData().forPath(zk_AddressNode);
        Endpoint endPoint = parseBuildData(new String(bytes));
        //服务器的时间不能小于最后一次上报的时间
        return endPoint.getTimestamp() < System.currentTimeMillis();
    }


    /**
     * 创建持久顺序节点 ,并把节点数据放入 value
     * @return                  创建后的节点
     * @throws Exception        exception
     */
    private String createNode() throws Exception {
        try {
            return curator.create().creatingParentsIfNeeded().
                    withMode(CreateMode.PERSISTENT_SEQUENTIAL).
                    forPath(PATH_FOREVER + "/" + listenAddress + "-", buildData().getBytes(StandardCharsets.UTF_8));
        }
        catch (Exception e) {
            log.error("Capture exception while creating zookeeper node ", e);
            throw e;
        }
    }

    /**
     * 构建需要上传的数据
     * @return json data of Endpoint
     */
    private String buildData() throws JsonProcessingException {
        Endpoint endpoint = new Endpoint(ip, servicePort, System.currentTimeMillis());
        return new ObjectMapper().writeValueAsString(endpoint);
    }

    /**
     * 解析zk节点的数据为Endpoint
     * @param json zk节点数据
     * @return Endpoint
     * @throws IOException
     */
    private Endpoint parseBuildData(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, Endpoint.class);
    }

    /**
     * 创建zk客户端
     * @param connectionString  zkConnectionString
     * @return zk客户端
     */
    private CuratorFramework curatorFramework(String connectionString) {
        return CuratorFrameworkFactory.builder().connectString(connectionString)
                .retryPolicy(new RetryUntilElapsed(1000, 4))
                .connectionTimeoutMs(10000)
                .sessionTimeoutMs(6000)
                .build();
    }

    /**
     * 创建指定文件的输入流
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    private FileOutputStream openFileOutputStream(File file) throws FileNotFoundException {
        return new FileOutputStream(file,false);
    }

    /**
     * 上报数据结构
     */
    @Setter
    @Getter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    static class Endpoint {
        private String ip;
        private int servicePort;
        private long timestamp;
    }

    @VisibleForTesting
    public String getPathForever() {
        return this.PATH_FOREVER;
    }

    @VisibleForTesting
    public String getWorkerNodeKey() throws Exception {
        return getWorkerNodeKeyFromZk();
    }

    @VisibleForTesting
    public void updateLocalCacheWorkerId(int workerId) {
        updateLocalWorkerId(workerId);
    }
}
