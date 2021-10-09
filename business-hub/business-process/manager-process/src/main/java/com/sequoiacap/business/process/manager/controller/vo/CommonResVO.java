package com.sequoiacap.business.process.manager.controller.vo;

import com.sequoiacap.business.process.manager.cst.ResponseCodeEnum;

public class CommonResVO {

  public CommonResVO(ResponseCodeEnum responseCodeEnum){
    this.code=responseCodeEnum.getCode();
  }

  //返回报文是否成功标记
  private int code;
  //返回报文失败原因
  private String message;

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
