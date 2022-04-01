package com.zz.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * @author zz
 * @version 1.0
 * @date 2022/4/1
 */
public class ConvertToShortCodeTest {

    @Test
    public void digitTo62Code() {
        List<Integer> integers = ConvertToShortCode.digitTo62Code(10000);
        int[] res = new int[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            res[i] = integers.get(i);
        }
        Assert.assertArrayEquals(res, new int[]{2, 37, 18});
    }

    @Test
    public void digitTo62Code_1() {
        List<Integer> integers = ConvertToShortCode.digitTo62Code(1);
        int[] res = new int[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            res[i] = integers.get(i);
        }
        Assert.assertArrayEquals(res, new int[]{1});
    }

    @Test
    public void digitTo62Code_0() {
        List<Integer> integers = ConvertToShortCode.digitTo62Code(0);
        int[] res = new int[integers.size()];
        for (int i = 0; i < integers.size(); i++) {
            res[i] = integers.get(i);
        }
        Assert.assertArrayEquals(res, new int[]{0});
    }
}