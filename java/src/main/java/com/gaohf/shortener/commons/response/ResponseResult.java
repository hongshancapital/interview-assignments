package com.gaohf.shortener.commons.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;

/**
 * @author: Gaohf
 * @create: 2021-05-11 18:01:39
 **/
@Data
public class ResponseResult implements Serializable {


    private static final long serialVersionUID = -5933929454580904046L;

    public static final String SUCCESS_CODE = "20000";
    public static final String FAIL_CODE = "20002";
    public static final String SUCCESS_MSG = "success";
    public static final String FAIL_MSG = "fail";


    /**
     * 状态码
     */
    private String code;

    /**
     * 消息
     */
    private String msg;

    /**
     * 异常
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String throwable;

    /**
     * 需要返回的数据对象
     */
    private Object data;

    public ResponseResult() {
        super();
    }

    public ResponseResult(String code, String mesg, Object data) {
        this.code = code;
        this.msg = mesg;
        this.data = data;
    }



    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static ResponseResult success(Object data) {
        return new ResponseResult(SUCCESS_CODE, SUCCESS_MSG, data);
    }


    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static ResponseResult success() {
        return success(null);
    }

    /**
     * 系统异常类有异常信息
     *
     * @return Result
     */
    public static ResponseResult fail(String failMsg) {
        if (!StringUtils.hasLength(failMsg)){
            failMsg = FAIL_MSG;
        }
        return new ResponseResult(FAIL_CODE,failMsg,null);
    }

    /**
     * 快速创建失败对象
     * @return
     */
    public static ResponseResult fail(){
        return new ResponseResult(FAIL_CODE,FAIL_MSG,null);
    }





    /**
     * 成功code=RX0000
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.code);
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }

}