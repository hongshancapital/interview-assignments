package com.zhangliang.suril.util;

import com.zhangliang.suril.controller.view.BaseResult;

/**
 * 控制器返回帮助类
 *
 * @author zhang
 * @date 2021/12/02
 */
public class ResultUtil {

    /**
     * 成功
     *
     * @param code 代码
     * @param value 价值
     * @return {@link BaseResult}<{@link T}>
     */
    public static <T> BaseResult<T> success(Integer code, T value) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(code);
        result.setMsg(null);
        result.setPlayLoad(value);
        return result;
    }

    /**
     * 错误
     *
     * @param code 代码
     * @param msg 味精
     * @return {@link BaseResult}<{@link T}>
     */
    public static <T> BaseResult<T> error(Integer code, String msg) {
        BaseResult<T> result = new BaseResult<>();
        result.setCode(code);
        result.setMsg(null);
        return result;
    }
}
