package com.domain;

import com.domain.model.DomainDTO;
import com.domain.model.Response;

/**
 * @author: xielongfei
 * @date: 2022/01/09
 * @description: 域名存储接口
 */
public interface IDomainStoreService {

    /**
     * 新增一个域名
     */
    Response addDomain(DomainDTO domainDTO);

    /**
     * 查询一个域名
     */
    Response getDomain(DomainDTO domainDTO);
}
