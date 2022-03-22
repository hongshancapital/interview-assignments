package cn.scdt.shorturl.model;

/**
 * 状态码
 * @author TianWenTao
 */
public enum StatusCode {

	PROCESSED(1, "任务已处理"),
	UNPROCESSED(0, "任务未处理"),
	;

    private Integer code;
    private String msg;

    private StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
