package com.cyh.assignment.util;

import com.cyh.assignment.constant.Constants;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class DomainIndexGeneratorUtil {

    // 选择一个随机数作为起始标号
    private static final Random random = new Random();
    private static final AtomicLong DOMAIN_INDEX = new AtomicLong(Math.abs(random.nextLong()) % Constants.MAX_ID);

    public static Long generateNextDomainIndex() {
        return DOMAIN_INDEX.addAndGet(Constants.INCREMENT)  % Constants.MAX_ID;
    }

}
