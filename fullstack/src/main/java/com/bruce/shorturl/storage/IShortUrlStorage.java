package com.bruce.shorturl.storage;

/**
 * 短域名接口
 * @author bruce
 */
public interface IShortUrlStorage {

	/**
	 * 判断hash是否存在
	 * @param hash
	 * @return
	 */
	boolean notExists(String hash);

	/**
	 * 根据hash加载value
	 * @param hash
	 * @return
	 */
	String loadValueByHash(String hash);

	/**
	 * 设置value
	 * @param hash
	 * @param value
	 * @return
	 */
	String putValueByHash(String hash, String value);


	/**
	 * 清除
	 */
	void remove(String hash);

	/**
	 * 清除全部
	 */
	void clearAll();
}
