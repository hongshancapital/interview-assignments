package com.assignment.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @description:  数据格式返回统一
 */
@ApiModel(description = "响应实体类")
public class ResponseVO {

    /**
     * 异常码
     */

    @ApiModelProperty(value = "状态码", example = "2000")
    private Integer code;

    /**
     * 描述
     */
    @ApiModelProperty(position = 1, value = "提示信息", example = "成功")
    private String msg;

    /**
     * 数据
     */
    @ApiModelProperty(position = 2, value = "具体数据", example = "{xxx:{\"xx\":\"xxxx\",\"xxx\":\"xxxxxxx\"}}")
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getmsg() {
        return msg;
    }

    public void setmsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResponseVO() {}

    public ResponseVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseVO(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseVO(ResponseEnum responseEnum) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
    }

    public ResponseVO(ResponseEnum responseEnum, Object data) {
        this.code = responseEnum.getCode();
        this.msg = responseEnum.getMsg();
        this.data = data;
    }

    public static ResponseVO success(){
        return new ResponseVO(ResponseEnum.SUCCESS);
    }

    public static  ResponseVO success(Object data){
        return new ResponseVO(ResponseEnum.SUCCESS, data);
    }

    public static ResponseVO success(int code, String msg){
        return new ResponseVO(code, msg);
    }

    public static ResponseVO error(int code, String msg){
        return new ResponseVO(code, msg);
    }

    public static ResponseVO error(ResponseEnum responseEnum){
        return new ResponseVO(responseEnum);
    }

    public static ResponseVO error(ResponseEnum responseEnum, Object data){
        return new ResponseVO(responseEnum, data);
    }

    public static ResponseVO error(String msg){
        return new ResponseVO(ResponseEnum.FAILED.getCode(), msg);
    }

}
