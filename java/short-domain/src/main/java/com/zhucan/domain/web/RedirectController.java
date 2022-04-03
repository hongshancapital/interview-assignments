package com.zhucan.domain.web;

import com.zhucan.domain.application.query.DomainQueryService;
import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 22:21
 */
@Controller
@RequestMapping("/d")
@Api(value = "短域名转发", tags = "域名 - 转发")
@AllArgsConstructor
public class RedirectController {

  private final DomainQueryService queryService;

  @GetMapping("/{shortDomain}")
  public String ShortDomainRedirect(@PathVariable String shortDomain) {
    return "redirect:" + queryService.metathesisLongDomain(shortDomain).getDomain();
  }
}
