package com.scdt.url.common.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * Util class for generating unique IDs based on UUID.
 */
@UtilityClass
public final class UuidGenerator {

    public static String newUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
