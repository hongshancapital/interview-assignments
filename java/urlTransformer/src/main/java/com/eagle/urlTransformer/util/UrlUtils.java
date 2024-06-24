package com.eagle.urlTransformer.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** 
 * @ClassName: UtlUtils 
 * @Description: url工具类
 * @author Eagle
 * @date 2022年1月18日 上午11:31:17 
 *  
 */
public class UrlUtils {
	/**
     * 判断是否为标准url字符串
     * @param urls
     * @return
     */
    public static boolean isValidUrl(String urls) {
    	//url正则匹配表达式
        String regex = "^(?:https?://)?[\\w]{1,}(?:\\.?[\\w]{1,})+[\\w-_/?&=#%:]*$";
        Pattern pattern = Pattern.compile(regex.trim());
        Matcher matcher = pattern.matcher(urls.trim());
        //判断是否匹配
        return matcher.matches();
    }
    
    public static void main(String[] args) {
		String test = "https://q.W/jadpfj/ab?name=milan";
		System.out.println(UrlUtils.isValidUrl(test));
	}
    
    
}
