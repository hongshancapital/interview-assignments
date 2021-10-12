package com.jk.shorturl.utils;

import com.jk.shorturl.Utils.Base62RadixUtil;
import com.jk.shorturl.Utils.SequenceUtil;
import org.junit.jupiter.api.Test;

class Base62RadixUtilTest {
    @Test
    void TestId() {
        long id = SequenceUtil.getNextId() + 1;
        System.out.println("id="+id);
        System.out.println("id的哈希="+Base62RadixUtil.to62RadixString(id));
        System.out.println("0的哈希="+Base62RadixUtil.to62RadixString(0l));
    }
}
