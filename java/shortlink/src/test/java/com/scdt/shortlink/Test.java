package com.scdt.shortlink;

import org.apache.tomcat.util.buf.StringUtils;

import java.util.ArrayList;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        List<String> chCol = new ArrayList<>();
        char ch = 'a';
        char ch2 = 'A';
        char ch3 = '0';
        for (int i = 0; i < 26; i++) {
            chCol.add(String.valueOf(ch++));
            chCol.add(String.valueOf(ch2++));
            if (i < 10) {
                chCol.add(String.valueOf(ch3++));
            }
        }
        List<String> res = new ArrayList<>();
        int last = 62;
        while (last > 0) {
            int index = (int) (System.currentTimeMillis() % last--);
            res.add(chCol.remove(index));
        }

        System.out.println(StringUtils.join(res, ','));
    }
}
