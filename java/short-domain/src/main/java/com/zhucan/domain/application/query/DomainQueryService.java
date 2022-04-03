package com.zhucan.domain.application.query;

import com.zhucan.domain.application.query.dto.LongDomainDTO;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 21:56
 */
public interface DomainQueryService {

  /**
   * 置换长域名
   *
   * @param shortDomain 短域名
   * @return 长域名数据传输对象
   */
  LongDomainDTO metathesisLongDomain(String shortDomain);

}
