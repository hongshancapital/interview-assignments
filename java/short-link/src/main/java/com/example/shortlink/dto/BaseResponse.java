package com.example.shortlink.dto;

/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
public class BaseResponse {
  static final String ERROR_MSG = "ERROR";
  static final String SUCCESS_MSG = "SUCCESS";
  static final String SUCCESS_CODE = "0";
  static final String ERROR_CODE = "1";

  /**
   * 结果code
   */
  private String resultCode;
  /**
   * 结果message
   */
  private String resultMsg;

  public String getResultCode() {
    return resultCode;
  }

  public void setResultCode(String resultCode) {
    this.resultCode = resultCode;
  }

  public String getResultMsg() {
    return resultMsg;
  }

  public void setResultMsg(String resultMsg) {
    this.resultMsg = resultMsg;
  }

  /**
   * constructor.
   */
  public BaseResponse(String resultCode, String resultMsg) {
    this.resultCode = resultCode;
    this.resultMsg = resultMsg;
  }

  /**
   * success.
   * @return response
   */
  public static BaseResponse success() {
    return new BaseResponse(SUCCESS_CODE, SUCCESS_MSG);
  }

  /**
   * success.
   * @return response
   */
  public static BaseResponse error() {
    return new BaseResponse(ERROR_CODE, ERROR_MSG);
  }

  /**
   * success.
   * @return response
   */
  public static BaseResponse error(String code, String message) {
    return new BaseResponse(code, message);
  }

}
