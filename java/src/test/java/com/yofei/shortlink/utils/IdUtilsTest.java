package com.yofei.shortlink.utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class IdUtilsTest {



    @Test
    void should_to_equals(){
        assertEquals("9", IdUtils.encode(9));
        assertEquals("9", IdUtils.toCode(9));
        assertEquals("a", IdUtils.encode(10));
        assertEquals("a", IdUtils.toCode(10));
    }

    @Test
    void should_to_no_equals(){
        assertEquals("90", IdUtils.encode(9));
        assertEquals("19", IdUtils.toCode(9));
        assertEquals("b", IdUtils.encode(10));
        assertEquals("b", IdUtils.toCode(10));
    }
}
