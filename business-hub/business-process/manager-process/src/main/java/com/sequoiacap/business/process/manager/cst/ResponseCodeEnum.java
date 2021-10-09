package com.sequoiacap.business.process.manager.cst;

/**
 * @ClassName: ResponseCodeEnum
 * @Description: 报文返回code信息
 * @Author: xulong.wang
 * @Date 10/9/2021
 * @Version 1.0
 */
public enum ResponseCodeEnum {

  SUCC(1,"SUCC"),
  FAIL(2,"FAIL"),
  PARTIAL_SUCC(3,"PARTIAL_SUCC");

  private int code;
  private String desc;

  // 构造方法
  private ResponseCodeEnum(int code,String desc) {
    this.code=code;
    this.desc=desc;
  }

  public int getCode() {
    return code;
  }

  public String getDesc() {
    return desc;
  }
}
