package com.demo.domain.cache;

import com.demo.domain.util.Constant;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author fanzj
 * @Date 2022/4/2 18:35
 * @Version 3.0
 * @Description HazelcastConfiguration
 */
@Configuration
public class HazelcastConfig {

    @Value("${hazelcast.group.name}")
    private String groupName;

    @Value("${hazelcast.listen.port}")
    private int port;

    @Value("${hazelcast.address}")
    private String hazelcastTcpIp = "";

    @Bean
    public HazelcastInstance hazelcastInstance() {
        NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPort(port);
        JoinConfig join = networkConfig.getJoin();
        // 没有配置hazelcastTcpIp
        join.getMulticastConfig().setEnabled(false);
        TcpIpConfig tcpIpConfig = join.getTcpIpConfig();
        tcpIpConfig.addMember(hazelcastTcpIp);
        tcpIpConfig.setEnabled(true);

        Map<String, MapConfig> mapConfigMap = new ConcurrentHashMap<>();
        MapConfig domainMap = new MapConfig(Constant.MAP_NAME);
        // 配置规则
        domainMap.setBackupCount(0)
                .setEvictionPolicy(EvictionPolicy.LRU)
                .setMaxSizeConfig(new MaxSizeConfig(Constant.MAX_CAPACITY, MaxSizeConfig.MaxSizePolicy.PER_NODE));
        // Constant.MAP_NAME
        mapConfigMap.put(Constant.MAP_NAME, domainMap);
        Config config = new Config();
        config.setInstanceName("domain").setGroupConfig(new GroupConfig(groupName))
                .setNetworkConfig(networkConfig)
                .setMapConfigs(mapConfigMap);
        return Hazelcast.newHazelcastInstance(config);
    }
}
