package com.example.domain.service.strategy;


/**
 * id生成服务
 */
public interface DomainGenerateService<T> {

    /**
     * 获取短域名id 单节点是long类型id  多节点是List<Long>
     * @return T
     */
    T getNextShortDomainId();
}
