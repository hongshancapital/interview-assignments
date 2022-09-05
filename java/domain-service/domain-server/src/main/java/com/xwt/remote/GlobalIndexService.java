package com.xwt.remote;

import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 分片器服务，生成全局唯一的序号
 *
 * @author xiwentao
 */
public class GlobalIndexService {
    /**
     * 步长，等于broker实例个数
     *
     * @return:
     */
    private final static int STEP = 5;

    private GlobalIndexService() {
    }

    private static AtomicLong atomicLong_1 = new AtomicLong();
    private static AtomicLong atomicLong_2 = new AtomicLong();
    private static AtomicLong atomicLong_3 = new AtomicLong();
    private static AtomicLong atomicLong_4 = new AtomicLong();
    private static AtomicLong atomicLong_5 = new AtomicLong();

    private static ConcurrentMap<Integer, AtomicLong> proxyMap = new ConcurrentHashMap<>();

    private static Random r = new Random();


    /**
     * 实例1的发号器
     */
    private static AtomicLong getGlobalIndex_1() {
        if (atomicLong_1.get() == 0) {
            atomicLong_1.set(1);
        }

        return atomicLong_1;
    }

    /**
     * 实例2的发号器
     */
    private static AtomicLong getGlobalIndex_2() {
        if (atomicLong_2.get() == 0) {
            atomicLong_2.set(2);
        }

        return atomicLong_2;
    }

    /**
     * 实例3的发号器
     */
    private static AtomicLong getGlobalIndex_3() {
        if (atomicLong_3.get() == 0) {
            atomicLong_3.set(3);
        }

        return atomicLong_3;
    }

    /**
     * 实例4的发号器
     */
    private static AtomicLong getGlobalIndex_4() {
        if (atomicLong_4.get() == 0) {
            atomicLong_4.set(4);
        }

        return atomicLong_4;
    }

    /**
     * 实例5的发号器
     */
    private static AtomicLong getGlobalIndex_5() {
        if (atomicLong_5.get() == 0) {
            atomicLong_5.set(5);
        }

        return atomicLong_5;
    }

    /**
     * 实例节点代理，和负载均衡
     *
     * @param
     * @date: 2021-07-23
     * @return: java.lang.Long
     */
    public static Long getGlobalIndexProxy() {
        if (proxyMap.isEmpty()) {
            proxyMap.put(1, getGlobalIndex_1());
            proxyMap.put(2, getGlobalIndex_2());
            proxyMap.put(3, getGlobalIndex_3());
            proxyMap.put(4, getGlobalIndex_4());
            proxyMap.put(5, getGlobalIndex_5());
        }

        int random = r.nextInt(100);
        int brokerId = (random % STEP);
        if (brokerId <= 0) {
            brokerId = 1;
        }
        long index = proxyMap.get(brokerId).addAndGet(STEP);
        return index;
    }


}
