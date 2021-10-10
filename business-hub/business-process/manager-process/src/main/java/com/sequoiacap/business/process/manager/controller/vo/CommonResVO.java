package com.sequoiacap.business.process.manager.controller.vo;

import com.sequoiacap.business.process.manager.cst.ResponseCodeEnum;
import io.swagger.annotations.ApiModelProperty;

public class CommonResVO<T> {

  public CommonResVO(ResponseCodeEnum responseCodeEnum){
    this.code=responseCodeEnum.getCode();
  }

  //返回报文是否成功标记
  @ApiModelProperty("status")
  private int code;
  //返回报文失败原因
  @ApiModelProperty("message")
  private String message;

  private T data;

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

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}
