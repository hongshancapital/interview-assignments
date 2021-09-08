package com.moonciki.interview.commons.exception;

import com.moonciki.interview.commons.model.ResponseCode;
import com.moonciki.interview.commons.enums.ResponseEnum;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * 自定义异常集合
 */
public class CustomException extends RuntimeException {
    @Getter
    @Setter
    private String errorCode;
    @Getter
    @Setter
    private String errorMsg;

    protected CustomException(String msg) {
        super(msg);
    }

    protected CustomException(Throwable te) {
        super(te);
    }

    protected void initException(ResponseCode error, String errorMsg){
        if(error == null){
            error = ResponseEnum.sys_error.info();
        }

        this.errorCode = error.getCodeName();

        if(StringUtils.isBlank(errorMsg)){
            this.errorMsg = error.getDec();
        }else{
            this.errorMsg = errorMsg;
        }
    }

    /**
     * 创建捕获到的异常
     * @param errorCode
     * @param errorMsg
     * @return
     */
    public static CustomException createException(ResponseCode errorCode, String errorMsg){

        if(errorCode == null){
            errorCode = ResponseEnum.sys_error.info();
        }

        if(errorMsg == null){
            errorMsg = errorCode.getDec();
        }

        CustomException oe = new CustomException(errorCode.getDec());

        oe.initException(errorCode, errorMsg);

        return oe;
    }

    /**
     * 创建捕获到的异常
     * @param errorCode
     * @return
     */
    public static CustomException createException(ResponseCode errorCode){
        CustomException oe = createException(errorCode, null);
        return oe;
    }

    /**
     * 创建捕获到的异常
     * @return
     */
    public static CustomException createException(){
        CustomException oe = createException(null, null);
        return oe;
    }

    /**
     * 创建捕获到的异常
     * @param te
     * @return
     */
    public static CustomException createException(Throwable te){
        CustomException oe = null;

        if(te instanceof CustomException){
            oe = (CustomException)te;
        }else{
            oe = new CustomException(te);
            oe.initException(null, null);
        }

        return oe;
    }
    
}
