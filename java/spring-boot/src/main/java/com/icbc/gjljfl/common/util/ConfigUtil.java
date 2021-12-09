package com.icbc.gjljfl.common.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

//@PropertySource({"classpath:test.properties"})
@Component
public class ConfigUtil {

	@Autowired
	private static Environment env;
	/**
	 * 获取string类型的值
	 * @param key
	 * @return
	 */
	public static String getParamAsStr(String key) {
		try {
			String param = getRealMsg(env.getProperty(key));
			if (param == null || param.isEmpty()) {
				return null;
			}
			return param;
		} catch (Exception ex) {
			//commonLogger.error("getParamAsStr Error. key=" + key, ex);
			return null;
		}
	}

	/**
	 * 获取int类型的值
	 * @param key
	 * @return
	 */
	public static int getParamAsInt(String key) {
		String param = getRealMsg(env.getProperty(key));
		try {
			return Integer.parseInt(param);
		} catch (Exception ex) {
			//commonLogger.error("getParamAsInt Error! param=" + key + ". defaultValue=-1.", ex);
			return -1;
		}
	}
	
	@Autowired
	public void setEnvironment(Environment env) {
		ConfigUtil.env = env;
	}
	
	
	/**
	 * 得到真正错误信息
	 * @param pattern
	 * @return String
	 */
	private static String getRealMsg(String pattern) {
		int varNum = StringUtils.countMatches(pattern, "${");
		StringBuffer msgBuf = new StringBuffer();
		int startPos = 0;
		for(int i = 0; i < varNum; i++) {
			int prefixPos = getPrefixPos(pattern,startPos);
			int suffixPos = getSuffixPos(pattern,startPos);
			String prefixMsg = getPrefixMsg(pattern,startPos,prefixPos);
			msgBuf.append(prefixMsg);
			msgBuf.append(env.getProperty(pattern.substring(prefixPos+2, suffixPos)));
			startPos = suffixPos+1;
		}//end for
		String tailMsg = getTailMsg(pattern,startPos);
		msgBuf.append(tailMsg);
		return msgBuf.toString();
	}
	
	/**
	 * 从start开始得到pattern开始索引值
	 * @param pattern
	 * @param start
	 * @return int  索引值
	 */
	private static int getPrefixPos(String pattern,int start) {
		return pattern.indexOf("${", start);
	}
	
	/**
	 * 从start开始得到pattern结束索引值
	 * @param pattern
	 * @param start
	 * @return int  索引值
	 */
	private static int getSuffixPos(String pattern,int start) {
		return pattern.indexOf("}", start);
	}
	
	/**
	 * 得到pattern之前字符串
	 * @param pattern
	 * @param start   起始位置
	 * @param prefix  pattern起始索引
	 * @return String
	 */
	private static String getPrefixMsg(String pattern,int start,int prefix) {
		return pattern.substring(start, prefix);
	}
	
	/**
	 * 得到pattern之后字符串
	 * @param pattern
	 * @param start   起始位置
	 * @return String
	 */
	private static String getTailMsg(String pattern,int start) {
		return pattern.substring(start);
	}
	
	
	
	public static void main(String[] args) {
		String pattern = "abd${1}fds${2}fdfdf${3}";
		
		//ConfigUtil util = new ConfigUtil();
		//System.out.println(util.getRealMsg(pattern));

		System.out.println(ConfigUtil.getParamAsStr("name"));
	}
}
