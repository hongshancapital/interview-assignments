package com.scdt.assignment.config;

import com.xiesx.fastboot.core.exception.RunException;

/**
 * @title Msg.java
 * @description
 * @author
 * @date 2022-04-15 17:10:24
 */
public final class Msg {

    public final static RunException INVALID = new RunException(911, "链接格式错误");

    public final static RunException LENGTH = new RunException(921, "链接长度错误");

    public final static RunException NOT_EXIST = new RunException(922, "查询没有记录");
}
