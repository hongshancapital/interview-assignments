package com.wjup.shorturl.util;

import com.google.common.hash.*;

import java.nio.charset.Charset;
import java.util.Random;

public class ShortUtils {

    static int size = 1000000;//预计要插入多少数据
    static double fpp = 0.001;//期望的误判率
    //使用布隆过滤器
    //这里做一下解释  因为只是做一个服务demo  所以采用实际方案简单落地  如果将来实用  可使用Redis布隆过滤器功能  Redis自带有持久化功能
    static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    /**
     * 生成随机数字和字母
     *
     * @param length 生成长度
     * @return shortUrlId
     */
    public static String getStringRandom(int length) {

        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {

            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

    /**
     * 将长url转换为短url
     * @param longUrl
     * @return
     */
    public static String to62url(String longUrl){
        //MurmurHash算法
        HashFunction function= Hashing.murmur3_32();
        HashCode hashCode = function.hashString(longUrl, Charset.forName("utf-8"));
        //i为长url的murmur值
        int i = Math.abs(hashCode.asInt());
        //准备一个url在生成的murmur值重复时拼接字符串用
        String newurl=longUrl;
        //bo如果为true说明布隆过滤器中已存在
        boolean bo=bloomFilter.mightContain(i);
        while(bo)
        {
            newurl+="ALREADY";
            hashCode = function.hashString(newurl, Charset.forName("utf-8"));
            //使用拼接过字符串的url重新生成murmur值
            i = Math.abs(hashCode.asInt());
            bo=bloomFilter.mightContain(i);
        }
        //将murmur值放入布隆过滤器
        bloomFilter.put(i);
        //转成62进制位数更短
        String to62url = encode(i);
        return to62url;
    }


    /**
     * 将目标转换为62进制 位数更短
     * @param num
     * @return
     */
    public static String encode(long num) {
        String chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        int scale = 62;
        StringBuilder sb = new StringBuilder();
        int remainder = 0;
        while (num > scale - 1) {
            remainder = Long.valueOf(num % scale).intValue();
            sb.append(chars.charAt(remainder));
            num = num / scale;
        }
        sb.append(chars.charAt(Long.valueOf(num).intValue()));
        return sb.reverse().toString();
    }

}
