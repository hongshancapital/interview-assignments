/**
 *
 */
package com.interview.shorter.service;

import java.util.Date;
/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 */
/**
 *
 * 操作接口定义
 *
 */
public interface Shorter {
	/**
	 *  根据类型 原url 置换成短码
	 * @param type
	 * @param source
	 * @return
	 */
	public String shorting(int type, String source);

	/**
	 * 判断一个短码是否存在
	 * @param id
	 * @return
	 */
	public boolean hasExist(String id);

	/**
	 * 获取一个别名
	 * @param id
	 * @return
	 */
	public Atlas restore(String id);

	/**
	 *
	 * @param id
	 * @return
	 */
	 Body replace(String id);

	/**
	 * 更新一个已经存在的短码的的原始url
	 * @param id
	 * @param body
	 * @return
	 */
	 int update(String id, String body);

	/**
	 * 原始url 抽象定义
	 */
	 interface Body{
		/**
		 * 获取转换的类型
		 * @return
		 */
		 int getType();

		/**
		 * 获取存储的原始url
		 * @return
		 */
		 String getContent();

		/**
		 * 重置body体
		 * @return
		 */
		Body reset();
	}

	/**
	 * 别名定义，为了之后的扩展需要
	 * 后期可能将短码或者别名存储到nosql
	 * 提供三个Hash运算比较实现，方便之后的扩展，之后三个值可能被持久化
	 */
	 interface Atlas extends Body{
		/**
		 * 提供唯一的短码作为ID
		 * @return
		 */
		String getId();

		/**
		 *
		 * @return
		 */
		 long getKey();
		 long getAux();
		 long getAux1();
		 boolean match(String source);
		 Date getCreateAt();
	}


}
