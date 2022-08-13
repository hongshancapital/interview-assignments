package com.scdt.urlshorter.utils;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 短链接生成工具类
 *
 * @author mingo
 */
public class ConversionUtil {
    private ConversionUtil()
    {
    }

    /**
     * 初始化 62 进制数据，索引位置代表字符的数值，比如 A代表10，z代表61
     */
    private static final char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
            'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L',
            '9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
            'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    /**
     * 将字符串转为62进制
     *
     * @param originalUrl 原始字符串
     * @return 62进制字符串
     */
    public static String generate(String originalUrl) {
        String originalId = IdGeneratorUtil.generate(originalUrl);
        List<String> segmentCodeList = new ArrayList<>();
        for (int segment = 0; segment < 4; segment++)
        {
            //将生成出来的ID分割成8位一段，即4段
            int offset = segment % 8;
            String subSegment = originalId.substring(offset, offset + 8);
            long scale16 = Long.parseLong(subSegment, 16);
            //去掉高位的前两位，保留32位
            scale16 &= 0X3FFFFFFF;

            StringBuilder stringBuilder = new StringBuilder();
            for (int scale16SubSegment = 0; scale16SubSegment < 6; scale16SubSegment++)
            {
                long target = scale16 & 0x0000003D;
                stringBuilder.append(digits[(int) target]);
                scale16 >>= 5;
            }
            segmentCodeList.add(stringBuilder.toString());
        }
        try
        {
            //随机取一个62位码
            Random random = SecureRandom.getInstanceStrong();
            return segmentCodeList.get(random.nextInt(segmentCodeList.size()));
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将字符串转为62进制分段集合
     *
     * @param originalUrl 原始字符串
     * @return 62进制字符串集合
     */
    public static List<String> generateMultiCode(String originalUrl) {
        String originalId = IdGeneratorUtil.generate(originalUrl);
        List<String> segmentCodeList = new ArrayList<>();
        for (int segment = 0; segment < 4; segment++)
        {
            //将生成出来的ID分割成8位一段，即4段
            int offset = segment % 8;
            String subSegment = originalId.substring(offset, offset + 8);
            long scale16 = Long.parseLong(subSegment, 16);
            //去掉高位的前两位，保留32位
            scale16 &= 0X3FFFFFFF;

            StringBuilder stringBuilder = new StringBuilder();
            for (int scale16SubSegment = 0; scale16SubSegment < 6; scale16SubSegment++)
            {
                long target = scale16 & 0x0000003D;
                stringBuilder.append(digits[(int) target]);
                scale16 >>= 5;
            }
            segmentCodeList.add(stringBuilder.toString());
        }
        return segmentCodeList;
    }
}
