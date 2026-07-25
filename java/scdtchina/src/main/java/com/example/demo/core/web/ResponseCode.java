package com.example.demo.core.web;


public class ResponseCode {

    //处理成功
    public static final MessageCode SUCCESS = MessageCode.of(200, "调用成功");
    public static final MessageCode ERR_URL_VALID = MessageCode.of(1000, "链接地址非法，请重新输入!");
    public static final MessageCode ERR_URL_BLANK = MessageCode.of(1001, "链接地址为空，请重新输入!");
    public static final MessageCode ERR_URL_NOT_EXIST = MessageCode.of(1002, "链接地址不存在!");

}