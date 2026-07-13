package url.converter.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 通用响应对象
 * @author Wang Siqi
 * @date 2021/12/31
 */
@Data
@AllArgsConstructor
public class CommonResult<T> {

    public static final int SUCCESS_CODE = 0;
    public static final int ERROR_CODE = -1;
    private static final String EMPTY = "";

    private int error;
    private String msg;
    private T data;

    public static CommonResult success() {
        return success(null);
    }

    public static <T> CommonResult<T> success(T data) {
        return new CommonResult<T>(SUCCESS_CODE, EMPTY, data);
    }

    public static <T> CommonResult<T> error(String msg) {
        return new CommonResult<T>(ERROR_CODE, msg, null);
    }

    public static <T> CommonResult<T> error(int error, String msg) {
        return new CommonResult<T>(error, msg, null);
    }

}
