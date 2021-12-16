package com.scdt.china.shorturl.repository.id;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IncrementIdGeneratorTest {

    @Test
    void nextId() {
        IncrementIdGenerator incrementIdGenerator = new IncrementIdGenerator(0, 4);
        Assertions.assertEquals(1, incrementIdGenerator.nextId());
        Assertions.assertEquals(2, incrementIdGenerator.nextId());
        Assertions.assertEquals(3, incrementIdGenerator.nextId());
        Assertions.assertEquals(4, incrementIdGenerator.nextId());
        Assertions.assertThrows(SequenceOverflowException.class, incrementIdGenerator::nextId);
    }

}