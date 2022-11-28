package com.jinblog.shorturl.service.impl;


import com.jinblog.shorturl.config.ShortConfiguration;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 字符生成器
 * 相对IntegerGenerator会慢，但是数量可以扩展
 */
public class CharacterGenerator extends AbstractGenerator {
    public static final char[] CHARS = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h',
            'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    public static final Map<Object, Short> CHARS_MAP = new HashMap<>();
    static {
        for (int i = 0; i < CHARS.length; i++) {
            CHARS_MAP.put(CHARS[i], (short)i);
        }
    }

    private char[] box;
    private short[] boxIndex;
    private volatile boolean outOfBox = false;
    public CharacterGenerator(int urlMaxLen) {
        super(urlMaxLen);
        box = new char[urlMaxLen];
        boxIndex = new short[urlMaxLen];
        Arrays.fill(box, CHARS[0]);
        Arrays.fill(boxIndex, (short) 0);
    }

    @Override
    public String generate() {
        if (outOfBox) return null;
        String ans = null;
        synchronized (box) {
            ans = new String(box);
            boolean plus = true;
            for (int i = boxIndex.length - 1; i >= 0; i--) {
                if (!plus) break;
                if (++boxIndex[i] >= CHARS.length) {
                    box[i] = CHARS[0];
                    boxIndex[i] = 0;
                    plus = true;
                } else {
                    box[i] = CHARS[boxIndex[i]];
                    plus = false;
                }
            }
            if (plus) {
                outOfBox = true;
            }
        }
        return ans;
    }

    @Autowired
    private ShortConfiguration shortConfiguration;

    public void setShortConfiguration(ShortConfiguration shortConfiguration) {
        this.shortConfiguration = shortConfiguration;
    }

    @Override
    public boolean validate(String url) {
        if (url == null) {
            return false;
        }
        if (!url.startsWith(shortConfiguration.getShortUrlDomain())) {
            return false;
        }
        return url.length() == shortConfiguration.getShortUrlDomain().length() + shortConfiguration.getUrlMaxLen();
    }
}
