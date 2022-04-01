package com.zz.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 通过编码生成相应的短码
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public class ConvertToShortCode {

    /**
     * 给定10进制数据，生成62进制短码
     *
     * @param value 10进制数
     * @return 返回的是62位的各个进制的位数
     */
    public static List<Integer> digitTo62Code(long value) {
        return digitToBase(value, Constants.CODE_DIGIT_62);
    }

    /**
     * 任意10进制，转换成base进制
     * 使用整数取余方法
     *
     * @param value
     * @param base
     * @return
     */
    private static List<Integer> digitToBase(long value, int base) {
        List<Integer> res = new ArrayList<>();
        if (value == 0) {
            res.add(0);
            return res;
        }
        while (value != 0) {
            int remain = (int) (value % base);
            value = value / base;
            res.add(0, remain);
        }
        return res;
    }
}
