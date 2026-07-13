package com.hs.shortlink.domain.common;

import com.hs.shortlink.domain.constant.ResultStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author: Dangerous
 * @time: 2022/5/13 21:38
 */
@Data
public class ResultVo<T> implements Serializable {
    private static final long serialVersionUID = -3681844748764439446L;

    private int error;
    private String msg;
    private T data;

    public ResultVo(ResultStatusEnum resultStatusEnum, T data) {
        this.error = resultStatusEnum.getError();
        this.msg = resultStatusEnum.getMsg();
        this.data = data;
    }

    public ResultVo(ResultStatusEnum resultStatusEnum) {
        this.error = resultStatusEnum.getError();
        this.msg = resultStatusEnum.getMsg();
    }

    public ResultVo(ResultStatusEnum resultStatusEnum, String msg, T data) {
        this.error = resultStatusEnum.getError();
        this.msg = msg != null && msg.length() > 0 ? msg : resultStatusEnum.getMsg();
        this.data = data;
    }

    public ResultVo(int error, String msg, T data) {
        this.error = error;
        this.msg = msg;
        this.data = data;
    }

    public ResultVo(int error, String msg) {
        this.error = error;
        this.msg = msg;
    }
}
