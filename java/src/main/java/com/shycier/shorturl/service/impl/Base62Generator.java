package com.shycier.shorturl.service.impl;

import com.shycier.shorturl.service.Generator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 基于自增序列和Base62编码的短域名生成器
 */
@Component
public class Base62Generator implements Generator {

	/**
	 * 字符串存储62进制字符
	 */
	private static final String BASE62_STRING = "0123456789qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";

	/**
	 * 自增计数器
	 * 考虑并发安全使用AtomicLong
	 */
	private final AtomicLong counter = new AtomicLong(0);

	@Override
	public String generate() {
		return toBase62(counter.incrementAndGet());
	}

	/**
	 * 10进制 转 62进制
	 * @param source 10进制数
	 * @return 62进制数
	 */
	private String toBase62(long source) {
		StringBuilder sBuilder = new StringBuilder();
		do {
			int remainder = (int) (source % 62);
			sBuilder.append(BASE62_STRING.charAt(remainder));
			source = source / 62;
		} while (source != 0);
		return sBuilder.reverse().toString();
	}

}
