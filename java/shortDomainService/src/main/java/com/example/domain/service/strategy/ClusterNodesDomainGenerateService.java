package com.example.domain.service.strategy;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @title: ClusterNodesDomainGenerateService
 * @Author DH
 * @Date: 2021/12/6 16:19
 * 集群节点获取下一个阶段的序列id
 */
@Component
public class ClusterNodesDomainGenerateService implements DomainGenerateService<List<Long>>{
    @Override
    public List<Long> getNextShortDomainId() {
        return null;
    }
}
