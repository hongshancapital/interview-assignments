package com.example.shortlink.controller;

import com.example.shortlink.dto.BaseDataResponse;
import com.example.shortlink.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
@Api(value = "短链接转化 API", tags = {"短链接转化 API"})
@RestController
@RequestMapping(value = "/link")
public class LinkController {

  @Autowired
  private LinkService linkService;

  /**
   * 短链接换长链接.
   *
   * @param shortLink 短连接
   * @return 返回结果
   */
  @ApiOperation(value = "短链接换长链接", notes = "短链接换长链接")
  @GetMapping(value = "/shortChangeLong")
  public BaseDataResponse<String> shortChangeLong(@ApiParam("短连接") String shortLink) {
    return BaseDataResponse.success(linkService.shortChangeLong(shortLink));
  }

  /**
   * 长链接换短链接.
   *
   * @param longLink 长连接
   * @return 返回结果
   */
  @ApiOperation(value = "长链接换短链接", notes = "长链接换短链接")
  @GetMapping(value = "/longChangShort")
  public BaseDataResponse<String> longChangShort(@ApiParam("长连接") String longLink) {
    return BaseDataResponse.success(linkService.longChangShort(longLink));
  }


}
