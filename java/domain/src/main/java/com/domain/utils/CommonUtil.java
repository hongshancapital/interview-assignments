package com.domain.utils;

import java.security.SecureRandom;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * @author ：ji
 * @date ：2021/4/3
 * @description：公共常用方法
 */
public class CommonUtil {
    /**
     * 验证 字符串 是否是URL
     *
     * @param url
     * @return
     */
    public static boolean isURL(String url){
        if (url == null){
            return false;
        }
        String regex = "^(http(s)?://)?([\\w-]+\\.)+[\\w-]+(/[\\w-./?%&=]*)?" ;
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(url).matches();
    }

    /**
     * 验证 是否为数字
     *
     * @param number
     * @return
     */
    public static boolean isNumber(String number){
        if (number == null){
            return false;
        }
        //  位数字
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(number).matches();
    }


    /**
     * 获取安全随机数
     * @param len
     * @return
     */
	public static int getSecureRandom(int len) {
		SecureRandom random = new SecureRandom();
		int nextInt = random.nextInt(len);
		return nextInt;
	}

    /**
     * 获得一个UUID字符串，去-线
     * @return
     */
    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","");
    }

}
