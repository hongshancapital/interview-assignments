package com.example.demo.model.utils;


import java.util.Arrays;

/**
 * @author wangxiaosong
 * @since 2022/1/10
 */
public class Base62 {

    private static final char[] Base62_Char = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

    private static final int[] From_Base62 = new int[128];

    private static final int RADIX = 62;

    static {
        Arrays.fill(From_Base62, -1);
        for (int i = 0; i < Base62_Char.length; i++) {
            From_Base62[Base62_Char[i]] = i;
        }
    }

    private Base62() {
    }

    /**
     * 编码
     */
    public static String encode(long hash32) {
        if (hash32 == 0) {
            return "A";
        }
        StringBuilder stringBuilder = new StringBuilder();
        while (hash32 > 0) {
            stringBuilder.append(Base62_Char[(int) (hash32 % RADIX)]);
            hash32 /= RADIX;
        }
        return stringBuilder.reverse().toString();
    }

    /**
     * 解码
     */
    public static long decode(String s) {
        long l = 0L;
        long d = 1L;
        for (int i = s.length() - 1; i >= 0; i--) {
            l = l + d * From_Base62[s.charAt(i)];
            d *= RADIX;
        }
        return l;
    }

}
