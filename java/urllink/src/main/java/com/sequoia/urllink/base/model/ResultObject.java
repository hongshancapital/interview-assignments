package com.sequoia.urllink.base.model;


/**
 * 返回结果对象:避免使用map的时候，key不统一问题或key输入错误
 * @author liuhai
 * @date 2022.4.15
 */
public class ResultObject<T extends AbstractPojo> {
  // 返回结果
  private Object data;
  // 查询条件
  private T param;

  public ResultObject(T param) {
    super();
    this.param = param;
  }

  public ResultObject() {
    super();
  }

  public static <T> ResultMessage newSuccessMessage(String message, T data) {
    ResultMessage msg = new ResultMessage(ResultMessage.ResultCode.SUCCESS.getCode(), message, data);
    return msg;
  }

  public ResultMessage createSuccessMessage(String message) {
    ResultMessage msg = new ResultMessage(ResultMessage.ResultCode.SUCCESS.getCode(), message, this.data);
    return msg;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
    this.param = null;
  }

  public T getParam() {
    return param;
  }

  public void setParam(T param) {
    this.param = param;
  }
}
