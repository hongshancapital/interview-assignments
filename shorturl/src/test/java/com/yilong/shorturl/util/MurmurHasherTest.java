package com.yilong.shorturl.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

public class MurmurHasherTest {

    @Test
    public void testHashNull() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                MurmurHasher.hash(null);
            }
        });
    }

    @Test
    public void testHashEmpty() {
        Assertions.assertThrows(IllegalArgumentException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                MurmurHasher.hash("");
            }
        });
    }

    @Test
    public void testHash() {
        Assertions.assertEquals(1136789967, MurmurHasher.hash("https://www.vampire.com/url/looooooong/1"));
    }
}
