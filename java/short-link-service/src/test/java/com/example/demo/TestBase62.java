package com.example.demo;

import com.example.demo.utils.Base62;
import org.junit.Test;

/**
 * @author shenbing
 * @since 2022/1/8
 */
public class TestBase62 {

    /**
     * 3844 => 238327
     * 238328 => 14776335
     * 14776336 => 916132831
     * 916132832 => 56800235583
     */
    @Test
    public void test0() {
        System.out.printf("%d => %d%n", Base62.decode("BAA"), Base62.decode("999"));
        System.out.printf("%d => %d%n", Base62.decode("BAAA"), Base62.decode("9999"));
        System.out.printf("%d => %d%n", Base62.decode("BAAAA"), Base62.decode("99999"));
        System.out.printf("%d => %d%n", Base62.decode("BAAAAA"), Base62.decode("999999"));
    }

}
