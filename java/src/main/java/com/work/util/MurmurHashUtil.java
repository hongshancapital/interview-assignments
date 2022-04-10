package com.work.util;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

public class MurmurHashUtil {
    public static String getHashStr(String str) {
        return Hashing.murmur3_32().hashString(str, Charsets.UTF_8).toString();
    }
}
