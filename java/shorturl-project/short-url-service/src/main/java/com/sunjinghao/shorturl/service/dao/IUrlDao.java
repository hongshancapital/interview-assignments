package com.sunjinghao.shorturl.service.dao;

import com.sunjinghao.shorturl.common.result.RpcResult;

/**
 * 链接存贮接口
 * <p>
 * 方法返回封装 RPC 或 Http 套结泛型，入参尽量用实体类 减少接口协议变动
 *
 * @author sunjinghao
 */
public interface IUrlDao<DomainMappingInfoDto> {

    /**
     * 保存链接信息
     *
     * @param domainMappingInfoDto
     * @return
     */
    public RpcResult<DomainMappingInfoDto> save(DomainMappingInfoDto domainMappingInfoDto);


    /**
     * 短链查长链
     *
     * @param domainMappingInfoDto
     * @return
     */

    public RpcResult<DomainMappingInfoDto>  getByCode(com.sunjinghao.shorturl.api.dto.DomainMappingInfoDto domainMappingInfoDto);

    /**
     * 长链查短链
     *
     * @param domainMappingInfoDto
     * @return
     */

    public RpcResult<DomainMappingInfoDto>  getByUrl(com.sunjinghao.shorturl.api.dto.DomainMappingInfoDto domainMappingInfoDto);
}
