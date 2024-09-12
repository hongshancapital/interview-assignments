package com.sequoia.web.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class Base62Test {
    @Test
    public void encode() {
        String base62Str = Base62.encode(19594855766L);
        Assert.assertEquals("LO616k", base62Str);
    }

}
