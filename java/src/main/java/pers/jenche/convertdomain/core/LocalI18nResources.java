package pers.jenche.convertdomain.core;

import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 *  Copyright (c) 2020 By www.jenche.cn
 * @author jenche E-mail:jenchecn@outlook.com
 * @date 2020/5/12 13:17
 * @description 国际化支持
 */
public class LocalI18nResources {
    private static LocalI18nResources instance;

    public static LocalI18nResources getInstance() {
        if (instance == null) {
            instance = new LocalI18nResources();
        }
        return instance;
    }


    /**
     * 获取国际化信息
     * @param key {@link String} 键
     * @param params {@link Object} 参数
     * @return {@link String} 返回字符串
     */
    public String getMessage(String key, Object... params) {
        //Locale locale = LocaleContextHolder.getLocale(); //根据环境获取
        Locale locale = Locale.SIMPLIFIED_CHINESE;  //可以配置成在配置文件中设定，尚未补全
        ResourceBundle message = SystemConfig.LANG_MESSAGE.get(locale.getLanguage());
        if (message == null) {
            synchronized (SystemConfig.LANG_MESSAGE) {
                //在这里读取配置信息
                message = SystemConfig.LANG_MESSAGE.get(locale.getLanguage());
                if (message == null) {
                    //注1
                    message = ResourceBundle.getBundle("i18n/messages", locale);
                    SystemConfig.LANG_MESSAGE.put(locale.getLanguage(), message);
                }
            }
        }
        //此处获取并返回message
        if (params != null) {
            String _msg;
            _msg = new String(message.getString(key).getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
            return String.format(_msg, params);
        }
        return message.getString(key);
    }

    /**
     * 清除国际化信息
     */
    public void flushMessage() {
        SystemConfig.LANG_MESSAGE.clear();
    }
}
