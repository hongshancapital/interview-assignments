package javaassignment.lcg.controller;

import javaassignment.lcg.constants.Constants;
import javaassignment.lcg.entity.Result;

/**
 * @Author: 栾晨光
 * @Date: 2021-03-18 18:55
 * @Email 10136547@qq.com
 * @Description: 基础控制器
 */
public class BaseController {

    /**
     * 封装成功
     * @return
     */
    public Result setResultSuccess() {
        return setResultSuccess(Constants.HTTP_RES_CODE_200, "请求成功", null);
    }

    /**
     * 封装成功
     * @param data
     * @return
     */
    public Result setResultSuccess(Object data) {
        return setResultSuccess(Constants.HTTP_RES_CODE_200, "请求成功", data);
    }

    /**
     * 失败
     * @param msg
     * @return
     */
    public Result setResultError(String msg) {
        Result r = new Result();

        r.setCode(Constants.HTTP_RES_CODE_500);
        r.setMessage(msg);

        return r;
    }

    /**
     * 自定义
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public Result setResultSuccess(Integer code, String msg, Object data) {
        Result r = new Result();

        r.setCode(code);
        r.setMessage(msg);
        if (data != null)
        {
            r.setData(data);
        }

        return r;
    }

    /**
     * 获得异常Result对象
     * @param code
     *      HTTP状态码
     * @param msg
     *      异常消息
     * @return
     */
    public Result setResultError(int code, String msg){
        Result r = new Result();
        r.setCode(code);
        r.setMessage(msg);
        return r;
    }

    /**
     * 获得异常Result对象
     * @param code
     *      HTTP状态码
     * @param msg
     *      异常消息
     * @param data
     *      堆栈信息
     * @return
     */
    public Result setResultError(int code, String msg, Object data){
        Result r = new Result();
        r.setCode(code);
        r.setMessage(msg);
        if (null != data){
            r.setData(data);
        }
        return r;
    }
}