package com.bighero.demo.shortdns.domain.assmbler;

/**
 * DTO装配接口
 * @author bighero
 */
public interface IAssembly<T> {
	/**
	 * DTO装配服务接口
	 * @param newObj
	 * @return T
	 */
	public T toAssembly(T newObj);
	

}
