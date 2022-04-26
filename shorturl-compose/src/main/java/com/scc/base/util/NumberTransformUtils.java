package com.scc.base.util;

import java.util.HashMap;
import java.util.Stack;

/**
 * @author renyunyi
 * @date 2022/4/25 1:42 PM
 * @description counting method convert
 **/
public class NumberTransformUtils {

    private static final char[] char62 =
            {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
             'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
             'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
             'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
             'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', };

    private static final HashMap<Character, Integer> base62map = new HashMap<>();

    static {
        //optimize search, and make code beautiful
        for (int i = 0; i < char62.length; i ++){
            base62map.put(char62[i], i);
        }
    }


    /**
     * transfer decimal number to base 62 number
     * @return base 62 number string
     */
    public static String decimalToBase62(long decimal){

        Stack<Character> stack = new Stack<>();
        long div = decimal ;
        while (div > 0){
            long remain = div % 62;
            stack.push(char62[Integer.parseInt(String.valueOf(remain))]);
            div = div / 62;
        }

        StringBuilder sb = new StringBuilder();
        while (stack.size() > 0){
            sb.append(stack.pop());
        }

        String res = sb.toString();
        return res.length() == 0 ? "0" : res;
    }

    /**
     * transfer base 62 number to decimal number
     * @param base62 base 62
     * @return decimal
     */
    public static long base62ToDecimal(String base62){
        long decimal = 0;
        for (int i = base62.length() - 1, j = 0; i >= 0; i --, j++ ){
            decimal += base62map.get(base62.charAt(i)) * Math.pow(62, j);
        }
        return decimal;
    }

    /**
     * decimal to binary
     * @param decimal decimal
     * @return binary string
     */
    public static String decimalToBinary(long decimal){

        Stack<Long> stack = new Stack<>();
        long div = decimal;
        long remain;
        while (div > 0){
            remain = div % 2;
            stack.push(remain);
            div = div / 2;
        }

        StringBuilder sb = new StringBuilder();
        while (stack.size() > 0){
            sb.append(stack.pop());
        }

        String res = sb.toString();
        return res.length() == 0 ? "0" : res;
    }

    public static boolean isBase62(String base62){

        for (int i = 0; i < base62.length(); i ++){
            if (!base62map.containsKey(base62.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
