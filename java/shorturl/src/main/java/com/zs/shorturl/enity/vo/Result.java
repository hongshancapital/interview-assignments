package com.zs.shorturl.enity.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.regex.Pattern;

/**
 * @author zs
 * @date 2021/5/10
 */
@ApiModel(description = "rest请求的返回模型，所有rest正常都返回该类的对象")
@Getter
public class Result<T>  implements Serializable {


    private static final long serialVersionUID = 1L;

    public static final String SUCCESSFUL_CODE = "RX0000";
    public static final String FAIL_CODE = "RX9999";
    public static final String SUCCESSFUL_MESG = "处理成功";
    public static final String FAIL_MESG = "系统异常";

    public static final Pattern IS_REPLACE = Pattern.compile("\\s*|\t|\r|\n");

    @ApiModelProperty(value = "处理结果code", required = true)
    private String code;


    @ApiModelProperty(value = "处理结果描述信息")
    private String msg;


    @ApiModelProperty(value = "处理结果数据信息")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    public Result() {
    }


    /**
     * 用于构造的结果
     *
     * @param code
     * @param mesg
     * @param data
     */
    public Result(String code, String mesg, T data) {
        this.code = code;
        this.msg = mesg;
        this.data = data;
    }



    /**
     * 快速创建成功结果并返回结果数据
     *
     * @param data
     * @return Result
     */
    public static Result success(Object data) {
        return new Result<>(SUCCESSFUL_CODE, SUCCESSFUL_MESG, data);
    }


    /**
     * 快速创建成功结果
     *
     * @return Result
     */
    public static Result success() {
        return success(null);
    }

    /**
     * 系统异常类有异常信息
     *
     * @return Result
     */
    public static Result fail(String failMsg) {
        if (!StringUtils.hasLength(failMsg)){
            failMsg = FAIL_MESG;
        }
        return new Result(FAIL_CODE,failMsg,null);
    }

    /**
     * 快速创建失败对象
     * @return
     */
    public static Result fail(){
        return new Result(FAIL_CODE,FAIL_MESG,null);
    }





    /**
     * 成功code=RX0000
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isSuccess() {
        return SUCCESSFUL_CODE.equals(this.code);
    }

    /**
     * 失败
     *
     * @return true/false
     */
    @JsonIgnore
    public boolean isFail() {
        return !isSuccess();
    }


}

