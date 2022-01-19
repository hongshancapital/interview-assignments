/**
 * this is a test project
 */

package com.example.interviewassgnments.entitys;

import com.example.interviewassgnments.utils.ResultEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * 对外统一输出结果
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:32
 * @Description: com.example.interviewassgnments.entitys
 * @version: 1.0
 */
@AllArgsConstructor
@Data
@ApiModel(value = "系统返回结果实体类", description = "结果实体类")
public class BaseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    /* 无参构造函数 */
    private BaseResult() {
    }

    // 自定义参数，链式编程
    public BaseResult data(T data) {
        this.setData(data);
        return this;
    }

    public BaseResult code(Integer code) {
        this.setCode(code);
        return this;
    }

    public BaseResult message(String message) {
        this.setMessage(message);
        return this;
    }

    /**
     * 公用代码
     */
    private void setResultCode(ResultEnum resultCode) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 成功
     *
     * @return SystemResult 统一消息体
     */
    public static BaseResult success() {
        return success(null);
    }


    /**
     * 成功
     *
     * @param data Object 消息体
     * @return SystemResult 统一消息体
     */
    public static BaseResult success(Object data) {
        BaseResult result = new BaseResult<>();
        result.setData(data);
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMessage(ResultEnum.SUCCESS.getMessage());
        return result;
    }

    /**
     * 异常处理
     *
     * @param code    Integer 设置返回码
     * @param message String 设置返回消息
     * @return SystemResult 统一消息体
     */
    public static BaseResult error(Integer code, String message) {
        BaseResult result = new BaseResult();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    /**
     * 异常处理
     *
     * @param resultCode SystemCode
     * @return SystemResult 统一消息体
     */
    public static BaseResult error(ResultEnum resultCode) {
        return error(resultCode.getCode(), resultCode.getMessage());
    }
}