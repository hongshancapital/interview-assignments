package com.example.service;

import javax.servlet.http.HttpServletRequest;

/**
 * token业务层接口
 * @author eric
 * @date 2021/7/21 22:52
 */
public interface TokenService {

    /**
     * 创建 token 并存入redis
     * @return String
     * @author eric
     */
    String createToken();

    /**
     * token验证
     * @param request       请求对象
     * @author eric
     */
    void checkToken(HttpServletRequest request);
}
