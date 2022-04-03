package com.zhucan.domain.application.query.impl;

import com.zhucan.domain.application.query.DomainQueryService;
import com.zhucan.domain.application.query.dto.LongDomainDTO;
import com.zhucan.domain.infrastructure.cache.DomainCache;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 21:57
 */
@Service
@AllArgsConstructor
public class DomainQueryServiceImpl implements DomainQueryService {

  private final DomainCache domainCache;

  @Override
  public LongDomainDTO metathesisLongDomain(String shortDomain) {
    return new LongDomainDTO(domainCache.getLongDomain(shortDomain));
  }
}
