package com.sequoiacap.tinyurl.service;

import com.sequoiacap.tinyurl.exception.BadParamException;
import com.sequoiacap.tinyurl.service.impl.HashIdGenerator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class HashIdGeneratorTest {
    @Test
    void should_() {
        IdGenerator idGenerator = new HashIdGenerator();
        assertThrows(BadParamException.class, () -> idGenerator.checkId(""));
        assertThrows(BadParamException.class, () -> idGenerator.checkId("123"));
    }
}
