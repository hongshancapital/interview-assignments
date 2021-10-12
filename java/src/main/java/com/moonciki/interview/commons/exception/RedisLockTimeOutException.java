package com.moonciki.interview.commons.exception;

import com.moonciki.interview.commons.model.ResponseCode;
import com.moonciki.interview.commons.enums.ResponseEnum;

/**
 * 自定义异常集合
 */
public class RedisLockTimeOutException extends CustomException {

    private RedisLockTimeOutException(String msg) {
        super(msg);
    }

    private RedisLockTimeOutException(Exception e) {
        super(e);
    }

    /**
     * redis 超时异常
     * @return
     */
    public static RedisLockTimeOutException createException(){
        ResponseCode respCode = ResponseEnum.redis_timeout.info();

        String codeName = respCode.getCodeName();
        String dec = respCode.getDec();

        RedisLockTimeOutException re = new RedisLockTimeOutException(dec);

        re.initException(respCode, null);

        return re;
    }

}
