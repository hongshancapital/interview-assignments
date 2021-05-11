package com.example.shortlink.infrastructure.common;

import com.google.common.hash.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.UUID;

public final class IdGenerator {
    private static Logger logger = LoggerFactory.getLogger(IdGenerator.class);
    private static final BloomFilter<Integer> uniqueIdBloomFilter = BloomFilter.create(Funnels.integerFunnel(),
            Constants.BLOOM_FILTER_SIZE, Constants.BLOOM_FILTER_FPP);

    private IdGenerator() {
    }

    public static int getUniqueIdWithBloomFilter(String sourceLink) {
        int uniqueId = getUniqueId(sourceLink);
        //bloomFilter check
        boolean answer = uniqueIdBloomFilter.mightContain(uniqueId);
        if (!answer) {
            logger.info("bloomFilter check return false,uniqueId is {}", uniqueId);
            uniqueIdBloomFilter.put(uniqueId);
        } else {
            logger.warn("hash collision, uniqueId is {}", uniqueId);
            uniqueId = getUniqueId(sourceLink);
        }
        return uniqueId;
    }

    public static int getUniqueId(String sourceLink) {
        HashFunction hf = Hashing.murmur3_128();
        UUID uuid = UUIDHelper.generateType1UUID();
        String hashKey = uuid.toString() + sourceLink;
        HashCode hc = hf.hashString(hashKey,
                Charset.defaultCharset());
        int uniqueId = hc.hashCode();
        return uniqueId;
    }
}
