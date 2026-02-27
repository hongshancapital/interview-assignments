package com.url.transform.common.response;
import lombok.Data;
/**
 * @author xufei
 * @Description
 * @date 2021/12/10 10:18 AM
 **/
@Data
public class RestResult<T> {

  private String code;

  private String message;

  private T data;

  protected RestResult(T data) {
    this.code = "0000";
    this.message = "success";
    this.data = data;
  }

  protected RestResult() {
    this.code = "0000";
    this.message = "success";
  }

  public static <T> RestResult<T> success(T data) {
    return new RestResult<>(data);
  }

  public static <T> RestResult<T> data(T data) {
    return new RestResult<>(data);
  }

  public static RestResult success() {
    return new RestResult();
  }

  public static RestResult error() {
    return error(500, "未知异常，请联系管理员");
  }

  public static RestResult error(String msg) {
    return error(500, msg);
  }

  public static RestResult error(String msg, Object data) {
    return error(500, msg, data);
  }

  public static RestResult error(int code, String msg) {
    RestResult r = new RestResult();
    r.setCode(code + "");
    r.setMessage(msg);
    return r;
  }

  public static RestResult error(int code, String msg, Object data) {
    RestResult r = new RestResult();
    r.setCode(code + "");
    r.setMessage(msg);
    r.setData(data);
    return r;
  }

  public static RestResult error(String code, String msg) {
    RestResult r = new RestResult();
    r.setCode(code);
    r.setMessage(msg);
    return r;
  }

}
