package com.zhucan.domain.application.command;

import com.zhucan.domain.application.command.cmd.DomainMetathesisCommand;
import com.zhucan.domain.application.query.dto.ShortDomainDTO;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/2 21:40
 */
public interface DomainCommandService {

  /**
   * 转换短域名
   *
   * @param command 域名转换命令
   * @return 长域名
   */
  ShortDomainDTO metathesisShortDomain(DomainMetathesisCommand command);


}
