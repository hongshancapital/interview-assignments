package com.example.bean.result;

import com.example.enums.ApiStatusCode;
import lombok.Data;

import java.io.Serializable;

/**
 * 描述:
 * Api返回结果类
 *
 * @author eric
 * @create 2021-07-21 5:00 下午
 */
@Data
public class ApiResult<T> implements Serializable {
    public String statusCode; //状态码
    public String message; //返回消息描述
    public T data; //返回数据

    public static <T> ApiResult success(T data) {
        ApiResult apiResultModel = new ApiResult();
        apiResultModel.setMessage(ApiStatusCode.SUCCESS.getDescription());
        apiResultModel.setStatusCode(ApiStatusCode.SUCCESS.getCode());
        apiResultModel.setData(data);
        return apiResultModel;
    }

    public static <T> ApiResult error(ResultRpc resultRpc) {
        ApiResult apiResultModel = new ApiResult();
        apiResultModel.setMessage(resultRpc.getMsg());
        apiResultModel.setStatusCode(ApiStatusCode.BUSINESS_EXCEPTION.getCode());
        apiResultModel.setData(resultRpc.getData());
        return apiResultModel;
    }

    public static <T> ApiResult getErrorResultWithMsg(String code, String errorMsg) {
        ApiResult apiResultModel = new ApiResult();
        apiResultModel.setMessage(errorMsg);
        apiResultModel.setStatusCode(code);
        apiResultModel.setData(null);
        return apiResultModel;
    }
}
