package com.sunjinghao.shorturl.service.cache.load;

import cn.hutool.core.util.ObjectUtil;
import com.github.benmanes.caffeine.cache.Cache;
import com.sunjinghao.shorturl.api.dto.DomainMappingInfoDto;
import com.sunjinghao.shorturl.service.cache.IShortCodeCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * DB Service 缓存操作实现
 * 微服务场景 ：可封装其它缓存数据聚合逻辑：例如订单头+订单体
 *
 * @author sunjinghao
 */
@Service
@Slf4j
public class ShortCodeCache implements IShortCodeCache {

    /**
     * 注入caffeineCache1缓存对象 : url为key,code 为key  O(1)查询
     * <p>
     * 也可以通过 切面的方式集成 spring boot cache
     */
    @Autowired
    private Cache<String, Object> caffeineCache;


    /**
     * 映射关系存储
     *
     * @param domainMappingInfoDto
     * @return
     */
    @Override
    public DomainMappingInfoDto add(DomainMappingInfoDto domainMappingInfoDto) {

        DomainMappingInfoDto ifPresent = getByLongUrl(domainMappingInfoDto);
        if (ifPresent == null) {
            caffeineCache.put(domainMappingInfoDto.getShortCode(), domainMappingInfoDto);
            caffeineCache.put(domainMappingInfoDto.getUrl(), domainMappingInfoDto);
            ifPresent = getByCode(domainMappingInfoDto);
        }
        return ifPresent;
    }

    /**
     * 查询数据
     *
     * @param domainMappingInfoDto
     * @return
     */
    @Override
    public DomainMappingInfoDto getByLongUrl(DomainMappingInfoDto domainMappingInfoDto) {
        Object ifPresent = caffeineCache.getIfPresent(domainMappingInfoDto.getUrl());
        return ObjectUtil.isNull(ifPresent) ? null : (DomainMappingInfoDto) ifPresent;
    }

    /**
     * 查询数据
     *
     * @param domainMappingInfoDto
     * @return
     */
    @Override
    public DomainMappingInfoDto getByCode(DomainMappingInfoDto domainMappingInfoDto) {
        Object ifPresent = caffeineCache.getIfPresent(domainMappingInfoDto.getShortCode());
        return ObjectUtil.isNull(ifPresent) ? null : (DomainMappingInfoDto) ifPresent;
    }

    /**
     * 删除缓存
     * db与缓存一致性通常 先删缓存再重新load数据
     *
     * @param domainMappingInfoDto
     * @return
     */
    @Override
    public void delete(DomainMappingInfoDto domainMappingInfoDto) {
        caffeineCache.invalidate(domainMappingInfoDto.getShortCode());
        caffeineCache.invalidate(domainMappingInfoDto.getUrl());
    }


}
