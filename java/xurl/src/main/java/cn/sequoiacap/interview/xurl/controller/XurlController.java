package cn.sequoiacap.interview.xurl.controller;

import cn.sequoiacap.interview.xurl.config.AppConfig;
import cn.sequoiacap.interview.xurl.service.XurlService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Api(value = "xurl服务", tags = "短链接接口")
@RestController
@RequestMapping("/api/v1/xurl")
public class XurlController {

  private final XurlService xurlService;

  private final String SHORT_URL_PREFIX;

  private final UrlValidator urlValidator = new UrlValidator();

  public XurlController(XurlService xurlService, AppConfig appConfig) {
    this.xurlService = xurlService;
    SHORT_URL_PREFIX = String.format("https://%s/", appConfig.getShortDomain());
  }

  @Operation(summary = "通过短码读取原始链接接口", description = "通过短码获取原始url接口")
  @ApiResponse(description = "字符串，原始url", responseCode = "200")
  @ResponseBody
  @RequestMapping(value = "/{shortCode:[\\da-zA-Z]{1,8}}", method = RequestMethod.GET)
  public String decodeXurl(
      @Parameter(in = ParameterIn.PATH, description = "短码") @PathVariable String shortCode) {
    String ori = xurlService.getOriUrl(shortCode);
    if (ori == null) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "original url not found");
    }
    return ori;
  }

  @Operation(summary = "获取短链接接口", description = "传入url参数获取短链接表示")
  @ApiResponse(description = "字符串，短链接", responseCode = "200")
  @ResponseBody
  @RequestMapping(value = "/gen", method = RequestMethod.PUT)
  public String genXurl(
      @Parameter(in = ParameterIn.QUERY, description = "原始url链接") @RequestParam String url) {
    if (!urlValidator.isValid(url)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "invalid url");
    }
    try {
      return SHORT_URL_PREFIX + xurlService.getShortCode(url);
    } catch (XurlService.GenerateError e) {
      throw new ResponseStatusException(
          HttpStatus.EXPECTATION_FAILED, "service temporarily failed");
    }
  }
}
