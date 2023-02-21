package com.scdt.tinyurl.util;

import com.scdt.tinyurl.common.ErrorCode;
import com.scdt.tinyurl.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class CodecUtil {

    private CodecUtil() {
    }

    private static final int BASE_62 = 62;


    /**
     * 十进制数字转62进制字符串
     *
     * @param val    十进制数字
     * @param dict   转换编码表
     * @param length 编码长度
     * @return 62进制字符串
     */
    public static String encode62(long val, String dict, int length) {
        final long maxValue = (long) Math.pow(BASE_62, length);
        if (val < 0 || val > maxValue) {
            log.error("Invalid Param: {}", val);
            throw new GlobalException(ErrorCode.ILLEGAL_REQUEST_PARAM);
        }
        StringBuilder sb = new StringBuilder();
        while (Math.abs(val) > BASE_62 - 1) {
            int index = Long.valueOf(val % BASE_62).intValue();
            sb.append(dict.charAt(index));
            val = val / BASE_62;
        }
        String result = sb.append(dict.charAt(Long.valueOf(val).intValue())).reverse().toString();
        //左填充长度至length
        return StringUtils.leftPad(result,length, String.valueOf(dict.charAt(0)));

    }


    /**
     * 62进制数字转十进制字符串
     *
     * @param val  62进制字符串
     * @param dict 转换编码表
     * @return 十进制数字符串
     */
    public static String decode62(String val, String dict, int maxIdLength) {

        if (val == null) {
            throw new GlobalException(ErrorCode.ILLEGAL_REQUEST_PARAM);
        }

        long num = 0;
        for (int i = 0; i < val.length(); i++) {
            int index = dict.indexOf(val.charAt(i));
            num += (long) (index * Math.pow(BASE_62, val.length() - i - 1));
        }
        String decodedString = String.valueOf(num);

        //小于maxLength时左填充，等于maxLength直接返回，大于maxLength抛出异常
        if (decodedString.length() < maxIdLength) {
            return StringUtils.leftPad(decodedString, maxIdLength, "0");
        } else if(decodedString.length() == maxIdLength) {
            return decodedString;
        } else {
            log.error("invalid id: {}",decodedString);
            throw new GlobalException(ErrorCode.INVALID_ID_ERROR);
        }

    }


}
