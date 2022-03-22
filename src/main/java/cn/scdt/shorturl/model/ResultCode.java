package cn.scdt.shorturl.model;

/**
 * 状态码
 * @author TianWenTao
 */
public enum ResultCode {

    SUCCESSED(1, "任务执行成功"),
    FAILED(0, "任务执行失败"),
	;

    private Integer code;
    private String msg;

    private ResultCode(Integer code, String msg) {
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
