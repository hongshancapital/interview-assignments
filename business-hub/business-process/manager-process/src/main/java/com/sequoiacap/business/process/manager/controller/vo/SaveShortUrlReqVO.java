package com.sequoiacap.business.process.manager.controller.vo;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class SaveShortUrlReqVO {

  //长域名
  @ApiModelProperty(
          value = "长域名",
          required = true,
          position = 1
  )
  @NotBlank(message = "长域名不能为空")
  private String longUrl;

  public String getLongUrl() {
    return longUrl;
  }

  public void setLongUrl(String longUrl) {
    this.longUrl = longUrl;
  }

}
