package com.sunjinghao.shorturl.gateway.domain;


import com.sunjinghao.shorturl.api.bo.DomainMappingInfoBo;

/**
 * 链接转换处理服务接口
 * <p>
 * /domain package 用于按领域聚合服务 套接层 （微服务聚合层，如调用多个rpc 或  http 服务）
 *
 * @author sunjinghao
 */
public interface IUrlTransferService {

    /**
     * 生成短链 code 与 长链接 做映射
     *
     * 若存在长链映射 覆盖，不存在则新生成
     *
     * @return DomainMappingInfoBo
     */
    DomainMappingInfoBo longUrlMappingShortCode(DomainMappingInfoBo domainMappingInfoBo);

    /**
     * 根据短链 code 获取 长链接
     *
     * 需要根据短链 拿到 长链映射的id,再查长链
     *
     * @return DomainMappingInfoBo
     */
    DomainMappingInfoBo getLongUrlByShortCode(DomainMappingInfoBo domainMappingInfoBo);


    /**
     * 根据长链接 获取  code
     *
     * 需要根据短链 拿到 长链映射的id,再查长链
     *
     * @return DomainMappingInfoBo
     */
//    DomainMappingInfoBo getShortCodeByUrl(DomainMappingInfoBo domainMappingInfoBo);
}
