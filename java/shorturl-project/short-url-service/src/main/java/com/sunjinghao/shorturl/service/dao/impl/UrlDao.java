package com.sunjinghao.shorturl.service.dao.impl;


import com.sunjinghao.shorturl.common.result.RpcResult;
import com.sunjinghao.shorturl.common.util.RpcResultUtil;
import com.sunjinghao.shorturl.api.dto.DomainMappingInfoDto;
import com.sunjinghao.shorturl.service.dao.IUrlDao;
import com.sunjinghao.shorturl.service.cache.IShortCodeCache;
import com.sunjinghao.shorturl.common.util.NumberEncodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 链接存贮接口
 * <p>
 * 方法返回封装 RPC 或 Http 套结泛型，入参尽量用实体类 减少接口协议变动
 *
 * @author sunjinghao
 */
@Service
@Slf4j
public class UrlDao implements IUrlDao<DomainMappingInfoDto> {

    @Autowired
    private IShortCodeCache shortCodeCache;


    /**
     * 模拟数据库自id
     */
    private static Long id = 100L;

    /**
     * 62进制
     */
    private static final int SEED = 62;

    /**
     * 保存链接信息
     *
     * @param domainMappingInfoDto
     * @return
     */
    @Override
    public RpcResult<DomainMappingInfoDto> save(DomainMappingInfoDto domainMappingInfoDto) {
        if (domainMappingInfoDto == null) {
            return RpcResultUtil.error( "domainMappingInfoDto is null ");
        }

        //先查询缓存
        // 命中则存在映射,直接返回，节省DB的压力
        DomainMappingInfoDto dto = shortCodeCache.getByLongUrl(domainMappingInfoDto);
        if (dto != null) {
            return RpcResultUtil.success(dto);
        }
        //取自增id
        Long identityId = getIdentityId();
        //id与短链 & id与长链映射关系
        dto = new DomainMappingInfoDto();
        dto.setId(identityId);
        dto.setUrl(domainMappingInfoDto.getUrl());
        dto.setShortCode(idTo62(identityId));

        // 未命中则不存在映射,直接返回，节省DB的压力
        return RpcResultUtil.success(shortCodeCache.add(dto));

    }


    /**
     * 发号器：取自增有顺Id
     * 模拟取db自增id
     *
     * @return
     */
    private Long getIdentityId() {
        //单体架构 jvm 锁
        //分布式架构需要加分布式锁
        //保证一次一个线程取id
        synchronized (this) {
            id++;
            log.info("@@::::::::::::::::::::::::::getIdentityId::{}:::::::::::::::::::::::::::::::::::::::::::::::::::::}", id);
        }
        return id;
    }

    private String idTo62(Long identityId) {
        //混淆函数加密
        Long blurId = NumberEncodeUtil.encryptNumber(identityId);
        //62进制转换成短链code
        return NumberEncodeUtil.numberEncryptToStr(blurId, SEED);

    }


    /**
     * 短链查长链
     *
     * @param domainMappingInfoDto
     * @return
     */
    @Override
    public RpcResult getByCode(DomainMappingInfoDto domainMappingInfoDto) {
        if (domainMappingInfoDto == null) {
            return RpcResultUtil.error( "domainMappingInfoDto is null ");
        }
        return RpcResultUtil.success(shortCodeCache.getByCode(domainMappingInfoDto));
    }

    /**
     * 长链查短链
     *
     * @param domainMappingInfoDto
     * @return
     */
    @Override
    public RpcResult getByUrl(DomainMappingInfoDto domainMappingInfoDto) {
        if (domainMappingInfoDto == null) {
            return RpcResultUtil.error( "domainMappingInfoDto is null ");
        }
        return RpcResultUtil.success(shortCodeCache.getByLongUrl(domainMappingInfoDto));
    }
}
