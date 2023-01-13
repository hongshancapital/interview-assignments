package com.example.tinyurl;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

/**
 * Tiny Url Application Tests
 * @author hermitriver
 */
public class TinyUrlApplicationTests {
    /** Test main entry of tiny url application */
    @Test
    public void testMain() {
        TinyUrlApplication.main(new String[] {""});
        Assert.assertEquals(true, true); // Confirm no Exception
    }
}
