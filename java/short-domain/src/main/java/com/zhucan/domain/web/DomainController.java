package com.zhucan.domain.web;

import com.zhucan.domain.application.command.DomainCommandService;
import com.zhucan.domain.application.command.cmd.DomainMetathesisCommand;
import com.zhucan.domain.application.query.DomainQueryService;
import com.zhucan.domain.application.query.dto.LongDomainDTO;
import com.zhucan.domain.application.query.dto.ShortDomainDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhuCan
 * @description 域名转换API
 * @since 2022/4/2 21:32
 */
@RestController
@RequestMapping("/domain")
@Api(value = "域名转换API", tags = "域名 - 管理")
@AllArgsConstructor
public class DomainController {

  private final DomainCommandService commandService;
  private final DomainQueryService queryService;

  @PostMapping("/short")
  @ApiOperation("转换短域名")
  public ShortDomainDTO shortDomain(@RequestBody @Validated DomainMetathesisCommand command) {
    return commandService.metathesisShortDomain(command);
  }

  @GetMapping("/long")
  @ApiOperation("置换长域名")
  public LongDomainDTO longDomain(@RequestParam @ApiParam(required = true, name = "shortDomain") String shortDomain) {
    return queryService.metathesisLongDomain(shortDomain);
  }

}
