package com.shorturl.util;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

/**
 * url 转换工具类
 * @author shaochengming
 * @date 2021/10/15
 */
public class UrlUtil {
	/**
     * 在进制表示中的字符集合，最大为64进制的符号表示
     */
    private static final char[] digits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a',
        'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
        't', 'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
        'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z','+','-'};
    
    public static String toNumberSystem62(long number, int seed)
    {
        if (number < 0)
        {
            number = ((long)2 * 0x7fffffff) + number + 2;
        }
        char[] buf = new char[32];
        int charPos = 32;
        while ((number / seed) > 0)
        {
            buf[--charPos] = digits[(int)(number % seed)];
            number /= seed;
        }
        buf[--charPos] = digits[(int)(number % seed)];
        return new String(buf, charPos, (32 - charPos));
    }
    
    /**
     * 雪花算法获取递增数字id
     */
    public static long getLongnumber(long machineId,long dataCenterId) {
    	Snowflake snowflake = IdUtil.createSnowflake(machineId,dataCenterId);
    	return snowflake.nextId();
    }

}
