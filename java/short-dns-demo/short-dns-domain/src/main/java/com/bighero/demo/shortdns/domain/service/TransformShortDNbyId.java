package com.bighero.demo.shortdns.domain.service;

import java.util.Stack;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

import com.bighero.demo.shortdns.domain.entity.LongDN;
import com.bighero.demo.shortdns.domain.entity.ShortDN;

import lombok.extern.slf4j.Slf4j;
/**
 * 自增id计算策略
 * @author bighero
 *
 */
@Slf4j
@Service("byID")
public class TransformShortDNbyId implements ITransformShortDN {
	public final static AtomicLong number=new AtomicLong(1);
	
	// 打乱编码,必须使用本集合进行解码
	public static final char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h',
			'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '8', '5', '2', '7', '3', '6', '4', '0', '9', '1', 'Q',
			'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C',
			'V', 'B', 'N', 'M', '+', '-' };

	/**
	 * 编码,从10进制转换到64进制
	 * @param number long类型的10进制数,该数必须大于0
	 * @return string类型的64进制数
	 */
	private static String encode(long number) {
		Long rest = number;
		Stack<Character> stack = new Stack<Character>();
		StringBuilder result = new StringBuilder(0);
		while (rest >= 1) {
			stack.add(array[new Long(rest % 64).intValue()]);
			rest = rest / 64;
		}
		for (; !stack.isEmpty();) {
			// 出栈
			result.append(stack.pop());
		}
		return result.toString();

	}
	
	/**
	 * 计算短域名
	 */
	@Override
	public ShortDN transform(LongDN longDN) {
		log.info("transform by id!");
		String path=encode(number.getAndIncrement());
		ShortDN shortDN = new ShortDN(path);
		return shortDN;
	}

}
