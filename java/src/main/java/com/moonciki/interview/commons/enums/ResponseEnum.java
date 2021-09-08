package com.moonciki.interview.commons.enums;

import com.moonciki.interview.commons.constant.GlobalConstant;
import com.moonciki.interview.commons.model.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public enum ResponseEnum {
    /** 成功 **/
    success("请求成功"),
    username_error("用户名错误"),
    password_error("密码错误"),
    username_password_not_match("用户名密码不匹配"),
    validate_code_error("验证码错误"),
    login_error("登录失败"),
    user_exist("用户已存在"),
    user_nonexist("用户不存在"),
    kick_out("用户已在其他地方登录"),
    request_error("参数错误"),
    sys_error("系统异常"),
    not_found("没有找到资源"),
    no_login("没有登录"),
    no_permission("没有权限"),
    timeout("超时"),
    redis_timeout("系统繁忙"),
    class_type_error("类型错误"),
    sys_busy("系统繁忙"),
    fallback("fallback"),
    session_cache_error("会话错误"),
    illegal_req("非法请求"),
    file_template_error("导入模板标题错误或模板标题格式错误"),
    file_template_empty("导入文件内没有数据"),

    ;

    private String dec;

    public String getDec() {
        return dec;
    }

    ResponseEnum(String dec){
        this.dec = dec;
    }

    public ResponseCode info(){
        String codeName = this.name();
        String dec = this.dec;

        ResponseCode respCode = new ResponseCode(codeName, dec);

        return respCode;
    }

    public ResponseCode info(String dec){
        String codeName = this.name();

        ResponseCode respCode = new ResponseCode(codeName, dec);

        return respCode;
    }

}
