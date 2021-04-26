package com.wxp.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel("通用返回值对象")
public class Message<T> implements Serializable {

    // 响应码
    @ApiModelProperty("响应码")
    protected int resultCode;
    // 错误信息
    @ApiModelProperty("错误信息")
    protected String errMsg;
    // 数据体
    @ApiModelProperty("数据体")
    protected T content;

    @ApiModelProperty("时间戳")
    protected long timestamp;

    public Message() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 创建成功消息
     *
     * @return 消息
     */
    public static <T> Message<T> success() {
        return Message.success(null);
    }

    /**
     * 创建成功消息
     *
     * @param data 数据
     * @return 消息
     */
    public static <T> Message<T> success(T data) {
        return Message.success(null, data);
    }

    /**
     * 创建成功消息
     *
     * @param msg  成功提示
     * @param data 数据
     * @return 消息
     */
    public static <T> Message<T> success(String msg, T data) {
        return buildMessage(DefaultCode.SUCCESS.getCode(),msg,data);
    }

    /**
     * 创建失败消息
     *
     * @param errMsg 错误提示
     * @return 消息
     */
    public static <T> Message<T> error(String errMsg) {
        return error(DefaultCode.SYS_ERROR, errMsg);
    }

    /**
     * 创建失败消息
     *
     * @param code   错误码
     * @param errMsg 错误提示
     * @return 消息
     */
    public static <T> Message<T> error(final DefaultCode code, String errMsg) {
        return buildMessage(code.getCode(),
                errMsg ==null ?
                        code.getMessage() : errMsg, null);
    }

    /**
     * 创建失败消息
     *
     * @param resultCode 错误码
     * @param errMsg     错误提示
     * @return 消息
     */
    public static <T> Message<T> error(int resultCode, String errMsg) {
        return buildMessage(resultCode, errMsg, null);
    }

    public static Boolean isSuccess(Message message) {
        if (message == null) {
            return false;
        }
        int resultCode = message.getResultCode();
        return resultCode == DefaultCode.SUCCESS.getCode();
    }

    private static <T> Message<T> buildMessage(int code, String msg, T data) {
        Message<T> message = new Message<T>();
        message.setResultCode(code);
        message.setErrMsg(msg);
        message.setContent(data);
        return message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Message content(T content) {
        this.content = content;
        return this;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "Message{" +
                "resultCode=" + resultCode +
                ", errMsg='" + errMsg + '\'' +
                ", content=" + content +
                ", timestamp=" + timestamp +
                '}';
    }
}