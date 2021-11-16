package pers.jenche.convertdomain.core;

import lombok.Getter;

/**
 *  Copyright (c) 2020 By www.jenche.cn
 * @author jenche E-mail:jenchecn@outlook.com
 * @date 2021/11/15 12:27
 * @description 系统异常消息定义，此处只定义了异常的代码，在自定义的系统异常中定义了异常值
 */
public enum ExceptionMessage {

    //起始两位数定义为异常发生的位置
    //10 发生在Dao层，一般为数据库操作出现的异常

    //20 发生在service层，一般为业务逻辑出现的异常
    S_20_NOT_IMPLEMENT(20, "0000"),     //未知的错误
    S_20_IS_BLANK_PARAMS(20, "0001"),   //必须参数是空的
    S_20_NOT_URI(20, "0002"),           //URL不合法
    //50 发生在Controller层，一般为数据的DTO传输层，一般为数据的校验或者传输的参数异常
    C_50_DATA_IS_EMPTY(50, "0001"),      //返回的数据为空
    C_50_PARAMS_EXCPTION(50, "0009"),    //参数异常
    ;
    @Getter
    int code;

    @Getter
    String msg;

    ExceptionMessage(int exType, String exCode) {
        String _code = String.valueOf(exType).concat(exCode);
        this.msg = LocalI18nResources.getInstance().getMessage(_code);
        this.code = Integer.parseInt(_code);
    }
}
