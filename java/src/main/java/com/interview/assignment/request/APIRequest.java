package com.interview.assignment.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;

/**
 * 对request的统一封装，用于声明一些公共字段，比如域名、终端类型、语言等
 *
 * @param <T> body数据
 */
@Getter
@Setter
@ApiModel("公共请求")
public class APIRequest<T> {

  @Valid
  @ApiModelProperty("请求body")
  private T body;

  public static <T> APIRequest<T> instance(T body) {
    APIRequest<T> result = new APIRequest<>();
    result.setBody(body);
    return result;
  }
}
