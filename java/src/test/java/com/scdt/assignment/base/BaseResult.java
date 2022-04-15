package com.scdt.assignment.base;

import java.io.Serializable;

import com.google.common.base.Objects;
import com.xiesx.fastboot.base.AbstractStatus;
import com.xiesx.fastboot.base.result.R;

import lombok.Data;

/**
 * @title BaseResult.java
 * @description
 * @author xiesx
 * @date 2021-06-06 23:18:50
 */
@Data
public class BaseResult<T> implements AbstractStatus, Serializable {

    private static final long serialVersionUID = 1L;

    public int code;

    public String msg;

    public T data;

    @Override
    public boolean isSuccess() {
        return Objects.equal(code, R.CODE_SUCCESS);
    }
}
