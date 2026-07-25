package com.example.shortlongurl.framework.utils;

import java.util.*;

public class Base62Utils {
    private static final String CHARACTERS = shuffle("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ");

    /**
     * 随机打乱字符串
     * @param str
     * @return
     */
    private static String shuffle(String str){
        char[] strArr = str.toCharArray();
        Random rnd = new Random();
        for (int i=strArr.length; i>1; i--){
            swap(strArr, i-1, rnd.nextInt(i));
        }
        String shuffleStr = String.valueOf(strArr);
        System.out.println("Remember Base62 initial CharSequence : " + shuffleStr);
        return shuffleStr;
    }

    /**
     * Swaps the two specified elements in the specified array.
     */
    private static void swap(char[] arr, int i, int j) {
        char tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    /**
     * Encodes a decimal value to a Base62 <code>String</code>.
     *
     * @param b10
     * the decimal value to encode, must be nonnegative.
     * @return the number encoded as a Base62 <code>String</code>.
     */
    public static String encodeBase62(long b10) {
        b10 &= 0x7fffffffffffL;//按要求短域名长度最大为 8 个字符；所以取值小于62^8最大全1位的整数相与
        if (b10 < 0) {
            throw new IllegalArgumentException("b10 must be nonnegative");
        }
        String ret = "";
        while (b10 > 0) {
            ret = CHARACTERS.charAt((int) (b10 % 62)) + ret;
            b10 /= 62;
        }
        return ret;

    }

    /**
     * Decodes a Base62 <code>String</code> returning a <code>long</code>.
     *
     * @param b62
     * the Base62 <code>String</code> to decode.
     * @return the decoded number as a <code>long</code>.
     * @throws IllegalArgumentException
     * if the given <code>String</code> contains characters not
     * specified in the constructor.
     */
    public static long decodeBase62(String b62) {
        for (char character : b62.toCharArray()) {
            if (!CHARACTERS.contains(String.valueOf(character))) {
                throw new IllegalArgumentException("Invalid character(s) in string: " + character);
            }
        }
        long ret = 0;
        b62 = new StringBuffer(b62).reverse().toString();
        long count = 1;
        for (char character : b62.toCharArray()) {
            ret += CHARACTERS.indexOf(character) * count;
            count *= 62;
        }
        return ret;
    }
}
