package org.zxl.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zhouxiliang
 * @date 2021/11/2 13:35
 */
public class DomainUtils {
    static char[] arr = {
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
    };

    static int BASE = arr.length;

    static Map<Character, Integer> tokenToIntMap = new HashMap<>();

    static {
        for (int i = 0; i < arr.length; ++i) {
            tokenToIntMap.put(arr[i], i);
        }
    }

    /**
     * origin 与 size 相加
     *
     * @param origin
     * @param size
     * @return
     */
    public static String add(String origin, int size) {
        return convertToBase62(add(convertToInt(origin), convertToInt(size)));
    }

    /**
     * 获取62进制 指定位的代表char
     *
     * @param pos
     * @return
     */
    public static char getToken(int pos) {
        return arr[pos];
    }

    /**
     * 转换为10进制
     *
     * @param token
     * @return
     */
    public static int convertToInt(char token) {
        return tokenToIntMap.get(token);
    }

    /**
     * 将62进制转换为10进制的数组
     *
     * @param origin 62进制的字符串
     * @return
     */
    public static List<Integer> convertToInt(String origin) {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < origin.length(); ++i) {
            result.add(convertToInt(origin.charAt(i)));
        }
        return result;
    }

    /**
     * 将十进制转换为62进制的数组
     *
     * @param number
     * @return
     */
    public static List<Integer> convertToInt(int number) {
        List<Integer> result = new ArrayList<>();
        while (number > 0) {
            result.add(number % 64);
            number /= 64;
        }
        Collections.reverse(result);
        return result;
    }

    public static String convertToBase62(List<Integer> numbers) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numbers.size(); ++i) {
            sb.append(getToken(numbers.get(i)));
        }
        return sb.toString();
    }

    /**
     * 将两个62进制数组相加
     *
     * @param left  从高位到低位存储
     * @param right 从高位到低位存储
     * @return
     */
    public static List<Integer> add(List<Integer> left, List<Integer> right) {
        //转换为从低位到高位存储
        Collections.reverse(left);
        Collections.reverse(right);
        List<Integer> result = new ArrayList<>();
        int max = Math.max(left.size(), right.size());
        //进位
        int remain = 0;
        for (int i = 0; i < max; ++i) {
            int tmp = 0;
            if (i < left.size()) {
                tmp += left.get(i);
            }
            if (i < right.size()) {
                tmp += right.get(i);
            }
            tmp += remain;
            if (tmp >= BASE) {
                remain = 1;
            } else {
                remain = 0;
            }
            result.add(tmp % BASE);
        }
        if (remain > 0) {
            result.add(remain);
        }
        //转换为从高位到低位存储
        Collections.reverse(result);
        return result;
    }
}
