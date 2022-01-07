package org.domain.util;

import com.google.common.primitives.Chars;
import org.springframework.util.StringUtils;

import java.util.List;

public class BinaryConversion {
    private static List<Character> sixtyTwoBinary = Chars.asList(generateSixtyTwoChars());

    private static char[] generateSixtyTwoChars() {
        char[] results = new char[62];
        for (char start = 'a', index = 0,  i = 0; i < 26; i++, index++) {
            results[index] = (char) (start + i);
        }

        for (Character start = 'A', index = 26, i = 0; i < 26; i++, index++) {
            results[index] = (char)(start + i);
        }

        for (Character start = '0', index = 52, i = 0; i < 10; i++, index++) {
            results[index] = (char)(start + i);
        }

        return results;
    }

    public static String tenToSixtyTwo(int i) {
        StringBuilder builder = new StringBuilder();
        for (int mod = i % 62, times = i; times != 0; mod = times % 62, times = times / 62) {
            builder.append(sixtyTwoBinary.get(mod));
        }
        builder.reverse();
        return builder.toString();
    }

    public static int sixtyTwoToTen(String str) {
        if (StringUtils.isEmpty(str)) {
            return 0;
        }
        int i = 0;
        for (char ch : str.toCharArray()) {
            if (!sixtyTwoBinary.contains(ch)) {
                throw new RuntimeException("invalid short url");
            }
            i = sixtyTwoBinary.indexOf(ch) * 62;

       }
       return i / 62;
    }
}
