package me.huchao.sd.domain;

/**
 * @author huchao
 */
public class DomainException extends Exception{

    public DomainException() {
        super();
    }

    public DomainException(String message) {
        super(message);
    }

    public DomainException(Throwable cause) {
        super(cause);
    }
}
