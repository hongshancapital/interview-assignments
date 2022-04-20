package cn.sequoiacap.java.common.def;

public class Constants {
    /** 处理成功的消息提示 */
    public static final String SUCCESS_MSG ="处理成功。";

    /** 找不到资源的消息提示 */
    public static final String NOT_FOUND_MSG = "没有找到短域名信息。";

    /** 发送系统错误的消息提示 */
    public static final String INTERNAL_ERROR_MSG = "发送系统错误。";

    /** 超出最大范围 */
    public static final String OVER_FLOW_ERROR = "短域名条数超过最大限制。";

    /** 最大储存个数（1亿） */
    public static final long URL_MAX_COUNT = 100000000L;
}
