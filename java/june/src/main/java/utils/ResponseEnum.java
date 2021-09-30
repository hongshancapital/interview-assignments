package utils;

/**
 * @Description:
 * @Params
 * @Return
 * @Author Jun.Wong
 * @Date 2021/10/13 10:17
 */
public enum ResponseEnum {
    UNKNOWN_ERROR(-1,"未知错误"),
    SUCCESS(200,"成功"),
    DATA_IS_NULL(201,"数据为空"),
    PARAMETER_IS_NULL(202,"参数为空")
    ;

    private Integer code;
    private String message;

    ResponseEnum(Integer code, String message){
        this.code = code;
        this.message = message;
    }

    public Integer getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
