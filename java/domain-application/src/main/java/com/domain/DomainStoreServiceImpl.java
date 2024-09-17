package com.domain;

import com.domain.cache.DomainMap;
import com.domain.common.ConvertTool;
import com.domain.enums.BizCode;
import com.domain.model.DomainDTO;
import com.domain.model.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * @author: xielongfei
 * @date: 2022/01/11
 * @description: 域名service实现，可聚合业务操作
 */
@Service
@Slf4j
public class DomainStoreServiceImpl implements IDomainStoreService {


    /**
     * 此处如果需要考虑解耦的话。
     * 可在基础设施层定义存储类接口，db、cache、mq实现其接口，通过spi扩展类机制来注入要调用的存储方式。
     * 这样存储方式的变更不影响域名领域这块的业务逻辑，与基础实现剥离开。只做业务逻辑的实现，增加了系统的灵活性。
     */

    @Override
    public Response addDomain(DomainDTO domainDTO) {
        String shortUrl = DomainMap.getLongKeyMap().get(domainDTO.getLongUrl());
        if (StringUtils.isEmpty(shortUrl)) {
            shortUrl = String.format("%s%s", ConvertTool.domain_prefix,
                    ConvertTool.toString(domainDTO.getLongUrl()));
            /**
             * 不同的长域名转换后的值可能相同
             * 处理思路：
             *      1. 类似map中红黑树结构，单独存储映射后值相同的问题；
             *      2. 调整转换函数，2次转换处理，相当于用多个字符串函数映射出最终的值，减少字符串碰撞概率。
             * 此处不做处理
             */
            DomainMap.getLongKeyMap().put(domainDTO.getLongUrl(), shortUrl);
            DomainMap.getShortKeyMap().put(shortUrl, domainDTO.getLongUrl());
        }
        domainDTO.setShortUrl(shortUrl);
        log.info("shortUrl：{}, longUrl：{}", shortUrl, domainDTO.getLongUrl());
        return Response.success(domainDTO);
    }


    @Override
    public Response getDomain(DomainDTO domainDTO) {
        String longUrl = DomainMap.getShortKeyMap().get(domainDTO.getShortUrl());
        if (!StringUtils.isEmpty(longUrl)) {
            domainDTO.setLongUrl(longUrl);
            return Response.success(domainDTO);
        }
        return Response.success(domainDTO, BizCode.NO_DATA_MATCHED.getMsg());
    }
}
