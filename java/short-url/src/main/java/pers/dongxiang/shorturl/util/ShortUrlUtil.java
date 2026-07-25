package pers.dongxiang.shorturl.util;


import java.util.Arrays;
import java.util.Stack;

/**
 * @ProjectName short-url
 * @Package pers.dongxiang.shorturl.util
 * @ClassName ShortUrlUtil
 * @Description 短地址生成工具
 * @Company lab
 * @Author dongxiang
 * @Date 10/31/2021 1:32 PM
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
public class ShortUrlUtil {


    //62进制
    private static int BASE_NUM = 62;

    private static int LEN = 8;

    //62进制字母,打乱
    public static final char[] array = {'G', 'q', 'w', '0', 'H', 'e', 'T', 'F', '9', 'r', 'V', 't', 'y', 'u', 'N', 'i', '6', 'D', 'o', 'p', 'L', 'a', 's', 'd', 'K', 'f', 'g', 'h', 'j', 'k', '4', 'l', 'z', 'x', 'c', 'v', 'b', 'S', 'n', 'm', '1', 'Z', '3', '5', 'Q', 'W', 'E', 'R', '7', 'Y', 'U', 'I', 'O', '2', 'P', 'A', 'J', 'X', 'C', 'B', '8', 'M'};

    /**
     * 校验短链是否正确
     * @param shortUrl
     * @return
     */
    public static boolean checkShortUrl(String shortUrl) {
        if(shortUrl==null) {
            return false;
        }
        if(shortUrl.length() != LEN) {
            return false;
        }
        char charAt = shortUrl.charAt(LEN-1);
        String substring = shortUrl.substring(0,LEN - 1);
        char checkCode = getCheckCode(substring);
        if(charAt == checkCode) {
            return true;
        }
        return false;
    }

    private static char getCheckCode(CharSequence str){
        int len = str.length();
        int count = 0;
        char[] sortArray = Arrays.copyOf(array,array.length);
        Arrays.sort(sortArray);
        for (int i=0;i<len;++i){
            char t = str.charAt(i);
            int val = Arrays.binarySearch(sortArray,str.charAt(i));
            count += val;
        }
        count = count % BASE_NUM;
        return array[count];
    }

    /**
     * 将10进制数转为62进制字符串(短网址)
     *
     * @param number
     * @return
     */
    public static String getShortUrlByLongNum(Long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        if (0 == rest) {
            return String.valueOf(array[0]);
        }
        while (rest != 0) {
            stack.add(array[new Long((rest - (rest / BASE_NUM) * BASE_NUM)).intValue()]);
            rest = rest / BASE_NUM;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        result.append(getCheckCode(result));
        return result.toString();
    }


    public static Long getLongNumByShortUrl(String originUrl){
        Long id = IdUtil.nextId();
        return id;
    }




}
