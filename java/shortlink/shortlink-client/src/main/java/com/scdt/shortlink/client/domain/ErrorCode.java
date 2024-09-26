package com.scdt.shortlink.client.domain;

/**
 * @Author tzf
 * @Date 2022/4/30
 */
public interface ErrorCode {

    /**
     * 获取错误码
     * @return
     */
    String getErrorCode();

    /**
     * 获取错误信息
     * @return
     */
    String getErrorMessgae();
}
