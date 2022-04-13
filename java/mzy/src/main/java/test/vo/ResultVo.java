
package test.vo;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResultVo<T> implements Serializable {

    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = -1;
    private int error = 0;
    private String msg = "success";
    private T data;

    public static <T> ResultVo<T> asSuccess(String msg, T data) {
        return new ResultVo<>(SUCCESS_CODE, msg, data);
    }

    public static <T> ResultVo<T> asError(String msg, T data) {
        return new ResultVo<>(ERROR_CODE, msg, data);
    }
}
