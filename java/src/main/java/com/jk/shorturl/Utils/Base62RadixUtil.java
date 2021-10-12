package com.jk.shorturl.Utils;

import java.util.Stack;

/**
 * @author Jiang Jikun
 * @decription 自定义62进制转换
 */
public class Base62RadixUtil {

    /**
     * 62进制定义：[a-z][A-Z][0-9] 打乱改字符数组的组合顺序，可以得到不同的转换结果
     */
    private static final char[] array = {'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g',
            'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '8', '5', '2', '7', '3', '6', '4', '0', '9', '1',
            'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X',
            'C', 'V', 'B', 'N', 'M'};

    /**
     * 10进制转62进制
     *
     * @param number double类型的10进制数,该数必须大于0
     */
    public static String to62RadixString(Long number) {
        double rest = number;
        // 创建栈
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest >= 1) {
            // 进栈,对  array.length 求余数，转换进制，然后取整继续去下一位。
            stack.add(array[new Double(rest % array.length).intValue()]);
            rest = rest / array.length;
        }
        for (; !stack.isEmpty(); ) {
            // 出栈
            result.append(stack.pop());
        }
        return result.toString();

    }

}
