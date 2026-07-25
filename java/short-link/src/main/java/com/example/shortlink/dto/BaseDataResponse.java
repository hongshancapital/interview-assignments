package com.example.shortlink.dto;

/**
 * @author tianhao.lan
 * @date 2021-12-27.
 */
public class BaseDataResponse<T> extends BaseResponse{

  /**
   * 数据
   */
  private T data;


  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  /**
   * constructor.
   */
  public BaseDataResponse(String resultCode, String resultMsg, T data) {
    super(resultCode, resultMsg);
    this.data = data;
  }

  /**
   * success.
   * @param t 数据
   * @param <T> 数据
   * @return response
   */
  public static <T> BaseDataResponse<T> success(T t) {
    return new BaseDataResponse<T>(SUCCESS_CODE, SUCCESS_MSG, t);
  }

}
