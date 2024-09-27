package demo.common.result;

public class ResultUtil {
    private static final String SUCCESS = "success";

    public static <T> Result<T> success(T data){
        return gen(ResultEnum.SUCCESS.getCode(), SUCCESS, data);
    }

    public static <T> Result<T> success(String msg, T data){
        return gen(ResultEnum.SUCCESS.getCode(), msg, data);
    }

    public static <T> Result<T> fail(String msg){
        return gen(ResultEnum.FAIL.getCode(), SUCCESS, null);
    }

    public static <T> Result<T> serverError(String msg){
        return gen(ResultEnum.INTERNAL_SERVER_ERROR.getCode(), msg, null);
    }

    public static <T> Result<T> gen(int code, String msg, T data){
        Result<T> result = new Result<>();

        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);

        return result;
    }
}
