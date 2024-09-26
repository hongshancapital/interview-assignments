package org.example.shorturl.enums;

/**
 * 返回结果代码的interface 系统错误码可继承该接口,保持统一
 *
 * @author bai
 * @date 2021/9/6 8:44
 */
public interface IResultCode {
    /**
     * 获取代码
     *
     * @return {@link Integer}
     */
    Integer getCode();
    
    /**
     * 获得应用程序代码
     *
     * @return {@link String}
     */
    String getAppCode();
    
    /**
     * 得到消息
     *
     * @return {@link String}
     */
    String getMessage();
}
