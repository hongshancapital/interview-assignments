package com.scdt.china.interview.enm;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author sujiale
 */
@Getter
@AllArgsConstructor
public enum ResultEnum {
    /**
     *  接口返回的代码和描述信息，code-message。
     */
    OK("200", "OK"),
    E000001("000001", "参数错误");
    private String code;
    private String message;
}
