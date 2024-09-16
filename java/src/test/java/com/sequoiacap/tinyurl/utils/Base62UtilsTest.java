package com.sequoiacap.tinyurl.utils;

import org.junit.jupiter.api.Assertions;;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Base62UtilsTest {

    @Test
    void should_check_base62_true(){
        assertTrue(Base62Util.isValidBaseChar('A'));
    }
    @Test
    void should_check_base62_false(){
        assertFalse(Base62Util.isValidBaseChar('?'));
    }

    @Test
    void should_to_base(){
        assertEquals("a", Base62Util.toBase(10L, 1));
        assertEquals("12", Base62Util.toBase(64L, 2));
        assertEquals("2", Base62Util.toBase(64L, 1));
    }
}
