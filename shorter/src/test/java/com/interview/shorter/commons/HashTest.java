package com.interview.shorter.commons;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;

import static org.junit.Assert.assertNotNull;

/**
 * @author Bai Lijun mailTo: 13910160159@163.com
 * Created at 2021-04-23
 */
@SpringBootTest
public class HashTest {
    @Test
    public void testMirror() {
        String source = "www.b.com";
        long hash = Hash.mirror(null);
        Assert.assertEquals(0L, hash);

        long r2 = Hash.mirror(source.getBytes(StandardCharsets.UTF_8));
        assertNotNull(r2);
    }

    @Test
    public void testnull() {
        long r1 = Hash.longHash(null, 0, 1);
        Assert.assertEquals(0, r1);

        long r2 = Hash.longHash(null, 0, 1);
        Assert.assertEquals(0, r2);

        long r3 = Hash.trans(null);
        Assert.assertEquals(0, r3);

        long r4 = Hash.longHash(null);
        Assert.assertEquals(0, r4);

        long r5 = Hash.longHash(null, 0, 1);
        Assert.assertEquals(0, r5);

        long r6 = Hash.transMirror(null);
        Assert.assertEquals(0, r6);


    }
}
