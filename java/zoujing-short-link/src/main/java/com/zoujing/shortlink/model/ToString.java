package com.zoujing.shortlink.model;

import com.alibaba.fastjson.JSON;

import java.io.Serializable;

public class ToString implements Serializable {
    private static final long serialVersionUID = -23434523425253252l;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
