package com.zc.shorturl.dto;

import lombok.Getter;

import java.time.LocalDateTime;

/**
 * 返回状态代码标识
 *
 */
@Getter
public enum ResultCode {
  // 成功
  SUCCESS(true, 200, "成功！"),

  // 40xx
  PARAMETER_ERROR(false, 4001, "参数错误"),
  PARAM_FORMAT_ERROR(false, 4002, "请求参数格式错误"),
  URL_NOT_FOUND(false, 4043, "url不存在或已过期"),
  NOT_FOUND(false, 4040, "资源不存在"),

  // 50xx
  INTERNAL_SERVER_ERROR(false, 5000, "服务器内部错误"),
  UNKNOWN_EXCEPTION(false, 5999, "未知异常"),
  ;

  // 是否成功
  private final boolean success;

  // 错误代码
  private final int code;

  // 错误信息
  private final String message;

  // 异常时间
  private final long timestamp;

  ResultCode(boolean success, int code, String message) {
    this.success = success;
    this.code = code;
    this.message = message;
    this.timestamp = System.currentTimeMillis();
  }
}
