package com.interview.assignment.constant;

public final class ConstantProviders {

  private static final String PREFIX = ConstantVariables.APPLICATION_NAME + ":";

  private ConstantProviders() {}

  public static String getIdCounterLockKey() {
    return PREFIX + "i:c:l:k";
  }

  public static String getIdCounterKey() {
    return PREFIX + "i:c:k";
  }
}
