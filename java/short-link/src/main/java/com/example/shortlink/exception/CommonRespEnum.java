package com.example.shortlink.exception;

/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
public enum CommonRespEnum {

  INPUT_PARAM_ERROR("","");

  private String code;

  private String message;

  CommonRespEnum(String code, String message){
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
