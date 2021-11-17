package com.icbc.gjljfl.util;

import com.icbc.gjljfl.common.ResponseEntity;
import com.icbc.gjljfl.common.enums.ErrorEnum;

public class ResponseUtils {
    /**
     * 响应成功的信息:  无结果
     *
     * @param code 响应码
     * @param msg  响应信息
     * @return
     */
    public static ResponseEntity toSuccess(String code, String msg) {
        return common(code, msg, null);
    }

    /**
     * 响应成功的信息:  无结果
     *
     * @return
     */
    public static ResponseEntity toSuccess() {
        return common(ErrorEnum.SUCCESS, null);
    }

    /**
     * 响应成功的信息:  无结果
     *
     * @return
     */
    public static ResponseEntity toSuccess(ErrorEnum errorEnum) {
        return common(errorEnum, null);
    }

    /**
     * 响应成功的信息:  有结果
     *
     * @param code 响应码
     * @param msg  响应信息
     * @param obj  响应结果数据
     * @return
     */
    public static ResponseEntity toSuccess(String code, String msg, Object obj) {
        return common(code, msg, obj);
    }

    /**
     * 响应成功的信息:  有结果
     *
     * @param obj 响应结果数据
     * @return
     */
    public static ResponseEntity toSuccess(ErrorEnum errorEnum, Object obj) {
        return common(errorEnum, obj);
    }

    /**
     * 响应成功的信息:  有结果
     *
     * @param obj 响应结果数据
     * @return
     */
    public static ResponseEntity toSuccess(String msg, Object obj) {
        return new ResponseEntity("0", msg, obj);
    }

    /**
     * 响应成功的信息:  有结果
     *
     * @param obj 响应结果数据
     * @return
     */
    public static ResponseEntity toSuccess(Object obj) {
        return common(ErrorEnum.SUCCESS, obj);
    }

    /**
     * 响应失败或者异常的信息
     *
     * @param code 响应码
     * @param msg  响应信息
     * @return
     */
    public static ResponseEntity toFail(String code, String msg) {
        return common(code, msg, null);
    }

    /**
     * 响应失败或者异常的信息
     *
     * @return
     */
    public static ResponseEntity toFail(ErrorEnum errorEnum) {
        return common(errorEnum, null);
    }

    /**
     * 响应失败或者异常的信息
     *
     * @return
     */
    public static ResponseEntity toFail() {
        return common(ErrorEnum.FAIL, null);
    }

    /**
     * 响应失败或者异常的信息
     *
     * @return
     */
    public static ResponseEntity toFail(String msg) {
        return common(ErrorEnum.FAIL.getInnerCode(), msg, null);
    }

    /**
     * 公共方法
     *
     * @param code
     * @param msg
     * @param obj
     * @return
     */
    private static ResponseEntity common(String code, String msg, Object obj) {
        ResponseEntity responseEntity = new ResponseEntity();
        return constructorObject(responseEntity, code, msg, obj);
    }

    /**
     * 公共方法
     *
     * @param errorEnum 枚举数值
     * @param obj       响应的数据
     * @return
     */

    private static ResponseEntity common(ErrorEnum errorEnum, Object obj) {
        ResponseEntity responseEntity = new ResponseEntity();
        String code = errorEnum.getOutterCode();
        String msg = errorEnum.getOutterMsg();
        return constructorObject(responseEntity, code, msg, obj);
    }

    private static ResponseEntity constructorObject(ResponseEntity responseEntity, String code, String msg, Object obj) {
        responseEntity.setCode(code);
        responseEntity.setMsg(msg);
        if (null != obj) {
            responseEntity.setResult(obj);
        }
        return responseEntity;
    }
}
