package com.interview.assignment.util;

import com.interview.assignment.enums.ResponseCode;
import com.interview.assignment.exception.BusinessException;

import java.util.HashMap;
import java.util.Map;

public class Base62Util {
  private static final String baseString = "ly3fbUq5prjsvLMQRIPZ6cBGTEhk8WSnw4XzAJa1YF9euoNxK0d7gmOtiVD2CH";
  private static final char[] indexToCharacter = new char[baseString.length()];
  private static final Map<Character, Integer> characterToIndex = new HashMap<>();

  static {
    for (int i = 0; i < baseString.length(); i++) {
      indexToCharacter[i] = baseString.charAt(i);
      characterToIndex.put(baseString.charAt(i), i);
    }
  }

  public static void main(String[] args) {
    System.out.println(0b111);
  }

  public static String encode(long number) {
    if (number < 0) {
      throw new BusinessException(ResponseCode.ILLEGAL_PARAMS);
    }

    if (number == 0) {
      return String.valueOf(indexToCharacter[0]);
    }

    StringBuilder result = new StringBuilder();
    long left = number;
    while (left != 0) {
      result.append(indexToCharacter[(int)(left % indexToCharacter.length)]);
      left /= indexToCharacter.length;
    }

    return result.reverse().toString();
  }

  public static long decode(String str) {
    if (str.isEmpty()) {
      throw new BusinessException(ResponseCode.ILLEGAL_PARAMS);
    }

    long result = 0;
    for (int i = 0; i < str.length(); i++) {
      Integer index = characterToIndex.get(str.charAt(i));
      if (index == null) {
        throw new BusinessException(ResponseCode.ILLEGAL_PARAMS);
      }

      result = result * characterToIndex.size() + index;
    }

    return result;
  }
}
