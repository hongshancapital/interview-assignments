package com.skg.domain.demo.base;

/**
 * @Author smith skg
 * @Date 2021/10/11 16:41
 * @Version 1.0
 */
public interface IResultCode {
    /**
     * 返回码
     *
     * @return int
     */
    int getCode();

    /**
     * 返回消息
     *
     * @return String
     */
    String getMsg();
}
