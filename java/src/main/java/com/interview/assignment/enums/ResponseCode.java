package com.interview.assignment.enums;

public enum ResponseCode {
  SUCCESS("00000000", "success"),
  ILLEGAL_PARAMS("00000001", "illegal param"),
  SERVER_ERROR("00000002", "server error"),
  RECORD_NOT_FOUNT("00000003", "record not found"),
  SHORT_CODE_EXPIRED("00000004", "short code expired");

  private final String code;
  private final String message;

  ResponseCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
