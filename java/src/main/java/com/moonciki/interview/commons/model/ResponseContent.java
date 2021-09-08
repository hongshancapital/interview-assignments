package com.moonciki.interview.commons.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.moonciki.interview.commons.enums.ResponseEnum;
import com.moonciki.interview.commons.exception.CustomException;
import com.moonciki.interview.utils.TripleDESCBC;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@ApiModel("统一返回类")
public class ResponseContent<T> {
    @ApiModelProperty(value = "返回状态")
    private Integer status;

    @ApiModelProperty(value = "返回码")
    private String code;

    @ApiModelProperty(value = "信息")
    private String msg;

    private String identify;

    @ApiModelProperty("系统时间")
    private Long ts = System.currentTimeMillis();

    @ApiModelProperty(value = "返回数据内容")
    private T content;

    @SuppressWarnings("unchecked")
    public void setAttribute(Object objKey, Object objValue){
        if(content == null){
            content = (T) new HashMap<>();
        }else {
            if(!(content instanceof Map)){
                throw CustomException.createException(ResponseEnum.class_type_error.info());
            }
        }

        ((Map) content).put(objKey, objValue);
    }

    /**
     * 返回成功
     */
    public static ResponseContent successResponse(){
        ResponseContent resp = new ResponseContent();

        resp.setStatus(1);
        resp.setCode(ResponseEnum.success.name());

        return resp;
    }

    /**
     * 返回成功
     * @param obj
     */
    public static<T> ResponseContent<T> successResponse(T obj){
        ResponseContent<T> resp = new ResponseContent<>();

        resp.setStatus(1);
        resp.setCode(ResponseEnum.success.name());
        resp.setContent(obj);

        return resp;
    }

    /**
     * 加密返回
     * @param obj
     */
    public static ResponseContent<String> encryptResponse(Object obj, String password){
        String encTxt = null;

        if(obj != null) {
            String objJson = JSON.toJSONString(obj);

            try {
                encTxt = TripleDESCBC.instance.encrypt2String(password, "", objJson);
            } catch (Exception e) {
                throw CustomException.createException(e);
            }
        }

        ResponseContent<String> resp = new ResponseContent<>();

        resp.setStatus(1);
        resp.setCode(ResponseEnum.success.name());
        resp.setContent(encTxt);

        return resp;
    }

    /**
     * 返回成功
     * @param keyObj
     * @param valueObj
     */
    public static ResponseContent responseAttribute(Object keyObj, Object valueObj){
        ResponseContent resp = successResponse();

        resp.setAttribute(keyObj, valueObj);

        return resp;
    }

    /**
     * 返回失败
     * @param exce
     */
    public static ResponseContent failResponse(Exception exce){

        CustomException customExc = CustomException.createException(exce);

        String errorCode = customExc.getErrorCode();
        String errorMsg = customExc.getErrorMsg();

        ResponseContent resp = new ResponseContent();

        resp.setStatus(0);
        resp.setCode(errorCode);
        resp.setMsg(errorMsg);
        
        return resp;
    }

    /**
     * 返回失败
     */
    public static <T> ResponseContent<T> failResponse(){
        CustomException exce = CustomException.createException();
        return failResponse(exce);
    }

    /**
     * 返回失败
     */
    public static <T> ResponseContent<T> failResponse(ResponseCode responseCode) {
        CustomException exce = CustomException.createException(responseCode);
        return failResponse(exce);
    }

    /**
     * 校验返回结果
     * @param resp
     * @return
     */
    public static boolean check(ResponseContent resp, boolean fastFail) {

        boolean check = false;

        ResponseCode errorCode = null;

        if (resp != null) {
            if (resp.getStatus() == null || resp.getStatus() != 1) {
                errorCode = new ResponseCode(resp.getCode(), resp.getMsg());
            }
        } else {
            errorCode = ResponseEnum.fallback.info();
        }
        if (errorCode == null){
            check = true;

        }else if(fastFail) {
            throw CustomException.createException(errorCode);
        }

        return check;
    }

    /**
     * 获取并校验返回结果
     * @param resp
     * @param <T>
     * @return
     */
    public static <T> T getResultContent(ResponseContent<T> resp, boolean fastFail) {
        boolean check = check(resp, fastFail);
        T result = null;
        if(check) {
            result = resp.getContent();
        }
        return result;
    }

    /**
     * 将返回信息以json 方式返回
     * @param resp
     * @param fastFail
     * @return
     */
    public static JSONObject getResultJson(ResponseContent<String> resp, boolean fastFail) {
        boolean check = check(resp, fastFail);
        JSONObject jsonData = null;
        if (check) {
            String jsonString = resp.getContent();

            jsonData = JSONObject.parseObject(jsonString);

        }
        return jsonData;
    }

    /**
     * 获取并校验返回结果 快速失败模式
     *
     * @param resp
     * @param <T>
     * @return
     */
    public static <T> T getResultContent(ResponseContent<T> resp) {
        return getResultContent(resp, true);
    }

}
