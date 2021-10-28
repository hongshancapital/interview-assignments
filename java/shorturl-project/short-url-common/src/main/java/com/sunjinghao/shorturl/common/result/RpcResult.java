package com.sunjinghao.shorturl.common.result;

import lombok.Data;

/**
 * RPC 通信结果封装
 *
 * @author sunjinghao
 */
@Data
public class RpcResult<T> {

    private Integer code = -1;
    private String msg;
    private T data;
}
