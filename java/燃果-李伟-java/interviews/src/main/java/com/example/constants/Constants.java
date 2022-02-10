package com.example.constants;

/**
 * 系统常量
 * @author eric
 * @date 2021/7/21 22:15
 */
public class Constants {

    /**
     * 日志类型
     * @author eric
     */
    public interface LogType {
        Integer LOGIN = 1;// 登录
        Integer LOGOUT = 2;// 登出
    }

    /**
     * redis常量状态
     * @author eric
     */
    public interface Redis {
        Integer EXPIRE_TIME_MINUTE = 60;// 过期时间, 60s, 一分钟
        String TOKEN_PREFIX = "token:";// token key
        String ACCESS_LIMIT_PREFIX = "accessLimit:"; // 接口限流前缀
    }
}
