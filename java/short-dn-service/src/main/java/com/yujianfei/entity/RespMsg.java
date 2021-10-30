package com.yujianfei.entity;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class RespMsg<T> implements Serializable {

    private static final long serialVersionUID = -8221365779790557490L;

    public String appMsg;
    public String appCode;
    public String appStatus;
    public T msgBody;

}
