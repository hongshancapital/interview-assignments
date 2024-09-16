package com.example.bean.result;

public enum ResultRpcEnum {
	
	RPC_INVOKE_SUCCESS("0", "调用成功"),
	/**
	 * 调用失败
	 */
	RPC_SYSTEM_ERROR_FAIL("-100", "系统异常"),
	RPC_PARAM_ERROR("000001", "参数有误"),
	USER_EXPIRES("000002", "您未登录或登录信息已过期"),
	PARAM_EMPTY("000003", "缺少请求参数"),
	DATA_EMPTY("000004", "未找到目标资源"),
	GEN_EXISTS_UNUSABLE_TPL("000100", "存在不可用的模版"),
	DATA_ERROR("999996", "数据异常"),
	BAD_REQUEST("999997", "非法请求"),
	FAILED("999998", "业务异常"),
	ERROR("999999","系统繁忙，请稍后再试"),
	ID_EMPTY("000020","缺少数据ID"),
	USER_EMPTY("000021","缺少用户ID"),
	CREATE_FAILURE("000030", "新增失败"),
	UPDATE_FAILURE("000031", "更新失败"),
	DELETE_FAILURE("000032", "删除失败"),
	SHARE_TOKEN_ILLEGAL("000042", "分享已过期"),
	SHARE_TOKEN_EXPIRED("000043", "分享已过期"),
	SHARE_TOKEN_NOPWD("000044", "缺少令牌密码"),
	SHARE_TOKEN_PWD_FAULT("000045", "令牌密码错误");

	private ResultRpcEnum(String code, String desc){
		this.code = code;
		this.desc = desc;
	}
	
	private String code = "0";
	
	private String desc;

	public String getDesc() {
		return desc;
	}

	private void setDesc(String desc) {
		this.desc = desc;
	}

    public String getCode() {
        return code;
    }

	private void setCode(String code) {
        this.code = code;
    }

	public static ResultRpcEnum getValue(String code) {

		for (ResultRpcEnum rpcEnum : values()) {
			if (rpcEnum.getCode().equals(code == null? "-100":code)) {
				return rpcEnum;
			}
		}
		return null;

	}
}
