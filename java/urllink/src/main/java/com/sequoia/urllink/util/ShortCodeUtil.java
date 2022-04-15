package com.sequoia.urllink.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sequoia.urllink.base.exception.InvalidBusinessException;

import java.util.List;
import java.util.Set;

/**
 * 初始化值，从 15000000 开始。根据需要，可以打乱 DICT_CHARS。
 * 得到500亿之后，再重置为 15000000。
 * @author liuhai
 * @date 2022.4.15
 */
public class ShortCodeUtil {
    private static final char[] DICT_CHARS = new char[]{
            'E', 'V', 'j', 'z', '3', 'Q', 'L', 'p', 'x', 'b', 'U', 'D', 'Y', '5', 'v', 'S', 'h', 'i', '0', '4', 'w', 'r', 'K', '7',
            'c', 'm', 'k', 'G', '8', 'R', 'O', '2', 'y', 'u', 'M', 'F', 's', 'Z', 'I', '9', 'N', 'P', 'n', '6', 'A', 'q', 'B',
            'e', 'g', 't', 'C', '1', 'X', 'd', 'o', 'H', 'T', 'l', 'J', 'f', 'W', 'a'
    };

    private ShortCodeUtil() {
    }

    public static void main(String[] args) {
        double t1 = System.currentTimeMillis();
        List<String> codeList = genShortCode(15235584L, 1);
        codeList.addAll(genShortCode(56785000000L, 1));
        codeList.addAll(genShortCode(56785000000L + 15235584L - 1, 1));
        System.out.println(codeList);
        double t2 = System.currentTimeMillis();
        System.out.println(t2 - t1);

        Set<String> codeSet = Sets.newHashSet(codeList);
        System.out.println(codeSet.size() == codeList.size());
    }

    /**
     * 索引数值，转化为code
     *
     * @param arr
     * @return
     */
    private static String arrConvertStr(int[] arr) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0, len = arr.length; i < len; i++) {
            builder.append(DICT_CHARS[arr[i]]);
        }
        return builder.toString();
    }

    /**
     * 通过指定其实数值，生成数量，批量生成code
     *
     * @param start
     * @param size
     * @return
     */
    public static List<String> genShortCode(long start, int size) {
        int maxVal = DICT_CHARS.length;
        int[] arr = fixedStartPosition(start, 6);
        List<String> codeList = Lists.newArrayListWithCapacity(size);
        codeList.add(arrConvertStr(arr));
        for (int i = 1; i < size; i++) {
            arrAddOne(arr, maxVal);
            codeList.add(arrConvertStr(arr));
        }
        return codeList;
    }

    /**
     * 将开始数值，转化为DICT_CHARS数组索引
     *
     * @param start
     * @param len
     * @return
     */
    private static int[] fixedStartPosition(long start, int len) {
        int[] arr = new int[len];
        int maxNum = DICT_CHARS.length;
        long tmp = start;
        for (int i = len - 1; i >= 0; i--) {
            if (i < 1 && tmp >= maxNum) {
                throw new InvalidBusinessException(start + "：数值溢出！");
            }

            long val = tmp / maxNum;
            arr[i] = (int) (tmp % maxNum);
            if (val < maxNum && i > 0) {
                arr[i - 1] = (int) val;
                break;
            }
            tmp = val;
        }
        return arr;
    }

    private static void arrAddOne(int[] arr, int maxVal) {
        for (int i = arr.length - 1; i >= 0; i--) {
            arr[i]++;
            if (arr[i] < maxVal) {
                break;
            }
            arr[i] = 0;
        }
    }
}