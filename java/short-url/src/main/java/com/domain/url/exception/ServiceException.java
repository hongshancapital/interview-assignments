package com.domain.url.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Arrays;
import java.util.function.Supplier;

/**
 * 自定义异常
 */
@EqualsAndHashCode(callSuper = false)
@Data
public class ServiceException extends Exception implements Supplier<ServiceException> {
    private static final long serialVersionUID = -1;

    private String code;

    public ServiceException(String message) {
        super(message);
        this.setCode("__UN_KNOWN__");
    }

    public ServiceException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public ServiceException(IExceptionCode code) {
        this(code.name(), code.getMessage());
    }

    public ServiceException(IExceptionCode code, Object... args) {
        this(code.name(), format(code.getMessage(), args));
    }

    private static String format(String format, Object... args) {
        if (isLastOneThrowable(args)) {
            return String.format(format.replaceAll("\\{\\}", "%s"), Arrays.copyOf(args, args.length - 1));
        } else {
            return String.format(format.replaceAll("\\{\\}", "%s"), args);
        }
    }

    private static boolean isLastOneThrowable(Object... args) {
        return args.length > 0 && args[args.length - 1] instanceof Throwable;
    }

    @Override
    public ServiceException get() {
        return this;
    }
}
