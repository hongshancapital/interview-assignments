package com.sequoia.shorturl.common;
import lombok.Data;
import org.apache.log4j.spi.ErrorCode;

import java.io.Serializable;
/***
 *
 *@ API 接口
 *
 *@Author xj
 *
 *@Date 2021/06/27
 *
 *@version v1.0.0
 *
 */
@Data
public class SequoiaResponse<T> implements Serializable {

    private T data;
    private int code;
    private String message;

    public SequoiaResponse() {
        this.code = 0;
        this.message = "success";
    }

    /**
     * 处理失败接口
     * @param error
     * @return
     */
    public SequoiaResponse responseError(String error) {
        return new SequoiaResponse(1, error,null);
    }

    public SequoiaResponse(int code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }


}
