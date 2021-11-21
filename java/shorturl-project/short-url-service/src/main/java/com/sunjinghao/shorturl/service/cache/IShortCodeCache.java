package com.sunjinghao.shorturl.service.cache;

import com.sunjinghao.shorturl.api.dto.DomainMappingInfoDto;

/**
 * DB Service 缓存操作接口
 *
 * @author sunjinghao
 */
public interface IShortCodeCache {

    /**
     * 映射关系存储
     *
     * @param domainMappingInfoDto
     * @return
     */

    DomainMappingInfoDto add(DomainMappingInfoDto domainMappingInfoDto);

    /**
     * 查询数据
     *
     * @param domainMappingInfoDto
     * @return
     */
    DomainMappingInfoDto getByLongUrl(DomainMappingInfoDto domainMappingInfoDto);


    /**
     * 查询数据
     *
     * @param domainMappingInfoDto
     * @return
     */
    DomainMappingInfoDto getByCode(DomainMappingInfoDto domainMappingInfoDto);


    /**
     * 删除缓存
     * db与缓存一致性通常 先删缓存再重新load数据
     *
     * @param domainMappingInfoDto
     * @return
     */
    public void delete(DomainMappingInfoDto domainMappingInfoDto);

}
