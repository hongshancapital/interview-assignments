package com.xwt.domain.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.raven.commons.data.annotation.Ignore;
import org.raven.commons.data.annotation.Member;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一的应答包装类
 *
 * @author xiwentao
 */
@Data
@ApiModel(description = "ResponseModel")
public class ResponseModel<TData>
        implements org.raven.commons.contract.ResponseModel<TData, String, Map<String, Object>> {

    public static final String EMPTY = "";
    public static final String SUCCESS_MESSAGE = "操作成功";
    public static final String FAIL_MESSAGE = "操作失败";
    private static final String RESULT = "result";

    /**
     * case代码，暂时没定
     */
    @ApiModelProperty(value = "case代码，暂时没定")
    private String code;

    /**
     * 请求结果标志，true：成功， false：失败
     */
    @ApiModelProperty(value = "请求结果标志，true：成功， false：失败")
    private boolean success;

    /**
     * 提示信息
     */
    @ApiModelProperty(value = "提示信息")
    private String message;

    /**
     * 结果数据
     */
    @Member(RESULT)
    @JsonProperty(RESULT)
    @ApiModelProperty(value = "结果数据")
    private TData data;

    /**
     * 扩展信息，根据接口情况而定
     */
    @ApiModelProperty(value = "扩展信息，根据接口情况而定")
    private Map<String, Object> extension;

    /**
     * 方法描述
     *
     * @param
     * @return:
     */
    public ResponseModel() {
        this.extension = new HashMap<>();
    }

    /**
     * 方法描述
     *
     * @param data
     * @param message
     * @param code
     * @param success
     * @return:
     */
    public ResponseModel(TData data, String message, String code, boolean success) {

        this.data = data;
        this.message = message;
        this.code = code;
        this.extension = new HashMap<>();
        this.success = success;
    }

    /**
     * 方法描述
     *
     * @param key
     * @param val
     * @return: void
     */
    @Ignore
    public void extend(String key, Object val) {
        this.extension.put(key, val);
    }

    /**
     * 方法描述
     *
     * @param data
     * @param message
     * @param code
     * @param success
     * @return ResponseModel
     */
    public static <TData> ResponseModel<TData> ok(TData data, String message, String code, boolean success) {
        return new ResponseModel(data, message, code, success);
    }

    /**
     * 方法描述
     *
     * @param data
     * @param message
     * @return ResponseModel
     */
    public static <TData> ResponseModel<TData> ok(TData data, String message) {
        return ok(data, message, EMPTY, true);
    }

    /**
     * 方法描述
     *
     * @param data
     * @return ResponseModel
     */
    public static <TData> ResponseModel<TData> ok(TData data) {
        return ok(data, SUCCESS_MESSAGE, EMPTY, true);
    }

    /**
     * 方法描述
     *
     * @param
     * @return ResponseModel
     */
    public static ResponseModel ok() {
        return ok(null);
    }

    /**
     * 方法描述
     *
     * @param message
     * @return ResponseModel
     */
    public static ResponseModel ok(String message) {
        return ok(null, message, EMPTY, true);
    }

    /**
     * 方法描述
     *
     * @param data
     * @return: com.xforceplus.goods.model.ResponseModel
     */
    public static <TData> ResponseModel fail(TData data) {
        return new ResponseModel(data, FAIL_MESSAGE, EMPTY, false);
    }

    /**
     * 方法描述
     *
     * @param message
     * @param code
     * @return: ResponseModel
     */
    public static ResponseModel fail(String message, String code) {
        return new ResponseModel(null, message, code, false);
    }

    /**
     * 方法描述
     *
     * @param message
     * @return: ResponseModel
     */
    public static ResponseModel fail(String message) {
        return fail(message, EMPTY);
    }

}
