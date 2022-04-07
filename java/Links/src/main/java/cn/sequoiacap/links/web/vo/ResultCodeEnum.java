package cn.sequoiacap.links.web.vo;

import lombok.Getter;

/**
 * @author : Liushide
 * @date :2022/4/6 11:25
 * @description : 响应码枚举，对应HTTP状态码
 */
@Getter
public enum ResultCodeEnum {

    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 创建成功
     */
    CREATED(201, "创建成功"),
    /**
     * 请求失败
     */
    BAD_REQUEST(400, "请求失败"),
    /**
     * 未认证
     */
    UNAUTHORIZED(401, "认证失败"),
    /**
     * 接口不存在
     */
    NOT_FOUND(404, "接口不存在"),
    /**
     * 方法不被允许
     */
    METHOD_NOT_ALLOWED(405,"方法不被允许"),
    /**
     * 没有找到数据FOUND
     */
    NOT_DATA_FOUND(406, "没有找到数据"),
    /**
     * 服务器内部错误
     */
    INTERNAL_SERVER_ERROR(500, "系统繁忙"),

    /* 参数错误:1001-1999 */
    /**
     * 参数无效
     */
    PARAMS_IS_INVALID(1001, "参数无效"),
    /**
     * 参数为空
     */
    PARAMS_IS_BLANK(1002, "参数为空");

    /**
     * 返回码
     */
    private Integer code;

    /**
     * 返回消息
     */
    private String message;

    ResultCodeEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
