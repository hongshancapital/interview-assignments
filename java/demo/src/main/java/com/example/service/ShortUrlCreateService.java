package com.example.service;

import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  
 *  取号器方式的获取短地址。前62的8次方（281474976710656）次获取保证无冲突。
 *  取号满了之后会按照之前的取号顺序重新取。之前的短地址对应的长地址数据会被替换失效。
 *  无随机
 *  @author wenbin
 */
@Component
public class ShortUrlCreateService implements IShortUrlCreator {

	private static final char[] CHAR_SET = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e',
			'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
			'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U',
			'V', 'W', 'X', 'Y', 'Z' };
	
	private static long MAX_NUM = (long) Math.pow(62, 8);
	
	private long currentIndex = 0;

	/**
	 * 	<短地址对应的10进制数，原始地址>
	 */
	private ConcurrentHashMap<String, String> shortLongMap = new ConcurrentHashMap<>();

	/**
	 *自减拿数
 	 */
	public synchronized long getNumber() {
		if (currentIndex == 0) {
			currentIndex = MAX_NUM;
		}
		return --currentIndex;
	}

	public String to62(long nextInt) {
		StringBuffer sb = new StringBuffer();
		int length = 8;
		while (nextInt > 0 || length > 0) {
			int char_offset = (int)(nextInt % 62);
			sb.append(CHAR_SET[char_offset]);
			nextInt = nextInt / 62;
			length--;
		}
		return sb.toString();
	}

	@Override
	public String createShortUrl(String longUrl) {
		long number = getNumber();
		String to62 = to62(number);
		shortLongMap.put(to62, longUrl);
		return to62;
	}

	@Override
	public String getLongUrl(String shortUrl) {
		return shortLongMap.get(shortUrl);
	}

//	public static void main(String[] args) {
//		ShortUrlCreateService service = new ShortUrlCreateService();
//		for (int i =0;i<=1000;i++){
//			System.out.println(service.to62(i));
//		}
//	}
}
