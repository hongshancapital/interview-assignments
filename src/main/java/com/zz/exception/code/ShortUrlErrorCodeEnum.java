package com.zz.exception.code;

/**
 * 短链接的错误信息编码
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public enum ShortUrlErrorCodeEnum implements ErrorCode {
    SU_001("参数错误"),
    SU_002("短链接不存在"),
    SU_003("短链接已经过期"),
    SU_004("生成序号失败"),
    SU_005("短码异常");

    private String desc;

    ShortUrlErrorCodeEnum(String desc) {
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getMessage() {
        return this.desc;
    }
}
