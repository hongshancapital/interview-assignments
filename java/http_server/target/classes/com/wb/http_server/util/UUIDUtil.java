package com.wb.http_server.util;

import java.util.UUID;

/**
 * @author bing.wang
 * @date 2021/1/15
 */

public class UUIDUtil {
    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","").toUpperCase();
    }
}
