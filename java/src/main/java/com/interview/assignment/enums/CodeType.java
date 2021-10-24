package com.interview.assignment.enums;

/**
 * 标记当前短链类型的，比如不同的短链会有不同的跳转方式：web端可以通过状态码直接重定向到目标页面；app端则只需要返回特定url，以让app端自行跳转。
 * 后续有新的场景也可以通过此字段进行扩展
 */
public enum CodeType {
  URL_REDIRECT("url_redirect", "短链跳转");

  private final String type;
  private final String desc;

  CodeType(String type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public String getType() {
    return type;
  }

  public String getDesc() {
    return desc;
  }

  public static CodeType get(String type) {
    for (CodeType value : values()) {
      if (value.type.equals(type)) {
        return value;
      }
    }

    return null;
  }
}
