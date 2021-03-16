package com.sequoiacap.domain.common.response;

import lombok.Data;
import java.io.Serializable;

@Data
public class Response<T> implements Serializable {

    public static final Long SUCCESS = 200L;
    public static final Long ERROR = 500L;
    public static final Long BAD_REQUEST = 400L;

    private Long code;
    private String message;
    private T payload;

    public static <T> Response<T> succeed(T payload) {
        Response<T> response = new Response<>();
        response.setCode(SUCCESS);
        response.setPayload(payload);
        return response;
    }
}
