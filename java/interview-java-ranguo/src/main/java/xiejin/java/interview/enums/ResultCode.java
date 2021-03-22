package xiejin.java.interview.enums;


import xiejin.java.interview.result.IResultCode;

/**
 * @ClassName: ResultCode
 * @author xiejin
 * @date 2020年7月7日
 */
public enum ResultCode implements IResultCode {
	/*
	 * 请求返回状态码和说明信息
	 */
	FAIL(0,"FAIL"), //失败
	SUCCESS(200, "SUCCESS"),  //成功
	BAD_REQUEST(400, "Param Error"),//参数错误
	UNAUTHORIZED(401, "Authentication Failed"), //验证失败
	LOGIN_ERROR(402, "Login Fail, UserName or Password is Wrong"),//登录失败  用户名/密码错误
	FORBIDDEN(403,  "Forbidden Access"), //禁止访问
	NOT_FOUND(404, "Resource Empty"), //资源空
	OPERATE_ERROR(405, "Resource does not exist"), //资源不存在
	TIME_OUT(408, "Request Timeout"), //请求超时
	TOO_MANY_REQUEST(429, "Request Too Many"),  // 发送请求过多
	SIGN_ERROR(409, "Sign Error"),   //签名错误
	SERVER_ERROR(500, "System Error");  //系统错误

	private int code;
	private String message;

	ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}

	@Override
	public int getCode() {
		return this.code;
	}

	@Override
	public String getMessage() {
		return this.message;
	}
}
