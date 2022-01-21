package indi.zixiu.shortdomainname.domain;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DomainName {
    private static final String BASE62_DIGITS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SHORT_DOMAIN_NAME_IN_BASE62_MAX_LENGTH = 8;

    private long shortName;
    private String longName;
    private int createdTime;  // 创建该域名的时间，Unix 时间戳，单位为秒

    /**
     * 检查一个域名是否合法，只检查域名的文本结构，不检查该域名是否是在某域名服务商注册的域名
     *
     * 合法域名必须满足以下条件：
     * 1. The domain name should be a-z or A-Z or 0-9 and hyphen (-).
     * 2. The domain name should be between 1 and 63 characters long.
     * 3. The domain name should not start or end with a hyphen(-) (e.g. -geeksforgeeks.org or geeksforgeeks.org-).
     * 4. The last TLD (Top level domain) must be at least two characters and a maximum of 6 characters.
     * 5. The domain name can be a subdomain (e.g. contribute.geeksforgeeks.org).
     * 6. The domain name must have at least 2 levels
     *
     * 参考：https://www.geeksforgeeks.org/how-to-validate-a-domain-name-using-regular-expression/
     *
     * @param domainName 域名
     * @return true：域名合法；false: 域名不合法。当 domainName 为 null 时，返回 false
     */
    public static boolean isValidDomainName(String domainName) {
        if (domainName == null) {
            return false;
        }

        String validDomainNameRegex = "^((?!-)[A-Za-z0-9-]" + "{1,63}(?<!-)\\.)" + "+[A-Za-z]{2,6}";
        Pattern pattern = Pattern.compile(validDomainNameRegex);
        Matcher matcher = pattern.matcher(domainName);
        return matcher.matches();
    }

    /**
     * 将短域名从 long 类型转换成 62 进制
     *
     * @param shortNameInLong long 类型短域名，必须为非负数
     * @return 62 进制的短域名
     * @throws IllegalArgumentException shortNameInLong 为负数
     */
    public static String convertShortNameInLongToInBase62(long shortNameInLong) throws IllegalArgumentException {
        if (shortNameInLong < 0) {
            throw new IllegalArgumentException("'shortNameInLong' 参数应该为非负数，实际值：" + shortNameInLong);
        }

        StringBuilder shortNameInBase62Builder = new StringBuilder();
        do {
            int mod = (int)(shortNameInLong % 62);
            shortNameInBase62Builder.insert(0, BASE62_DIGITS.charAt(mod));
            shortNameInLong = shortNameInLong / 62;
        } while (shortNameInLong != 0);
        return shortNameInBase62Builder.toString();
    }

    /**
     * 将短域名从 62 进制转换成 long 类型
     *
     * @param shortNameInBase62 62 进制短域名，必须是 1-8 位长的 62 进制数
     * @return long 类型短域名
     *
     * @throws IllegalArgumentException shortNameInBase62 不是 1-8 位长的 62 进制数
     */
    public static long convertShortNameInBase62ToInLong(String shortNameInBase62) throws IllegalArgumentException {
        validateShortNameInBase62(shortNameInBase62);
        long shortNameInLong = 0;
        for (char c : shortNameInBase62.toCharArray()) {
            shortNameInLong = shortNameInLong * 62 + BASE62_DIGITS.indexOf(c);
        }
        return shortNameInLong;
    }

    /**
     * 检查 62 进制短域名是否合法，即是否是 1-8 位长的 62 进制数
     *
     * @param shortNameInBase62 62 进制短域名
     * @throws IllegalArgumentException shortNameInBase62 不是合法的 62 进制短域名
     */
    private static void validateShortNameInBase62(String shortNameInBase62) throws IllegalArgumentException  {
        boolean isValidShortNameInBase62 = true;
        if (shortNameInBase62 == null) {
            isValidShortNameInBase62 = false;
        } else if (shortNameInBase62.length() < 1 || shortNameInBase62.length() > SHORT_DOMAIN_NAME_IN_BASE62_MAX_LENGTH) {
            isValidShortNameInBase62 = false;
        } else {
            for (char c : shortNameInBase62.toCharArray()) {
                if (BASE62_DIGITS.indexOf(c) == -1) {
                    isValidShortNameInBase62 = false;
                    break;
                }
            }
        }
        if (!isValidShortNameInBase62) {
            throw new IllegalArgumentException("'shortNameInBase62' 参数应该是 1-8 位长的 62 进制数，实际值：" + shortNameInBase62);
        }
    }

    public DomainName(long shortName, String longName, int createdTime) {
        this.shortName = shortName;
        this.longName = longName;
        this.createdTime = createdTime;
    }

    public long getShortName() {
        return shortName;
    }

    public void setShortName(long shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public int getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(int createdTime) {
        this.createdTime = createdTime;
    }
}
