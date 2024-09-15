package com.icbc.gjljfl.util;

import java.util.UUID;

/**
 * @author : gaowh
 * @desc : 生成 uuid
 * @date : 2021/4/29
 * @time : 16:47
 * @contact:
 */
public class UUIDUtils {

    /**
     * 生成不带- 的 uuid的值
     *
     * @return
     */
    public static String generateId() {
        return UUID.randomUUID().toString().replace("-", "").trim();
    }
}
