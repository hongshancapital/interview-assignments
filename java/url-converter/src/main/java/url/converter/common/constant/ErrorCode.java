package url.converter.common.constant;

/**
 * 错误码汇总
 * @author Wang Siqi
 * @date 2021/12/31
 */
public enum ErrorCode {

    UNKNOWN_SERVER_ERROR(1000, "服务器开小差了~"), // 未知异常
    BAD_REQUEST_PARAM(1001, "请求参数校验失败"),
    URL_HANDLE_EXCEPTION(1002, "url处理异常"),
    NO_MEM_USABLE(1003, "没有足够的空间"),
    URL_INVALID(1004, "url不合法"),
    SHORT_URL_UP_LIMIT(1005, "短链个数到达上限"),
    ;

    private final Integer value;
    private final String errorMsg;

    ErrorCode(int value, String errorMsg) {
        this.value = value;
        this.errorMsg = errorMsg;
    }

    public Integer code() {
        return this.value;
    }

    public String errorMsg() {
        return this.errorMsg;
    }

}
