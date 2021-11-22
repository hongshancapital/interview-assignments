package com.liukun.shortdomain.model;

import lombok.Data;
import lombok.ToString;

/**
 * <p>
 * <b>Class name</b>:
 * </p>
 * <p>
 * <b>Class description</b>: Class description goes here.
 * </p>
 * <p>
 * <b>Author</b>: kunliu
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 2021/10/6       kunliu        1.0          Initial Creation
 *
 * </pre>
 *
 * @author kunliu
 * @date 2021/10/6
 * </p>
 */
@ToString
@Data
public class ResponseResult<T> {

    private Integer status;
    private String message;
    private T result;

    public ResponseResult() {
    }

    public static ResponseResult failure(String msg) {
        ResponseResult resp = new ResponseResult();
        resp.status = 0001;
        resp.setMessage(msg);
        return resp;
    }


    public static ResponseResult success() {
        ResponseResult resp = new ResponseResult();
        resp.status = 0000;
        resp.setMessage("执行成功");
        return resp;
    }

    public static <K> ResponseResult<K> success(K t) {
        ResponseResult<K> resp = new ResponseResult<>();
        resp.status = 0000;
        resp.setMessage("执行成功");
        resp.result = t;

        return resp;
    }

}
