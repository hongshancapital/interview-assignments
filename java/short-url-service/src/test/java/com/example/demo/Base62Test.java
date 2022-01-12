package com.example.demo;

import com.example.demo.model.utils.Base62;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class Base62Test {

    @Test
    public void encode() {
        String base62Str = Base62.encode(26548315647L);
        assertEquals("c8qBpd", base62Str);
    }

    @Test
    public void encode2() {
        String base62Str = Base62.encode(13546548954L);
        assertEquals("Owv6NE", base62Str);
    }

}