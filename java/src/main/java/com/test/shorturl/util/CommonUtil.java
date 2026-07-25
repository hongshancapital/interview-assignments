package com.test.shorturl.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class CommonUtil {
    private static final Logger logger = LoggerFactory.getLogger(CommonUtil.class);
    public static String original = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    /**
     * 随机打乱打乱a-zA-Z0-9 62个字符的顺序
     *
     * @param original
     * @return
     */
    public static String shuffle(String original) {
        char[] c = original.toCharArray();
        List<Character> lst = new ArrayList<Character>();
        for (int i = 0; i < c.length; i++) {
            lst.add(c[i]);
        }
        Collections.shuffle(lst);
        Iterator<Character> iterator = lst.iterator();
        StringBuilder sb = new StringBuilder();
        while (iterator.hasNext()) {
            Character ch = iterator.next();
            sb.append("'").append(ch).append("'");
            if (iterator.hasNext()) {
                sb.append(",");
            }
        }
        String shuffer = sb.toString();
        logger.info(shuffer);
        return shuffer;
    }
}
