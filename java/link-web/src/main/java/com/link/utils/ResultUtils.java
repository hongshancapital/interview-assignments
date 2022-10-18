package com.link.utils;

import java.util.HashMap;
import java.util.Map;

public class ResultUtils {
	private static int CODE_OK = 0;
	private static int CODE_ERROR = -1;
	
	public static Map<String, Object> successData(Object data) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", CODE_OK);
		result.put("data", data);
		return result;
	}
	
	public static Map<String, Object> fail(String message) {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("code", CODE_ERROR);
		result.put("message", message);
		return result;
	}
}
