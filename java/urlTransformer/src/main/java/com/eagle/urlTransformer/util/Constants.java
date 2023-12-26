package com.eagle.urlTransformer.util;


/** 常量类 
 * @ClassName: Constants 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @author Eagle
 * @date 2022年1月18日 下午6:29:35 
 *  
 */
public class Constants {
	
	
	public static final String[] alphabets = new String[] { "a" , "b" , "c" , "d" , "e" , "f" , "g" , "h" ,
			   "i" , "j" , "k" , "l" , "m" , "n" , "o" , "p" , "q" , "r" , "s" , "t" ,
			   "u" , "v" , "w" , "x" , "y" , "z" , "0" , "1" , "2" , "3" , "4" , "5" ,
			   "6" , "7" , "8" , "9" , "A" , "B" , "C" , "D" , "E" , "F" , "G" , "H" ,
			   "I" , "J" , "K" , "L" , "M" , "N" , "O" , "P" , "Q" , "R" , "S" , "T" ,
			   "U" , "V" , "W" , "X" , "Y" , "Z"
			  };
	public static final String NULL_STR="";
	
	public static final int ALPHABETS_LENGTH = alphabets.length;
	
	public static final int SHORT_URL_MAX__LENGTH = 8;
	
	//兜底重试次数
	public static final int MAX_REGEN = 10 ;
	
	public static final int URL_CACHE_SIZE = 1000000;
	
	public static final String CACHE_NEW_PUT = "@NEW@";
	
	/** 
	 * 循环从缓存中去取值 ，最大循环次数
	 */ 
	public static final int MAX_LOOP_NUM = 100 ;
}
