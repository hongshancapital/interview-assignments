package com.sequoia.shorturl.common.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Base62Test {

    @Test
    public void encode() {
        String base62Str = Base62.encode(26548315647L);
        assertEquals("Syg1fT", base62Str);
    }

    @Test
    public void encode2() {
        String base62Str = Base62.encode(13546548954L);
        assertEquals("EmlwD4", base62Str);
    }

}