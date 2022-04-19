package com.mortimer.shortenurl.component.sequence;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class SimpleSequenceGeneratorTest {
    @Test
    public void test() {
        long initVal = 100;
        SimpleSequenceGenerator sequenceGenerator = new SimpleSequenceGenerator(initVal);
        Assertions.assertEquals(initVal, sequenceGenerator.nextId());
    }
}
