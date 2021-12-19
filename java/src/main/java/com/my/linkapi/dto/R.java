package com.my.linkapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@ApiModel("返回对象")
public class R<T> {
    @ApiModelProperty("返回代码 200正常 500错误")
    private Integer code;
    @ApiModelProperty("错误消息")
    private String msg;
    @ApiModelProperty("返回内容")
    private T data;

    public static <T> R<T> ok(T cdata){
        return R.<T>builder().code(200).data(cdata).build();
    }

    public static <T> R<T> failed(String msg){
        return R.<T>builder().code(500).msg(msg).build();
    }
}
