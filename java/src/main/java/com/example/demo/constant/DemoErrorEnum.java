/**
 * @(#)DemoErrorEnum.java, 12月 26, 2021.
 * <p>
 * Copyright 2021 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.example.demo.constant;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.serializer.SerializeConfig;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

/**
 * @author 张三
 */
@Slf4j
@ToString
@AllArgsConstructor
public enum DemoErrorEnum {

    SUCCESS(200, "成功"),
    PARAM_ERROR(400, "参数错误"),
    SYSTEM_EXCEPTION(500, "系统异常"),

    URL_TRANSFER_ERROR(600, "url转换异常");

    @Getter
    private final int code;

    @Getter
    private final String message;

    @Override
    public String toString() {
        SerializeConfig serializeConfig = new SerializeConfig();
        serializeConfig.configEnumAsJavaBean(DemoErrorEnum.class);
        return JSON.toJSONString(this, serializeConfig);
    }

    public static Pair<Integer, String> parseException(String str) {
        try {
            if (!str.contains("code")) {
                return DemoErrorEnum.SYSTEM_EXCEPTION.transform();
            }
            JSONObject jsonObject = JSONObject.parseObject(str);
            String code = jsonObject.getString("code");
            String message = jsonObject.getString("message");
            return Pair.of(Integer.valueOf(code), message);
        } catch (Exception e) {
            log.warn("错误码解析异常，str:{}", str, e);
            return DemoErrorEnum.SYSTEM_EXCEPTION.transform();
        }
    }

    private Pair<Integer, String> transform() {
        return Pair.of(this.code, this.message);
    }

}
