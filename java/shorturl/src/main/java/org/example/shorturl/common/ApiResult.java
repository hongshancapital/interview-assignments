package org.example.shorturl.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.example.shorturl.constants.HttpStatusConstant;
import org.example.shorturl.enums.ResultCodeEnum;
import org.example.shorturl.util.MySpringUtil;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * 统一返回结果
 *
 * @author bai
 * @date 2021/7/20 21:09
 */
@Getter
@Setter
@ApiModel
public class ApiResult<T>
        implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** 为了优化性能,所有没有携带数据的正确结果,均可用该对象表示。 */
    private static final ApiResult<Void> OK = new ApiResult<>();
    
    @ApiModelProperty(value = "是否成功标记")
    private boolean success = true;
    
    @ApiModelProperty(value = "http状态码")
    private Integer code = HttpStatusConstant.HTTP_OK;
    
    @ApiModelProperty(value = "错误码")
    private String appCode = "";
    
    @ApiModelProperty(value = "信息描述")
    private String message = "";
    
    @ApiModelProperty(value = "服务名")
    private String path = MySpringUtil.getAppName();
    /** 时间 */
    @ApiModelProperty(value = "时间戳")
    private String time = String.valueOf(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
    /** 在验证同时,仍然需要附加的关联数据对象 */
    @ApiModelProperty(value = "结果对象")
    private T data;
    
    private ApiResult() {
    }
    
    /**
     * 构造方法
     *
     * @param success 成功
     * @param code    代码
     * @param appCode 应用程序代码
     * @param message 消息
     */
    public ApiResult(boolean success,
                     Integer code,
                     String appCode,
                     String message) {
        this.success = success;
        this.code = code;
        this.appCode = appCode;
        this.message = message;
    }
    
    
    /**
     * 创建成功对象。
     * 如果需要绑定返回数据,可以在实例化后调用setData方法。
     *
     * @return 返回创建的ApiResult实例对象
     */
    public static ApiResult<Void> success() {
        return OK;
    }
    
    /**
     * 创建带有返回数据的成功对象。
     *
     * @param data 返回的数据对象
     * @return 返回创建的ApiResult实例对象
     */
    public static <T> ApiResult<T> success(T data) {
        ApiResult<T> resp = new ApiResult<>();
        resp.data = data;
        return resp;
    }
    
    
    /**
     * 失败
     *
     * @param string 字符串
     * @return {@link ApiResult}<{@link T}>
     */
    public static <T> ApiResult<T> failure(String string) {
        return failure(ResultCodeEnum.FAILURE, string);
    }
    
    /**
     * 创建错误对象。
     * 如果返回错误对象,code,appCode 和 message 分别取自于参数code, appCode 和参数 message。
     *
     * @param returnCodeEnum 错误码枚举
     * @return 返回创建的ApiResult实例对象
     */
    public static <T> ApiResult<T> failure(ResultCodeEnum returnCodeEnum,
                                           String strings) {
        return failure(returnCodeEnum.getCode(), returnCodeEnum.getAppCode(), strings);
    }
    
    
    /**
     * 创建失败对象。
     * 如果返回错误对象,code,appCode 和 message 分别取自于参数code, appCode 和参数 message。
     *
     * @param code    代码
     * @param appCode 应用程序代码
     * @param message 消息
     * @return 返回创建的ApiResult实例对象
     */
    public static <T> ApiResult<T> failure(Integer code,
                                           String appCode,
                                           String message) {
        return new ApiResult<>(false, code, appCode, message);
    }
}
