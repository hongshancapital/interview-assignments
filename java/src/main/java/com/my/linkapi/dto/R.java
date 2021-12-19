package com.my.linkapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class R<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <T> R<T> ok(T cdata){
        return R.<T>builder().code(200).data(cdata).build();
    }

    public static <T> R<T> failed(String msg){
        return R.<T>builder().code(500).msg(msg).build();
    }
}
