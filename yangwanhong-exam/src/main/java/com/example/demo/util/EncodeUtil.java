package com.example.demo.util;

import java.util.Map;
import java.util.Stack;
import java.util.concurrent.ConcurrentHashMap;

public class EncodeUtil {

    //62进制
    public static int BASE_NUM = 62;

    //数字+字母大小写一共62个
    public static final char[] array = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    //longUrl:id Map
    public static Map<String,Long> longUrlIdMap= new ConcurrentHashMap<>();

    //id:shortUrl Map
    public static Map<Long,String> idShortUrlMap= new ConcurrentHashMap<>();


    //shortUrl:longUrl Map
    public static Map<String,String> urlMap= new ConcurrentHashMap<>();
    /**
     * 将10进制数转为62进制字符串(短网址)
     *
     * @param number
     * @return
     */
    public static String getShortUrlByLongNum(Long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        if (0 == rest) {
            return String.valueOf(array[0]);
        }
        while (rest != 0) {
            stack.add(array[new Long((rest - (rest / BASE_NUM) * BASE_NUM)).intValue()]);
            rest = rest / BASE_NUM;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();
    }

    /**
     * 通过短网址返回10进制数
     *
     * @param shortUrl
     * @return
     */
    public static Long getLongNumByShortUrl(String shortUrl) {
        long multiple = 1;
        long result = 0;
        Character c;
        for (int i = 0; i < shortUrl.length(); i++) {
            c = shortUrl.charAt(shortUrl.length() - i - 1);
            result += valueOfCharacter(c) * multiple;
            multiple = multiple * BASE_NUM;
        }
        return result;
    }

    /**
     * 字母对应的值
     *
     * @param c
     * @return
     */
    private static int valueOfCharacter(Character c) {
        for (int i = 0; i < array.length; i++) {
            if (c == array[i]) {
                return i;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        long id = 218340105584895L;
        String shortUrl = EncodeUtil.getShortUrlByLongNum(id);
        System.out.println(shortUrl);
        Long longUrlId = EncodeUtil.getLongNumByShortUrl(shortUrl);
        System.out.println(longUrlId);
    }
}
