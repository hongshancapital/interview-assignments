package com.zhucan.domain.application.command.impl;

import com.zhucan.domain.application.command.DomainCommandService;
import com.zhucan.domain.application.command.cmd.DomainMetathesisCommand;
import com.zhucan.domain.application.query.dto.ShortDomainDTO;
import com.zhucan.domain.infrastructure.cache.DomainCache;
import com.zhucan.domain.infrastructure.utils.DomainMetathesisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 21:57
 */
@Slf4j
@Service
@AllArgsConstructor
public class DomainCommandServiceImpl implements DomainCommandService {

  private final DomainCache domainCache;
  private final Environment environment;

  @Override
  public ShortDomainDTO metathesisShortDomain(DomainMetathesisCommand command) {

    ShortDomainDTO domainDTO = new ShortDomainDTO();

    // 将产生4组6位字符串
    String[] shortDomains = DomainMetathesisUtil.shortUrl(command.getDomain());

    // 打印出结果
    for (String domain : shortDomains) {
      log.info("short domain:{}", domain);
    }

    String shortDomain = shortDomains[0];
    domainDTO.setShortDomain(shortDomain);
    try {
      String serverHost = InetAddress.getLocalHost().getHostAddress() + ":" + environment.resolvePlaceholders("${server.port}") + "/d/";
      domainCache.save(serverHost + shortDomain, command.getDomain());
      domainDTO.setFullShortDomain(serverHost + shortDomain);
    } catch (UnknownHostException e) {
      log.error("获取当前服务域名异常", e);
    }

    domainCache.save(shortDomain, command.getDomain());

    return domainDTO;
  }
}
