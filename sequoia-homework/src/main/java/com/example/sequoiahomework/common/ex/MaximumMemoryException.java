package com.example.sequoiahomework.common.ex;

import lombok.NoArgsConstructor;

/**
 * @author Irvin
 * @description 内存上限异常
 * @date 2021/10/10 12:50
 */
@NoArgsConstructor
public class MaximumMemoryException extends Exception {


    /**
     * 有参构造方法
     * @author Irvin
     * @date 2021/10/10
     * @param message 异常信息
     */
    public MaximumMemoryException(String message) {
        super(message);
    }

}
