package com.example.domain.service;

import com.example.domain.service.strategy.DomainGenerateService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @title: DomainRuleStragety
 * @Author DH
 * @Date: 2021/12/6 15:11
 * 短域名生成策略 根据id base62得到一串字符串
 */
@Component
public class ShortDomainGenerateRuleStrategy {

    @Value("${domain.rule}")
    public String rule;

  public Object actionRule() {
      DomainGenerateService domainServiceStrategy = RuleFactory.getDomainServiceStrategys(rule);
      Long id = (Long)domainServiceStrategy.getNextShortDomainId();
      return id;
  }

}
