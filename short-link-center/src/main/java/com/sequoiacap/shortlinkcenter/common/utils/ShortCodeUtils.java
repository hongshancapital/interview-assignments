package com.sequoiacap.shortlinkcenter.common.utils;

/**
 * @author h.cn
 * @date 2022/3/17
 */
public class ShortCodeUtils {

    /**
     * 默认短链编码
     */
    private static final String EMPTY_STR = "";

    /**
     * 非标准的BASE64编码表
     */
    private static char[] BASE64CODE_NON_STANDARD = new char[]{
            'A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
            'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z',
            '0','1','2','3','4','5','6','7','8','9','-','+'
    };

    /**
     * 编码url
     * @param randomArr 随机数
     * @return 编码（base64字符集）
     */
    public static String encodeShortUrl(int[] randomArr) {
        if (randomArr == null || randomArr.length <=0) {
            return EMPTY_STR;
        }
        StringBuilder codeBuilder = new StringBuilder();
        for (int index : randomArr) {
            codeBuilder.append(BASE64CODE_NON_STANDARD[index]);
        }
        return codeBuilder.toString();
    }

    /**
     * 批量生成0-63随机数（<=0 , 默认6位数）
     * @param num 随机数数量
     * @return 随机数数组
     */
    public static int[] batchShortUrlRandom(int num) {
        num = num <= 0 ? 6 : num;
        int[] randomArr = new int[num];
        for (int i=0; i<num; i++) {
            randomArr[i]=RandomUtils.nextInt(0,63);
        }
        return randomArr;
    }

    /**
     * 生成url编码
     * @param num 生成大小位数（<=0 , 默认6位数）
     * @return 编码
     */
    public static String generateShortUrl(int num) {
        int[] randomArr = batchShortUrlRandom(num);
        return encodeShortUrl(randomArr);
    }
}
