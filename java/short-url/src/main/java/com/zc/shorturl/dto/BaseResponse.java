package com.zc.shorturl.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 返回对象包装类
 * @param <T> 实际返回的业务数据对象
 */
@Getter
@Setter
@ToString
public class BaseResponse<T> {
  // 时间戳
  private long timestamp;
  // 是否成功
  private boolean success;
  // 错误代码
  private int code;
  // 提示信息
  private String message;
  // 返回的数据
  private T data;

  public BaseResponse() {
    this(ResultCode.SUCCESS);
  }

  public BaseResponse(ResultCode resultCode) {
    this.success = resultCode.isSuccess();
    this.code = resultCode.getCode();
    this.message = resultCode.getMessage();
    this.timestamp = System.currentTimeMillis();
  }

  public BaseResponse(ResultCode resultCode, String message) {
    this.success = resultCode.isSuccess();
    this.code = resultCode.getCode();
    this.message = message;
    this.timestamp = System.currentTimeMillis();
  }

  /**
   * 返回成功并设置data
   * @param data
   * @param <E> data类型
   * @return
   */
  public static <E> BaseResponse<E> ok(E data) {
    BaseResponse<E> baseResponse = new BaseResponse<>();
    baseResponse.setData(data);
    return baseResponse;
  }

  /**
   * 返回错误
   * @param resultCode
   * @return
   */
  public static BaseResponse<ResultCode> fail(ResultCode resultCode) {
    return new BaseResponse<>(resultCode);
  }

  /**
   * 返回错误并自定义message
   * @param resultCode
   * @param message
   * @return
   */
  public static BaseResponse<ResultCode> fail(ResultCode resultCode, String message) {
    return new BaseResponse<>(resultCode, message);
  }
}