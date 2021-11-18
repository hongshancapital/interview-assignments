package com.youyuzuo.shortdn.util;

public class NumberUtil {

    final static char[] digits = {
            '0' , '1' , '2' , '3' , '4' , '5' ,
            '6' , '7' , '8' , '9' , 'a' , 'b' ,
            'c' , 'd' , 'e' , 'f' , 'g' , 'h' ,
            'i' , 'j' , 'k' , 'l' , 'm' , 'n' ,
            'p' , 'q' , 'r' , 's' , 't' , 'u' ,
            'v' , 'w' , 'x' , 'y' , 'z' , 'A' ,
            'B' , 'C' , 'D' , 'E' , 'F' , 'G' ,
            'H' , 'I' , 'J' , 'K' , 'L' , 'M' ,
            'N' , 'P' , 'Q' , 'R' , 'S' , 'T' ,
            'U' , 'V' , 'W' , 'X' , 'Y' , 'Z' ,
            '!' , '@' , '*' , '&' , '#' ,
            //'(' ,
            //')' , '[' , ']' , '{' , '}' , '|' ,
            //'<' , '>' ,
    };

    public static String toDigitsString(Long value){
        assert value > 0;
        long i = value.longValue();
        int radix = digits.length;
        char[] buf = new char[64];
        int charPos = buf.length -1;
        while (i >= radix) {
            buf[charPos--] = digits[(int)(i % radix)];
            i = i / radix;
        }
        return new String(buf, charPos+1, (buf.length - charPos -1));
    }
}
