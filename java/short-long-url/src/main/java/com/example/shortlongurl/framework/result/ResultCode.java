package com.example.shortlongurl.framework.result;

public enum ResultCode implements BaseEnum {
    SUCCESS(200,"成功"),
    FAIL(500,"失败");

    Integer code;
    String message;
    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }



    @Override
    public String getMessage(Integer code) {
        if(null == code) return null;
        for(ResultCode rc : ResultCode.values()){
            if(code.equals(rc.code))
                return rc.message;
        }
        return null;
    }
}
