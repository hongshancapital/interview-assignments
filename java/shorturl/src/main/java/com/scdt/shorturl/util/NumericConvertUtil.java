package com.scdt.shorturl.util;

/**
 * 进制转换工具，最大支持十进制和62进制的转换
 * 1、将十进制的数字转换为指定进制的字符串；
 * 2、将其它进制的数字（字符串形式）转换为十进制的数字
 */
public class NumericConvertUtil{
	/**
	 * 在进制表示中的字符集合，0-Z分别用于表示最大为62进制的符号
	 */
	public static final char[]digits={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};

	/**
	 * 将十进制的数字转换为指定进制的字符串
	 * @param number 十进制的数字
	 * @param seed   指定的进制
	 * @return 指定进制的字符串
	 */
	public static String toOtherNumber(Long number,int seed){
		if(number<0)number=((long)2*0x7fffffff)+number+2;
		char[]buf=new char[32];
		int charPos=32;
		while((number/seed)>0){
			buf[--charPos]=digits[(int)(number%seed)];
			number/=seed;
		}
		buf[--charPos]=digits[(int)(number%seed)];
		return new String(buf,charPos,(32-charPos));
	}
	
	public static String toOtherNumber(Long number){
		return toOtherNumber(number,digits.length);
	}

	/**
	 * 将其它进制的数字（字符串形式）转换为十进制的数字
	 * @param value 其它进制的数字（字符串形式）
	 * @param seed   指定的进制，也就是参数str的原始进制
	 * @return 十进制的数字
	 */
	public static Long toDecimalNumber(String value,int seed){
		char[]charBuf=value.toCharArray();
		if(seed==10)return Long.parseLong(value);
		long result=0,base=1;
		for(int i=charBuf.length-1;i>=0;i--){
			int index=0;
			for(int j=0,length=digits.length;j<length;j++)if(digits[j]==charBuf[i])index=j;//找到对应字符的下标，对应的下标才是具体的数值
			result+=index*base;
			base*=seed;
		}
		return result;
	}
	
	public static Long toDecimalNumber(String value){
		return toDecimalNumber(value,digits.length);
	}
}