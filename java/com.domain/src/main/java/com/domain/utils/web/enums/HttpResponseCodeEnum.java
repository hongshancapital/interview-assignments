package com.domain.utils.web.enums;

import com.alibaba.fastjson.annotation.JSONField;
import com.alibaba.fastjson.annotation.JSONType;
import lombok.Getter;

/**
 * 全局 http内部响应枚举
 * @author jacky
 * @version 1.0
 * @since 1.0
 */
@Getter
@JSONType(serializeEnumAsJavaBean = true)
public enum HttpResponseCodeEnum {

    SUCCESS(10,"成功"),
    TRYAGAIN(20,"你操作太快，请稍后尝试"),
    BUSY(30,"系统正忙，请稍后尝试"),
    NOTFUND(40,"您查找的数据不存在"),
    FAIL(99,"失败");

    @JSONField(serialize = false)
    private Integer code;

    private String message;

    HttpResponseCodeEnum(Integer code, String message){
        this.code=code;
        this.message=message;
    }

    public String getName(){
        return this.name();
    }

    public String getMessage(){
        return this.message;
    }
}
