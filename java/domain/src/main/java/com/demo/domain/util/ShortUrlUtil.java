package com.demo.domain.util;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author fanzj
 * @Date 2022/4/2 11:25
 * @Version 3.0
 * @Description
 */
public class ShortUrlUtil {

    private static HazelcastInstance hazelcastInstance = SpringBeanUtil.getBean("hazelcastInstance", HazelcastInstance.class);

    private static Logger LOGGER = LoggerFactory.getLogger(ShortUrlUtil.class);

    /**
     * 域名地址-长短映射: 增加需求设置有效期，有效期可以存到v，或另开队列
     **/
    //private static Map<String, String> DOMAIN_URL_MAP = new LRUCache<>(MAX_CAPACITY);

    public static IMap<String, String> DOMAIN_URL_MAP = hazelcastInstance.getMap(Constant.MAP_NAME);

    /**
     * 加密混合的key
     **/
    private static final String KEY = "1qaz2wsx3edc";

    /**
     * Title:
     * Description: 根据短地址获取长地址
     *
     * @return
     * @date 2022/4/2-13:14
     */
    public static String getLongUrl(String longUrl) {
        //为空不做处理 可以exception
        return DOMAIN_URL_MAP.get(longUrl);
    }

    /**
     * Title:
     * Description: 根据长地址获取短地址
     * 1.将长网址用md5算法生成32位签名串，分为4段,，每段8个字符；
     * 2.对这4段循环处理，取每段的8个字符, 将他看成16进制字符串与0x3fffffff(30位1)的位与操作，超过30位的忽略处理；
     * 3.将每段得到的这30位又分成6段，每5位的数字作为字母表的索引取得特定字符，依次进行获得6位字符串；
     * 4.这样一个md5字符串可以获得4个6位串，取里面的任意一个就可作为这个长url的短url地址。
     *
     * @return String 短地址
     * @date 2022/4/2-13:14
     */
    public static String getShortUrl(String longUrl, String key) {
        String shortUrl = "";
        //1.存在已经生成，无须重新计算
        if (DOMAIN_URL_MAP.containsValue(longUrl)) {
            shortUrl = cacheKey(longUrl);
        }
        // 不为空，返回
        if (!"".equals(shortUrl)) {
            return shortUrl;
        }
        //2.生成6位码，取第一段数组值
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        key = key == null ? KEY : key;
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"
        };
        // 对传入网址进行 MD5 加密
        String md5EncryptResult = encrypt(key + longUrl);
        String hex = md5EncryptResult;
        String[] resUrl = new String[4];
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * 8, i * 8 + 8);
            // 这里需要使用 long 型来转换，因为 Inteper .parseInt() 只能处理 31 位 , 首位为符号位 , 如果不用 long ，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for (int j = 0; j < 6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }

            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
        }
        // 1.resUrl[0]是否已存在处理-单元测试难以覆盖
        for (int j = 0; j < resUrl.length; j++) {
            // 1.1短地址被占用处理
            if (DOMAIN_URL_MAP.containsKey(resUrl[j])) {
                continue;
            }
            // 2.没有被占用，就使用
            DOMAIN_URL_MAP.put(resUrl[j], longUrl);
            return resUrl[j];
        }
        // 如果四个都被占用,
        return getShortUrl(longUrl, UUID.randomUUID().toString());
    }

    public static synchronized String cacheKey(String longUrl) {
        for (Map.Entry<String, String> map : DOMAIN_URL_MAP.entrySet()) {
            if (map.getValue().equals(longUrl)) {
                // 返回key
                return map.getKey();
            }
        }
        return "";
    }

    /**
     * Title:
     * Description: salt+str
     *
     * @return
     * @date 2022/4/2-13:37
     */
    public static String encrypt(String dataStr) {
        try {
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte[] basic = m.digest();
            String result = "";
            for (int i = 0; i < basic.length; i++) {
                result += Integer.toHexString((0x000000FF & basic[i]) | 0xFFFFFF00).substring(6);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 验证是否是URL
     *
     * @param url
     * @return
     */
    public static boolean verifyUrl(String url) {
        // 为空
        if (StringUtils.isBlank(url)) {
            return false;
        }
        // URL验证规则,不考虑脚本类型路径，交给网关过滤
        String regEx = "[a-zA-z]+://[^\\s]*";
        // 编译正则表达式
        Pattern pattern = Pattern.compile(regEx);
        // 忽略大小写的写法
        // Pattern pat = Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(url);
        // 字符串是否与正则表达式相匹配
        boolean rs = matcher.matches();
        return rs;

    }

    public static int size() {
        return DOMAIN_URL_MAP.size();
    }
}
