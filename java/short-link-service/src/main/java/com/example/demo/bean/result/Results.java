package com.example.demo.bean.result;


/**
 * @author shenbing
 * @since 2022/1/8
 */
public class Results {

    public static <T> Result<T> paramError(String msg) {
        return fail("参数错误：" + msg);
    }

    public static <T> Result<T> fail() {
        return fail("失败");
    }

    public static <T> Result<T> fail(String msg) {
        return fail(null, msg);
    }

    public static <T> Result<T> fail(T t, String msg) {
        Result<T> result = new Result<>();
        result.setStatus(-1);
        result.setMsg(msg);
        result.setData(t);
        return result;
    }

    public static <T> Result<T> ok(T t) {
        return ok(t, "ok");
    }

    public static <T> Result<T> ok(T t, String msg) {
        Result<T> result = new Result<>();
        result.setStatus(0);
        result.setMsg(msg);
        result.setData(t);
        return result;
    }

}
