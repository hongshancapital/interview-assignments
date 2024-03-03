package com.tb.link.client.domain;

import com.tb.link.client.domain.enums.ShortLinkErrorCodeEnum;

import java.io.Serializable;

/**
 *
 * @author andy.lhc
 * @date 2022/4/16 12:53
 */
public class Result<T> implements Serializable {

    /**
     * data
     */
    private T  data ;

    /**
     * errorCode
     */
    private String errorCode ;

    /**
     * errorMsg
     */
    private String errorMsg ;

    /**
     * 状态
     */
    private boolean status ;

    public Result(T data ,boolean status,String errorCode,String errorMsg) {
        this.data = data ;
        this.status = status ;
        this.errorCode = errorCode ;
        this.errorMsg = errorMsg ;
    }

    public Result(T data) {
        this(data,true,null,null );
    }

    public Result(String errorCode,String errorMsg) {
        this(null,false,errorCode,errorMsg );
    }



    public static <T> Result<T> success(T data){
        Result<T> result = new Result<T>(data);
        return result;
    }

    public static <T> Result<T> success(){
        Result<T> result = new Result<T>(null);
        return result;
    }

    public static <T> Result<T>  fail(String errorCode,String errorMsg){
        Result<T> result = new Result<T>(errorCode,errorMsg);
        return result ;
    }
    public static <T> Result<T>  fail(ShortLinkErrorCodeEnum errorCodeEnum){
        Result<T> result = new Result<T>(errorCodeEnum.getErrorCode(),errorCodeEnum.getErrorMsg());
        return result ;
    }



    public T getData() {
        return data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public Boolean isSuccess(){
        return status ;
    }

}
