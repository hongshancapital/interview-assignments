package com.wb.shorturl.common.exception;

/**
 * 错误码接口
 *
 * @author bing.wang
 * @date 2021/1/8
 */
public interface ErrorCode {

    /**
     * 获取 code
     * @return the code
     */
    Integer getCode();

    /**
     * 获取 message
     * @return the message
     */
    String getMessage();
}
