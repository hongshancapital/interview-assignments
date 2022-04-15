package com.sequoia.urllink.base.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Collections;

/**
 * @author liuhai
 * @date 2022.4.15
 * \返回结果 ：swagger的文档使用
 */
@ApiModel(value = "ResultMessage", description = "返回结果信息")
public class ResultMessage<T> {
  @ApiModelProperty(value = "返回结果代码:0-成功 1-失败 2-服务异常", allowableValues = "0,1,2", example = "1")
  private int code;
  @ApiModelProperty(value = "记录总条数", example = "100")
  private Long count;
  @ApiModelProperty(value = "返回数据")
  private T data;
  @ApiModelProperty(value = "结果提示文本信息", example = "查询成功")
  private String message;
  @ApiModelProperty(value = "记录分页总数", example = "10")
  private Long total;

  public ResultMessage() {
  }

  public ResultMessage(int code, String message) {
    this.code = code;
    this.message = message;
  }

  public ResultMessage(int code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public ResultMessage(int code, String message, T data, Long count, Long total) {
    this.code = code;
    this.message = message;
    this.data = data;
    this.count = count;
    this.total = total;
  }

  public static ResultMessage createErrorMessage(String message) {
    return new ResultMessage(ResultCode.ERROR.getCode(), message, Collections.EMPTY_MAP);
  }

  public static ResultMessage createFailMessage(String message) {
    return new ResultMessage(ResultCode.FAIL.getCode(), message, Collections.EMPTY_MAP);
  }

  public static ResultMessage createSuccessMessage(String message) {
    return new ResultMessage(ResultCode.SUCCESS.getCode(), message, Collections.EMPTY_MAP);
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public Long getCount() {
    return count;
  }

  public void setCount(Long count) {
    this.count = count;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getTotal() {
    return total;
  }

  public void setTotal(Long total) {
    this.total = total;
  }

  @Override
  public String toString() {
    return "ResultMessage{" + "code=" + code + ", message='" + message + '\'' + ", data=" + data + ", count=" + count +
        ", total=" + total + '}';
  }

  public enum ResultCode {
    SUCCESS("成功", 0),
    FAIL("失败", 1),
    ERROR("服务异常", 2);

    private int code;
    private String name;

    private ResultCode(String name, int code) {
      this.name = name;
      this.code = code;
    }

    public int getCode() {
      return this.code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getName() {
      return this.name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}