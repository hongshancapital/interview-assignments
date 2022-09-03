package com.url.service.demo.util;

import com.url.service.demo.constant.UrlServiceConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ThreadLocalRandom;

public class AlgorithmUtil {

	private static final Logger logger = LoggerFactory.getLogger(AlgorithmUtil.class);

	private AlgorithmUtil() {
		throw new IllegalStateException("Utility class");
	}


	public static String[] md5Url(String src) {
		String[] urlArr = new String[4];
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(src.getBytes());
			String digest = DatatypeConverter.printHexBinary(md.digest());
			// 分为4组
			for (int i = 0; i < 4; i++) {
				String subStr = digest.substring(i * 8, i * 8 + 8);
				// 16进制转10进制
				long dec = Long.parseLong(subStr, 16);
				StringBuilder shortUrl = new StringBuilder();
				for (int j = 0; j < 8; j++) {
					long idx = dec & (UrlServiceConstant.dict.length - 1);
					shortUrl.append(UrlServiceConstant.dict[(int) idx]);
					// 一共32位, 每次右移4位, 8次对应取8个字符
					dec = dec >> 4;
				}
				urlArr[i] = shortUrl.toString();
			}
			return urlArr;
		} catch (NoSuchAlgorithmException e) {
			logger.error("Exception in md5: ", e);
		}
		return urlArr;
	}

	public static String randomUrl() {
		StringBuilder url = new StringBuilder();
		//多线程竞争激烈时更好的表现
		ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int i = 0; i < 8; i++) {
			url.append(UrlServiceConstant.dict[random.nextInt(61)]);
		}
		return url.toString();
	}
}
