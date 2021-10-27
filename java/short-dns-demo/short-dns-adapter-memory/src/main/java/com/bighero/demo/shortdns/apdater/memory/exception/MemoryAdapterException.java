package com.bighero.demo.shortdns.apdater.memory.exception;

import com.bighero.demo.shortdns.domain.exception.AdapterException;

/**
 * 基础设施层-内存适配器服务自定义异常
 * @author Administrator
 *
 */

public class MemoryAdapterException extends AdapterException {

	public MemoryAdapterException(String code, String msg) {
		super(code, msg);
	}
 
}
