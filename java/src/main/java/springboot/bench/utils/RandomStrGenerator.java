package springboot.bench.utils;

import java.util.Random;

public class RandomStrGenerator {
    
    public static final int SHORT_DOMAIN_LEN = 8;
    public static final int LONG_DOMAIN_LEN = 20;
    private static final String STR_DICT = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int DICT_SIZE = STR_DICT.length();
    
    public static String genShortDomain() {
        return getRandomString(SHORT_DOMAIN_LEN);
    }
    
    public static String getLongDomain() {
        return getRandomString(LONG_DOMAIN_LEN);
    }
    
    private static String getRandomString(int length) {
        Random random = new Random();
        StringBuffer sb = new StringBuffer("http://");
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(DICT_SIZE);
            sb.append(STR_DICT.charAt(number));
        }
        return sb.toString();
    }

}
