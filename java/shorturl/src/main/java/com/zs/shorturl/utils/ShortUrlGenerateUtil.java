package com.zs.shorturl.utils;

import ch.qos.logback.core.rolling.DefaultTimeBasedFileNamingAndTriggeringPolicy;
import org.springframework.util.StringUtils;

import java.util.HashSet;
import java.util.Random;

/**
 * 短链接生成器
 * 根据一个自增的id，转换成自定义62进制的字符串，生成8位字符串
 *
 * @author zs
 * @date 2021/5/10
 */
public class ShortUrlGenerateUtil {

    /**
     * 自定义62进制表示  元素顺序可自定义
     */
    private static final char[] CANDIDATE_ARRAY = {
            'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S',
            'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B',
            'N', 'M', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's',
            'n', 'm'
    };

    /**
     *  补位字符串
     */
    private static final char[] FILL_ARRAY = {
            'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b',
    };


    /**
     *  进制
     */
    private static final int BASE_NUM = CANDIDATE_ARRAY.length;


    /** 短链接字符串长度 */
    private static final int SHORT_URL_LEGTH = 8;



    /** 短链接域名前缀*/
    public static final String SHORT_URL_PRE = "http://www.zs.com/";


    /**
     * 获到8位字符串
     * @param id  自增id
     * @return
     */
    public static String getSerialCode(long id){

        char[] buf=new char[32];
        int charPos=32;

        while((id / BASE_NUM) > 0) {
            int ind=(int)(id % BASE_NUM);
            buf[--charPos]=CANDIDATE_ARRAY[ind];
            id /= BASE_NUM;
        }
        buf[--charPos]=CANDIDATE_ARRAY[(int)(id % BASE_NUM)];
        String str=new String(buf, charPos, (32 - charPos));
        // 不够长度的自动随机补全
        if(str.length() < SHORT_URL_LEGTH) {
            StringBuilder sb=new StringBuilder();
            Random rnd=new Random();
            for(int i=0; i < SHORT_URL_LEGTH - str.length(); i++) {
                sb.append(FILL_ARRAY[rnd.nextInt(FILL_ARRAY.length)]);
            }
            str += sb.toString();
        }
        return str;
    }


    /**
     * 获取完整的短链接
     * @param serialCode
     * @return
     */
    public static String getCompleteShortUrl(String serialCode){
        return SHORT_URL_PRE + serialCode;
    }



}
