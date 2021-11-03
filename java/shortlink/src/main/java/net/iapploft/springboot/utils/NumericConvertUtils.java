package net.iapploft.springboot.utils;

import java.math.BigInteger;

public class NumericConvertUtils {
    /**
     * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号表示
     */
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 62进制to十进制BigInteger
     */
    public static BigInteger Base62ToBase10(String s) {
        BigInteger n = BigInteger.ZERO;
        for (int i = 0; i < s.length(); i++) {
            n = n.multiply(BigInteger.valueOf(62)).add(BigInteger.valueOf(convert(s.charAt(i))));
        }
        return n;
    }

    private static int convert(char c) {
        if (c >= '0' && c <= '9')
            return c - '0';
        if (c >= 'a' && c <= 'z') {
            return c - 'a' + 10;
        }
        if (c >= 'A' && c <= 'Z') {
            return c - 'A' + 36;
        }
        return -1;
    }

    /**
     * 十进制62进制8位字符串
     */
    public static String Base10ToBase62(BigInteger n) {
        StringBuilder sb = new StringBuilder();
        while (!BigInteger.ZERO.equals(n)) {
            sb.insert(0, digits[n.mod(new BigInteger("62")).intValue()]);
            n = n.divide(new BigInteger("62"));
        }
        while (sb.length() != 8) {
            sb.insert(0, '0');
        }
        return sb.toString();
    }

//
//    public static void main(String[] args) {
//
//        BigInteger  big = new BigInteger("200000000000");
//        String shortCode =  Base10ToBase62(big);
//        BigInteger orgInter = Base62ToBase10(shortCode);
//        System.out.println(shortCode);
//        System.out.println(orgInter);
//
//    }



}
