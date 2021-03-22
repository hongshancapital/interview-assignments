package xiejin.java.interview.enums;


import xiejin.java.interview.result.IResultCode;

/**
 *
 * @author xiejin
 * @date 2020/7/9 19:53
*/
public enum ResultEnum implements IResultCode {

    MODEL_CODE(110000, "USER_SERVER"),
    RECORD_EXISTS(111002, "记录已存在"),
    RECORD_STATE_EXPIRE(111003, "短网址已停用或已过期"),
    ;



	private int code;
	private String message;

	ResultEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}

    public static IResultCode get(Integer code) {
        for (ResultEnum codeEnum : ResultEnum.values()) {
            if (codeEnum.code == code) {
                return codeEnum;
            }
        }
        return MODEL_CODE;
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
