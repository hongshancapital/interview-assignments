package com.interview.assignment.enums;

/**
 * 标记短链的时效类型的，分为永久和限时两种。至于只有时间上限或下限的类型，可以通过设置一个比较大的时间或比较小的时间来实现
 */
public enum DurationType {
  PERMANENT("permanent", "永久"), LIMITED_TIME("limited_time", "限时");

  private final String type;
  private final String desc;

  DurationType(String type, String desc) {
    this.type = type;
    this.desc = desc;
  }

  public final String getType() {
    return type;
  }

  public final String getDesc() {
    return desc;
  }

  public static DurationType get(String type) {
    for (DurationType value : values()) {
      if (value.type.equals(type)) {
        return value;
      }
    }

    return null;
  }
}
