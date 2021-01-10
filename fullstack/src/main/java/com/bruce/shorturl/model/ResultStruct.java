package com.bruce.shorturl.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 响应对象
 * @author bruce
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultStruct<T extends Serializable > implements Serializable {


	/** 错误码 */
	private Integer errCode;
	/** 错误消息 */
	private String errMsg;
	/** 数据*/
	private T data;

	/**
	 * 判断是否是成功的响应
	 * @param resultStruct
	 * @return
	 */
	public static boolean isSuccess(ResultStruct resultStruct){
		boolean result = resultStruct!=null && resultStruct.getErrCode()==null;
		return result;
	}


	/**
	 * 构造成功响应
	 * @param data
	 * @return
	 */
	public static ResultStruct buildSucessResult(Serializable data){
		ResultStruct result = new ResultStruct();
		result.setData(data);
		return result;
	}


	/**
	 * 构造失败响应
	 * @param errCode
	 * @param errMsg
	 * @return
	 */
	public static ResultStruct buildErrorResult(int errCode, String errMsg){
		return buildErrorResult(errCode, errMsg, null);
	}

	/**
	 * 构造失败响应
	 * @param errCode
	 * @param errMsg
	 * @param data
	 * @return
	 */
	public static ResultStruct buildErrorResult(int errCode, String errMsg, Serializable data){
		ResultStruct result = new ResultStruct(errCode, errMsg, data);
		return result;
	}


}
