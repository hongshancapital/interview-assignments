package demo.oriURL.service;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * ID 生成器
 */
public class URLIdGenerator {
    // 初始值为随意填写，如果从 0 开始会生成一位的短链接，可以结合实际情况调整
    private static final AtomicInteger id = new AtomicInteger(46656);

    public static int getId() {
        return id.incrementAndGet();
    }
}
