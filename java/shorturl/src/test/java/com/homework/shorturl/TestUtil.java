package com.homework.shorturl;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public class TestUtil {

    /**
     * generate long url
     *
     * @return long url
     */
    public static String randomLongUrl() {
        StringBuilder sb = new StringBuilder();
        sb.append("http://");
        sb.append(RandomStringUtils.random(RandomUtils.nextInt(1, 128)));
        sb.append("/");
        sb.append(RandomStringUtils.random(RandomUtils.nextInt(1, 1024)));
        sb.append("?");
        sb.append(RandomStringUtils.random(RandomUtils.nextInt(1, 1024)));
        sb.append("=");
        sb.append(RandomStringUtils.random(RandomUtils.nextInt(1, 1024)));
        return sb.toString();
    }
}
