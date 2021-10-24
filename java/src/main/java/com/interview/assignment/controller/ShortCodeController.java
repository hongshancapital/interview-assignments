package com.interview.assignment.controller;

import com.interview.assignment.request.APIRequest;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import com.interview.assignment.request.ShortCodeQueryRequest;
import com.interview.assignment.response.APIResponse;
import com.interview.assignment.response.ShortCodeGenerateResponse;
import com.interview.assignment.response.ShortCodeQueryResponse;
import com.interview.assignment.service.ShortCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

@Api("短链管理")
@RestController
@RequestMapping("/")
public class ShortCodeController {

  @Autowired
  private ShortCodeService shortCodeService;

  @ApiOperation("生成短链")
  @ResponseBody
  @RequestMapping(value = "/short/code/generate", method = RequestMethod.POST)
  public APIResponse<ShortCodeGenerateResponse> generate(@RequestBody @Validated APIRequest<ShortCodeGenerateRequest> request) {
    ShortCodeGenerateResponse response = shortCodeService.generate(request.getBody());
    return APIResponse.ok(response);
  }

  @ApiOperation("获取短链详情")
  @ResponseBody
  @RequestMapping(value = "/short/code/detail", method = RequestMethod.GET)
  public APIResponse<ShortCodeQueryResponse> detail(@RequestParam("shortCode") String shortCode) {
    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode(shortCode);
    ShortCodeQueryResponse response = shortCodeService.detail(request);
    return APIResponse.ok(response);
  }

  @ApiOperation("根据短链进行重定向")
  @RequestMapping(value = "/{shortCode}", method = RequestMethod.GET)
  public void redirect(@ApiParam("短码") @PathVariable("shortCode") String shortCode, HttpServletResponse servletResponse) throws IOException {
    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode(shortCode);
    ShortCodeQueryResponse response = shortCodeService.detail(request);
    servletResponse.sendRedirect(response.getUrl());
  }

}
