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

    // 原始链接
    String sLongUrl = "http://www.51bi.com/bbs/_t_278433840/";
    // 将产生4组6位字符串
    String[] aResult = DomainMetathesisUtil.shortUrl(sLongUrl);
    // 打印出结果
    for (int i = DomainMetathesisUtil.Constants.INDEX_0; i < aResult.length; i++) {
      System.out.println("[" + i + "]:" + aResult[i]);
    }
    Random random = new Random();
    // 生成4以内随机数
    int j = random.nextInt(DomainMetathesisUtil.Constants.INDEX_4);
    // 随机取一个作为短链
    System.out.println("短链接:" + aResult[j]);

    String s = aResult[j];
    domainCache.save(s, command.getDomain());
    String serverHost = "http://localhost:8080";
    try {
      serverHost = InetAddress.getLocalHost().getHostAddress() + ":" + environment.resolvePlaceholders("${server.port}") + "/";

    } catch (UnknownHostException e) {
      e.printStackTrace();
    }
    return serverHost + s;
  }
}
