package com.domain.exception;

/**
 * @author ：ji
 * @description：自定义异常类
 */
public class DomainException extends RuntimeException{

    private int code;
    private String message;

    public DomainException(){
        super();
    }

    public DomainException(int code, String message){
        super(message);
        this.code = code;
        this.message = message;
    }

    public DomainException(String message){
        super(message);
        this.code = ExceptionEnums.FAIL.getCode();
        this.message = message;
    }

    public DomainException(ExceptionEnums exc){
        super(exc.getMessage());
        this.code = exc.getCode();
        this.message = exc.getMessage();
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * 返回super.getMessage
     * @return
     */
    public String getOriginMessage() {
        return super.getMessage();
    }
}
