package com.example.shortlink.infrastructure;

import com.example.shortlink.infrastructure.common.IdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class IdGeneratorTest {

    String sourceUrl = "http://www.didi.com";
    @Test
    public void should_success_when_generate_UniqueId(){
        int uid1 = IdGenerator.getUniqueIdWithBloomFilter(sourceUrl);
        int uid2 = IdGenerator.getUniqueIdWithBloomFilter(sourceUrl);
        assertNotEquals(uid1,uid2);
    }
}
