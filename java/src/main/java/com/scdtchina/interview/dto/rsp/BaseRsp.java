package com.scdtchina.interview.dto.rsp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaseRsp<T> {
    @Builder.Default
    private int code = 0;
    private String msg;
    private T data;
}
