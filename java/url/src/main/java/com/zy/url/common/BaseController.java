package com.zy.url.common;

import com.zy.url.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller基类
 */
@Slf4j
public class BaseController {
    private void logParams() {

    }

    protected static void logParams(Thread thread, Object... logs) {
        try {
            String clazz = thread.getStackTrace()[1].getClassName();
            String method = thread.getStackTrace()[1].getMethodName();
            StringBuilder logStr = new StringBuilder("class name: " + clazz + " Method Name " + method);
            String[] parms = new String[logs.length];
            for (int i = 0; i < logs.length; i++) {
                logStr.append(logs[i].getClass().getSimpleName()).append(":{}；");
                parms[i] = JsonUtils.Object2Json(logs[i]);
            }
            log.info(logStr.toString(), (Object[]) parms);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
