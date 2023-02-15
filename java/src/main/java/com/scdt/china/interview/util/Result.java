

package com.scdt.china.interview.util;


import lombok.Getter;
import lombok.Setter;

/**
 * @author sujiale
 */
@Getter
@Setter
public class Result<T> {

    /**
     * 接口返回值码
     */
    private String code;

    /**
     * 返回信息
     */
    private String msg;

    /**
     * 返回数据
     */
    private T data;

    public Result() {
    }

}
