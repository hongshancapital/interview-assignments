package com.interview.assignment.exception;

import com.interview.assignment.enums.ResponseCode;
import lombok.Data;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
  private String code;
  private String message;

  public BusinessException(String code, String message) {
    super(message);
    this.code = code;
    this.message = message;
  }

  public BusinessException(ResponseCode responseCode) {
    super(responseCode.getMessage());
    this.code = responseCode.getCode();
    this.message = responseCode.getMessage();
  }
}
