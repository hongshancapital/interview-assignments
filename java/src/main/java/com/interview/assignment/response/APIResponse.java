package com.interview.assignment.response;

import com.interview.assignment.enums.ResponseCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

/**
 * 用于统一封装返回值的response
 *
 * @param <T> 具体的body数据
 */
@Getter
@ApiModel("业务response")
public class APIResponse<T> {
  @ApiModelProperty("响应body")
  private T data;
  @ApiModelProperty("响应code")
  private String code;
  @ApiModelProperty("响应message")
  private String message;

  private APIResponse(T data) {
    this.code = ResponseCode.SUCCESS.getCode();
    this.message = ResponseCode.SUCCESS.getMessage();
    this.data = data;
  }

  private APIResponse(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public static <T> APIResponse<T> ok(T data) {
    return new APIResponse<>(data);
  }

  public static <T> APIResponse<T> fail(String code, String message) {
    return new APIResponse<>(code, message);
  }
}
