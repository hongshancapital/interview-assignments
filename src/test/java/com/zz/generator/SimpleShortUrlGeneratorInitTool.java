package com.zz.generator;


import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.zz.util.Constants;

/**
 * 生成初始随机的编码工具——临时使用
 *
 * @author zz
 * @version 1.0
 * @date 2022/3/31
 */
public class SimpleShortUrlGeneratorInitTool {

    public static void main(String[] args) throws Exception {
//        char[] cs = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
//
//        List<Character> list = new ArrayList<>();
//        for (char c : cs) {
//            list.add(c);
//        }
//        Random random = new Random();
//        StringBuilder sb = new StringBuilder();
//        while (list.size() > 1) {
//            int index = random.nextInt(list.size());
//            sb.append(list.remove(index));
//        }
//        sb.append(list.get(0));
//        System.out.println(sb);

//        MessageDigest md5 = MessageDigest.getInstance("md5");
//        byte[] digest = md5.digest("http:www.baidu.com".getBytes(StandardCharsets.UTF_8));
//        String s = new BigInteger(1, digest).toString(16);
//        System.out.println(s);

//        Calendar instance = Calendar.getInstance();
//        instance.set(2022, 01, 01, 0, 0, 0);
//        long time = instance.getTime().getTime();
//
//        System.out.println(new Date(instance.getTime().getTime() + (long) Math.pow(2, 40)));
//        long cur = System.currentTimeMillis();
//        System.out.println(cur - time);

        Cache<String, String> localCache = CacheBuilder.newBuilder().softValues().maximumSize(100).concurrencyLevel(Constants.CPU_CORE).build();
        localCache.put("htt", "123");
        System.out.println(localCache.getIfPresent("123"));
    }
}