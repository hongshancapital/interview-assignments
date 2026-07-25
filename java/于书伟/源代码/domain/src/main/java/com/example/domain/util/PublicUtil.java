package com.example.domain.util;


import java.util.UUID;

import static com.example.domain.util.Constant.CONSTANT_ARRAY_A_Z0_9;

/**
 * 工具方法
 */
public class PublicUtil {

    /**
     * 模拟获取8位UUID
     * @return
     */
    public static String generateShortUuid() {
        StringBuffer shortBuffer = new StringBuffer();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        for (int i = 0; i < 8; i++) {
            String str = uuid.substring(i * 4, i * 4 + 4);
            int x = Integer.parseInt(str, 16);
            shortBuffer.append(CONSTANT_ARRAY_A_Z0_9[x % 0x3E]);
        }
        return shortBuffer.toString();
    }

}
