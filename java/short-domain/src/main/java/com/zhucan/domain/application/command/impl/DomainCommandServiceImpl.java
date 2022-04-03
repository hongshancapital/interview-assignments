package com.zhucan.domain.application.command.impl;

import com.zhucan.domain.application.command.DomainCommandService;
import com.zhucan.domain.application.command.cmd.DomainMetathesisCommand;
import com.zhucan.domain.infrastructure.cache.DomainCache;
import com.zhucan.domain.infrastructure.utils.DomainMetathesisUtil;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Random;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 21:57
 */
@Service
@AllArgsConstructor
public class DomainCommandServiceImpl implements DomainCommandService {

  private final DomainCache domainCache;
  private final Environment environment;

  @Override
  public String metathesisShortDomain(DomainMetathesisCommand command) {

    // 将产生4组6位字符串
    String[] aResult = DomainMetathesisUtil.shortUrl(command.getDomain());

    // 打印出结果
    for (int i = DomainMetathesisUtil.Constants.INDEX_0; i < aResult.length; i++) {
      System.out.println("[" + i + "]:" + aResult[i]);
    }

    String s = aResult[1];
    domainCache.save(s, command.getDomain());

    return s;
  }
}
