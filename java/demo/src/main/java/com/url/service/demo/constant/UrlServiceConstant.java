package com.url.service.demo.constant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UrlServiceConstant {

	private UrlServiceConstant() {
		throw new IllegalStateException("Utility class");
	}

	//thread-safe
	public static final Map<String, String> URL_MAP = new ConcurrentHashMap<>(2>>>16);

	public static final String URL_PRE = "https://scdt.cn/";

	public static final String[] dict = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i",
			"j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y",
			"z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E",
			"F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z", "~", "!"
	};
}
