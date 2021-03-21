package com.interview.exception;

/**
 * 
 * @类名称   com.interview.exception.BizException.java
 * @类描述   <pre>自定义异常类</pre>
 * @作者     zhangsheng
 * @创建时间 2021年3月21日下午9:42:17
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本           修改人 		    修改日期 		           修改内容描述
 *     ------------------------------------------------------
 *     1.00 	zhangsheng 	2021年3月21日下午9:42:17             
 *     ------------------------------------------------------
 * </pre>
 */
public class BizException extends RuntimeException
{
	//错误码
	private int errorCode;
	//错误消息
	private String errorMsg;
  
	private static final long serialVersionUID = 1L;
  
	public BizException()
	{
		super("unfindedBizError");
		errorCode = 99999;
		errorMsg = "unfindedBizError";
	}

	public BizException(int errorCode, String errorMsg) {
		super(errorMsg);
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}
  
	public int getErrorCode()
	{
		return errorCode;
	}
  
	public String getErrorMsg() {
		return errorMsg;
	}
}
