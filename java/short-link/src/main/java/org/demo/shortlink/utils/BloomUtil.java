package org.demo.shortlink.utils;

import java.util.BitSet;

/**
 * @author wsq
 * @date 2022/3/26 002614:41
 * @description:
 */

public class BloomUtil {
    /**
     * 位数组大小
     */
    private static final int DEFAULT_SIZE = 2 << 24;

    /**
     *位数组，存0或1
     */
    private final BitSet bits = new BitSet(DEFAULT_SIZE);

    /**
     * 随机哈希函数映射数组，6个哈希函数
     */
    private static final int[] RANDOMS = {3,13,46,71,91,134};

    /**
     * 存放hash函数类的数组
     */
    private final RandomHash[] func = new RandomHash[RANDOMS.length];

    private static final BloomUtil INSTANCE = new BloomUtil();

    /**
     * 初始化随机  hash 函数
     */
    public BloomUtil(){
        for (int i = 0; i < RANDOMS.length; i++) {
            func[i] = new RandomHash(DEFAULT_SIZE, RANDOMS[i]);
        }
    }

    public static BloomUtil getInstance() {
        return INSTANCE;
    }

    public void add(Object value){
        for(RandomHash f:func){
            bits.set(f.hash(value),true);
        }
    }

    public boolean contains(Object value){
        boolean ret = true;
        for(RandomHash f: func){
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    /**
     * hash 操作类
     */
    static class RandomHash
    {
        private int cap;
        private int seed;

        public RandomHash(int cap , int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        /**
         * 计算 hash 值
         * @param value
         * @return
         */
        public int hash(Object value) {
            int h;
            return (value == null) ? 0 : Math.abs(seed * (cap - 1) & ((h = value.hashCode()) ^ (h >>> 16)));
        }
    }
}
