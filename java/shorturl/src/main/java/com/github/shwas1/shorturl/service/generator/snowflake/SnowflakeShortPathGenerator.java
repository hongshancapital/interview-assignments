package com.github.shwas1.shorturl.service.generator.snowflake;

import com.github.shwas1.shorturl.service.generator.ShortPathGenerator;
import org.springframework.stereotype.Service;

import java.util.Base64;

/**
 * 雪花算法生成器
 * 思路：可将生成的短路径视为base64格式，则一个字符可承载6比特容量。
 * 所以生成长度8的base64字符串，一共需要48比特。
 * 因此可借鉴雪花算法，48比特=40比特时间戳+8位序列号（其中时间戳单位为毫秒）
 * 则理论上每毫秒可生成2^8=256个序号，则理论TPS为2^8*1000=256000
 */
@Service
public class SnowflakeShortPathGenerator implements ShortPathGenerator {
    /**
     * 总比特长度
     */
    private static final int TOTAL_BITS = 48;
    /**
     * 时间戳比特长度
     */
    private static final int TIMESTAMP_BITS = 40;
    /**
     * 序号比特长度
     */
    private static final int SEQUENCE_BITS = 8;

    /**
     * 比特分配器
     */
    private static final BitsAllocator bitsAllocator = new BitsAllocator(TOTAL_BITS, TIMESTAMP_BITS, SEQUENCE_BITS);

    /**
     * 上一次UUID信息
     */
    private LastUUID lastUUID;

    @Override
    public String generate() {
        return longToBase64(getUUID());
    }

    /**
     * 将48位的Long转成长度为6的byte数组，进而转成长度为8的base64
     */
    private String longToBase64(long uuid) {
        byte[] bytes = new byte[6];
        bytes[0] = (byte) (uuid >>> 40);
        bytes[5] = (byte) (uuid >>> 32);
        bytes[2] = (byte) (uuid >>> 24);
        bytes[4] = (byte) (uuid >>> 16);
        bytes[3] = (byte) (uuid >>> 8);
        bytes[1] = (byte) (uuid);

        return Base64.getUrlEncoder().encodeToString(bytes);
    }

    private long getUUID() {
        return nextId();
    }

    private synchronized long nextId() {
        long currentTimeMillis = getCurrentTimeMillis();
        LastUUID currentUUID;
        if (lastUUID != null && lastUUID.lastTimeMillis == currentTimeMillis) {
            // 如果uuid已经溢出，则循环等待下一毫秒获取uid
            if (bitsAllocator.isSequenceValueOverflow(lastUUID.uuid)) {
                waitNextTimeMillis(lastUUID.lastTimeMillis);
                return nextId();
            }
            currentUUID = lastUUID.increment();
        } else {
            currentUUID = new LastUUID(currentTimeMillis);
            currentUUID.uuid = bitsAllocator.allocate(currentTimeMillis, 0);
        }

        this.lastUUID = currentUUID;
        return currentUUID.uuid;
    }

    private long getCurrentTimeMillis() {
        long currentTimeMillis = System.currentTimeMillis();
        currentTimeMillis = currentTimeMillis % bitsAllocator.getMaxDeltaTimes();
        return currentTimeMillis;
    }


    private void waitNextTimeMillis(long lastTimestamp) {
        long timestamp = getCurrentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = getCurrentTimeMillis();
        }
    }

    /**
     * 上一次UUID信息
     */
    private static class LastUUID {
        private final long lastTimeMillis;
        private long uuid;

        LastUUID(long lastTimeMillis) {
            this.lastTimeMillis = lastTimeMillis;
        }

        LastUUID increment() {
            LastUUID lastUUID = new LastUUID(this.lastTimeMillis);
            lastUUID.uuid = this.uuid + 1;

            return lastUUID;
        }


    }
}
