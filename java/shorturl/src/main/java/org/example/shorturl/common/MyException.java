package org.example.shorturl.common;

/**
 * 业务异常
 *
 * @author bai
 * @date 2022/02/16
 */
public class MyException
        extends RuntimeException {
    
    public MyException(String errorMessage) {
        super(errorMessage);
    }
    
    /**
     * 重写堆栈跟踪,优化性能,参考kafak
     *
     * @return {@link Throwable}
     */
    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
