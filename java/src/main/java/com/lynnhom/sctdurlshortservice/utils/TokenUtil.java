package com.lynnhom.sctdurlshortservice.utils;

import com.google.common.collect.ImmutableMap;
import com.lynnhom.sctdurlshortservice.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @description: Token管理和验证工具
 * @author: Lynnhom
 * @create: 2021-11-09 19:02
 **/

@Slf4j
public class TokenUtil {

    /**
     * salt加盐信息，用于用户token的验证
     */
    private static final Map<String, String> saltMap = new ImmutableMap.Builder<String, String>()
            .put("001", "ZUR4uDNa7k!N9C0i").put("002", "svY5Ga3MUvmxtms3").put("003", "8AjuO2Tn8gHZVpYP").build();

    private TokenUtil(){}

    /**
     * 验证token的正确性
     * token计算方法：两份appId对应的加盐信息中间拼接接口请求信息，进行MD5运算
     * @param appId
     * @param params
     * @param token
     * @return
     */
    public static Boolean isValid(String appId, String params, String token) {
        if (!saltMap.containsKey(appId)) {
            log.warn("isValid, appId not exist, appId={}", appId);
            return Boolean.FALSE;
        }
        StringBuilder sb = new StringBuilder(saltMap.get(appId));
        sb.append(params).append(saltMap.get(appId));

        String rightToken = DigestUtils.md5DigestAsHex(sb.toString().getBytes(StandardCharsets.UTF_8));
        return StringUtil.equalsIgnoreCase(rightToken, token);
    }

}
