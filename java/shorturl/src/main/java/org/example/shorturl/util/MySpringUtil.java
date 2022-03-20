package org.example.shorturl.util;

import cn.hutool.extra.spring.SpringUtil;
import org.example.shorturl.constants.AppConstant;
import org.springframework.stereotype.Component;

/**
 * Spring 系统启动应用感知对象,主要用于获取Spring Bean的上下文对象,后续的代码中可以直接查找系统中加载的Bean对象。
 *
 * @author bai
 * @date 2021-01-14
 */
@Component
public class MySpringUtil
        extends SpringUtil {
    
    /**
     * 得到应用程序名称
     *
     * @return {@link String}
     */
    public static String getAppName() {
        return SpringUtil.getProperty(AppConstant.APP_NAME);
    }
}
