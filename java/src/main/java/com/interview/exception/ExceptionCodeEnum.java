package com.interview.exception;

/**
 * 
 * @类名称   ExceptionCodeEnum.java
 * @类描述   <pre>异常枚举</pre>
 * @作者     zhangsheng
 * @创建时间 2021年3月21日下午9:43:40
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本           修改人 		    修改日期 		           修改内容描述
 *     ------------------------------------------------------
 *     1.00 	zhangsheng 	2021年3月21日下午9:43:40             
 *     ------------------------------------------------------
 * </pre>
 */
public enum ExceptionCodeEnum {
	
    /**
     * 压缩数超出最大范围
     */
	COMPRESS_NUM_TOO_LONG(10000, "压缩数超出最大范围！"),
	
	SHORT_URL_FORMAT_ERROR(10001, "短域名格式错误！")
	;
    
	//异常代码
    private final Integer code;

    //异常描述
    private final String message;

    ExceptionCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }


    /**
     * Return the integer code of this status code.
     */
    public Integer getCode() {
        return this.code;
    }

    /**
     * Return the reason phrase of this status code.
     */
    public String getMessage() {
        return this.message;
    }

}
