package cn.sequoiacap.domain.exception;

/**
 * 自定义异常
 *
 * @author liuhao
 * @date 2021/12/10
 */
public class DomainException extends RuntimeException{

    public DomainException(String message) {
        super(message);
    }
}
