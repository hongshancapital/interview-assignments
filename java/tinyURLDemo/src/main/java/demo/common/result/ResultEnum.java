package demo.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultEnum {
    SUCCESS(200),
    FAIL(400),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500);

    private final int code;
}
