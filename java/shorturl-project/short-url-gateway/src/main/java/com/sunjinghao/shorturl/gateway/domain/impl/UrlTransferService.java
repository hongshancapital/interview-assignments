package com.sunjinghao.shorturl.gateway.domain.impl;

import cn.hutool.core.bean.BeanUtil;
import com.sunjinghao.shorturl.api.bo.DomainMappingInfoBo;
import com.sunjinghao.shorturl.api.dto.DomainMappingInfoDto;
import com.sunjinghao.shorturl.common.result.GlobalResultCodeEnum;
import com.sunjinghao.shorturl.common.result.RpcResult;
import com.sunjinghao.shorturl.gateway.domain.IUrlTransferService;
import com.sunjinghao.shorturl.service.dao.IUrlDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 链接转换处理服务实现
 * <p>
 * /domain package 用于按领域聚合服务 套接层 （微服务聚合层，如调用多个rpc 或  http 服务）
 *
 * @author sunjinghao
 */
@Service
public class UrlTransferService implements IUrlTransferService {

    /**
     * 数据服务层注入
     * rpc 或 http
     */
    @Resource
    private IUrlDao urlDao;


    /**
     * 生成短链 code 与 长链接 做映射
     * <p>
     * 若存在长链映射 覆盖，不存在则新生成
     *
     * @return DomainMappingInfoBo
     */
    @Override
    public DomainMappingInfoBo longUrlMappingShortCode(DomainMappingInfoBo domainMappingInfoBo) {

        DomainMappingInfoDto dto = new DomainMappingInfoDto();
        BeanUtil.copyProperties(domainMappingInfoBo, dto);

        RpcResult<DomainMappingInfoDto> result = urlDao.save(dto);
        if (result.getCode().equals(GlobalResultCodeEnum.REQUEST_OK.getCode())) {
            BeanUtil.copyProperties(result.getData(), domainMappingInfoBo);
        }
        return domainMappingInfoBo;
    }

    /**
     * 根据短链 code 获取 长链接
     * <p>
     * 需要根据短链 拿到 长链映射的id,再查长链
     *
     * @return DomainMappingInfoBo
     */
    @Override
    public DomainMappingInfoBo getLongUrlByShortCode(DomainMappingInfoBo domainMappingInfoBo) {

        DomainMappingInfoDto dto = new DomainMappingInfoDto();
        dto.setShortCode(domainMappingInfoBo.getShortCode());

        RpcResult<DomainMappingInfoDto> result = urlDao.getByCode(dto);
        if (result.getCode().equals(GlobalResultCodeEnum.REQUEST_OK.getCode())) {
            BeanUtil.copyProperties(result.getData(), domainMappingInfoBo);

        }
        return domainMappingInfoBo;
    }

    /**
     * 根据长链接 获取  code
     *
     * 需要根据短链 拿到 长链映射的id,再查长链
     *
     * @return DomainMappingInfoBo
     */
//    @Override
//    public DomainMappingInfoBo getShortCodeByUrl(DomainMappingInfoBo domainMappingInfoBo) {
//
//        DomainMappingInfoDto dto = new DomainMappingInfoDto();
//        dto.setShortCode(domainMappingInfoBo.getUrl());
//
//        RpcResult<DomainMappingInfoDto> result = urlDao.getByUrl(dto);
//        if (result.getCode().equals(GlobalResultCodeEnum.REQUEST_OK)) {
//            BeanUtil.copyProperties(result.getData(), domainMappingInfoBo);
//        }
//        return domainMappingInfoBo;
//    }
}
