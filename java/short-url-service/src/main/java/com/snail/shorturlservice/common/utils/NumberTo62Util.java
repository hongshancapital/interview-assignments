package com.snail.shorturlservice.common.utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * 十进制正整数转62进制
 */
public class NumberTo62Util {
//    private static String baseStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static String baseStr = "x5sZpw8CzhdeTWatq6m9j7iDUOKlvFJ341cRkryLQ2VXHN0ubnYSfgEGPMoIAB";
    private static char[] charArray = baseStr.toCharArray();
    private static int BASE = 62;

    /**
     * 十进制正整数转62进制，小于等于0的数字返回原数字字符串
     *
     * @param number 十进制正整数
     * @return
     */
    public static String to62(long number) {
        if (number <= 0) return String.valueOf(number);

        long rest = number;
        Stack<Character> stack = new Stack<Character>();
        while (rest > 0) {
            int index = (int) (rest % BASE);
            stack.add(charArray[index]);
            rest = rest / BASE;
        }

        StringBuilder sb = new StringBuilder(stack.size());
        while (!stack.isEmpty()) {
            sb.append(stack.pop());
        }

        return sb.toString();
    }

    /**
     * 十进制正整数转62进制，小于等于0的数字返回原数字字符串
     *
     * @param number 十进制正整数
     * @param minLength 指定字符串长度，生成的字符串长度不足时前补0，超过指定长度的不变
     * @return
     */
    public static String to62(long number, int minLength) {
        String str = to62(number);
        StringBuilder sb = new StringBuilder(minLength);
        int count = minLength - str.length();
        while (count > 0){
            sb.append(0);
            count--;
        }
        sb.append(str);
        return sb.toString();
    }

    /**
     * 反向，62进制转换成十进制正整数
     *
     * @param str62
     * @return
     */
    private static long numberFrom62(String str62) {
        str62 = str62.replaceAll("^0*", "");
        long decimal = 0;
        char[] arr = str62.toCharArray();
        for (int i = arr.length - 1; i >= 0; i--) {
            int index = baseStr.indexOf(arr[i]);
            long num = index;
            int count = arr.length - 1 - i;
            long pow = (long) Math.pow(BASE, count);
            num *= pow;
            decimal += num;
        }
        return decimal;
    }

    public static void main(String[] args) {
//        shuffle();
//        long number = 56_800_235_583L;
        long number = 1L;
        String str62 = to62(number, 6);
        System.out.println("number " + number + " to62:" + str62);
        System.out.println("numberFrom62:" + numberFrom62(str62));
    }

    private static void shuffle(){
        String[] arr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".split("");
        List list = Arrays.asList(arr);
        Collections.shuffle(list);
        list.forEach(System.out::print);
    }

}
