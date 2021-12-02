package com.zhangliang.suril.configuration;

import java.util.HashMap;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


/**
 * 统一消息返回代码，方便error统一管理
 *
 * @author zhang
 * @date 2021/12/01
 */
@SuppressWarnings("ConfigurationProperties")
@Component
@ConfigurationProperties
@PropertySource(value = "classpath:message.properties", encoding = "UTF-8")
public class CodeMessageConfiguration {

    private static Map<Integer, String> codeMessage = new HashMap<>();

    /**
     * 根据代码获取消息
     *
     * @param code 代码
     * @return {@link String} 消息
     */
    public static String getMessage(Integer code) {
        return codeMessage.get(code);
    }

    public Map<Integer, String> getCodeMessage() {
        return codeMessage;
    }

    public void setCodeMessage(Map<Integer, String> codeMessage) {
        CodeMessageConfiguration.codeMessage = codeMessage;
    }
}
