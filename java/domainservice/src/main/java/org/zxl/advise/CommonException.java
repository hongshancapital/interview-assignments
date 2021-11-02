package org.zxl.advise;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.Errors;

@Getter
@Setter
public class CommonException extends RuntimeException {

    private int statusCode = HttpStatus.BAD_REQUEST.value();
    private Object errors;
    private int code = 100;
    private String message = "parameter_incorrect";

    public CommonException() {
        super();
    }


    public CommonException(String message) {
        super(message);
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
