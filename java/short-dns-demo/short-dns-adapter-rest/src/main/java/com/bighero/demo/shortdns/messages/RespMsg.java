package com.bighero.demo.shortdns.messages;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回消息对象
 * 
 * @author bighero
 *
 * @param <T>
 */
@Builder
public class RespMsg<T> implements Serializable {

	private static final long serialVersionUID = 1L;
	public String sysMsg;
	public int sysCode;
	public String sysStatus;
	public String appMsg;
	public String appCode;
	public String appStatus;
	public T msgBody;

	/**
	 * @param sysMsg
	 * @param sysCode
	 * @param sysStatus
	 * @param appMsg
	 * @param appCode
	 * @param appStatus
	 * @param msgBody
	 */
	public RespMsg(String sysMsg, int sysCode, String sysStatus, String appMsg, String appCode, String appStatus,
			T msgBody) {
		super();
		this.sysMsg = sysMsg;
		this.sysCode = sysCode;
		this.sysStatus = sysStatus;
		this.appMsg = appMsg;
		this.appCode = appCode;
		this.appStatus = appStatus;
		this.msgBody = msgBody;
	}

	public RespMsg() {
		super();
	}

	public void setSysMsg(String sysMsg) {
		this.sysMsg = sysMsg;
	}

	public void setSysCode(int sysCode) {
		this.sysCode = sysCode;
	}

	public void setSysStatus(String sysStatus) {
		this.sysStatus = sysStatus;
	}

	public void setAppMsg(String appMsg) {
		this.appMsg = appMsg;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}

}
