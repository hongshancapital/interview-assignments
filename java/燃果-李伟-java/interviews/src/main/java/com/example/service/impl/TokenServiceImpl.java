package com.example.service.impl;

import com.example.bean.result.ResultRpcEnum;
import com.example.constants.Constants;
import com.example.exception.BusinessException;
import com.example.service.TokenService;
import com.example.util.JedisUtil;
import com.example.util.RandomUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * token 业务层接口实现
 * @author eric
 * @date 2021/7/21 22:52
 */
@Service("tokenService")
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    private JedisUtil jedisUtil;

    @Override
    public String createToken() {
        String uuid = RandomUtil.UUID32();
        StringBuilder token = new StringBuilder();
        token.append(Constants.Redis.TOKEN_PREFIX).append(uuid);
        // 存入redis
        jedisUtil.set(token.toString(), token.toString(), Constants.Redis.EXPIRE_TIME_MINUTE);
        // 返回token
        return token.toString();
    }

    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) { // header中不存在token
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) { // parameter中也不存在token
                throw new BusinessException(ResultRpcEnum.PARAM_EMPTY.getDesc());
            }
        }

        // 查看 redis 是否存在
        if (!jedisUtil.exists(token)) {
            throw new BusinessException(ResultRpcEnum.ERROR.getDesc());
        }

        // 查看 redis 是否存在
        Long del = jedisUtil.del(token);
        if (del <= 0) {
            throw new BusinessException(ResultRpcEnum.ERROR.getDesc());
        }
    }
}
