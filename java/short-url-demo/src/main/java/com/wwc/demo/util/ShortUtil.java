package com.wwc.demo.util;

import com.wwc.demo.constant.Constants;

import java.util.Random;

/**
 * @description：生成短码工具类
 * @date：2022年04月27日 20时09分51秒
 * @author：汪伟成
**/
public class ShortUtil {
    /**
     * 根据传入的codeLength确定生成几位的短码
     * @param codeLength
     * @return
     */
    public static String shortCode(int codeLength){
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"
        };
        //用来拼接字符串
        StringBuilder sb=new StringBuilder();
        //循环8次，随机取一个字符，组装作为短码
        for (int i = Constants.INDEX_0; i < codeLength; i++) {
            Random random = new Random();
            // 生成62以内随机数
            int j = random.nextInt(Constants.INDEX_62);
            // 取数组中第j位的字符
            sb.append(chars[j]);
        }
        return sb.toString();
    }
}
