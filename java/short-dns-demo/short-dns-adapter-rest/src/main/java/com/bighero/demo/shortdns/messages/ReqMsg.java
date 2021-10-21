package com.bighero.demo.shortdns.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 请求消息对象
 * 
 * @author bighero
 *
 * @param <T>
 */
@NoArgsConstructor
public class ReqMsg<T> {
	public T data;
}
