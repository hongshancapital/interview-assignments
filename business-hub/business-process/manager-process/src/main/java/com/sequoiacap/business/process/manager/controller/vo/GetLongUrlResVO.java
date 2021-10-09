package com.sequoiacap.business.process.manager.controller.vo;

import com.sequoiacap.business.process.manager.cst.ResponseCodeEnum;

public class GetLongUrlResVO extends CommonResVO{

  //长域名
  private String longUrl;

  public GetLongUrlResVO(ResponseCodeEnum responseCodeEnum) {
    super(responseCodeEnum);
  }

  public String getLongUrl() {
    return longUrl;
  }

  public void setLongUrl(String longUrl) {
    this.longUrl = longUrl;
  }
}
