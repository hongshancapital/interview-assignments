package translation.util;

import translation.model.ThePreTree;
import translation.model.UrlMapBean;

import java.security.MessageDigest;
import java.util.Random;
import java.util.UUID;

/**
 * 短连接生成工具
 *
 * @author: hello
 * @since: 2023/2/21
 */
public class ShortLongUrlUtils {

    /**
     * 会使用到的常量
     */
    public static final long LONG_0X3FFFFFFF_1073741823 = 0x3FFFFFFF;//1073741823
    public static final long LONG_0X0000003D_61 = 0x0000003D;//61
    public static final int LONG_0XFF_255 = 0xFF;//255

    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * 短域名读取接口：接受短域名信息，返回长域名信息。
     */
    public static void main(String[] args) {
        ThePreTree store = new ThePreTree();
        String longUrl = UUID.randomUUID().toString();
        String longUrlMap = UUID.randomUUID().toString();
        String shortUrl = longToShort(longUrl, null);
        UrlMapBean urlMapBean = new UrlMapBean(longUrl, shortUrl);
        urlMapBean.setCreatIime(DateUtil.getCurrentDateTime());
        store.addOrSet(shortUrl, urlMapBean);
        UrlMapBean urlMapBeanForMap = store.get(shortUrl);
        longUrlMap = urlMapBeanForMap.getLongUrl();
        System.out.println("短域名为:" + shortUrl + ";对应的长域明为:" + longUrlMap);
        //短域名为:ZbM7Rz;对应的长域明为:0de40bb3-c443-4b65-8bbc-de45521b917b
    }


    public static String longToShort(String longUrl, String theKey) {
        // 原始链接
        String sLongUrl = longUrl;
//        sLongUrl = UUID.randomUUID().toString();
//        System.out.println("长链接:" + sLongUrl);//长链接:0de40bb3-c443-4b65-8bbc-de45521b917b
        // 将产生4组6位字符串
        String[] aResult = shortUrl(sLongUrl, theKey);
        // 打印出结果
//        for (int i = Constants.INDEX_0; i < aResult.length; i++) {
//            System.out.println("[" + i + "]:" + aResult[i]);
//        }
        Random random = new Random();
        // 生成4以内随机数
        int j = random.nextInt(Constants.INDEX_4);
        String result = aResult[j];

        // 随机取一个作为短链
//        System.out.println("短链接:" + result);//短链接:ZbM7Rz
        result = new StringBuilder().append(getTheUUIDChar()).append(result).append(getTheUUIDChar()).toString();
//        System.out.println("拼接之后为:"+result);//拼接之后为:0cku4k3e
        return result;
    }

    public static char getTheUUIDChar() {
        Random random = new Random();
        return UUID.randomUUID().toString().charAt(random.nextInt(Constants.INDEX_36));
    }

    public static String[] shortUrl(String url, String keyDefined) {
        // 可以自定义生成 MD5 加密字符传前的混合 KEY
        String key = "trie_红杉";
        // 要使用生成 URL 的字符
        String[] chars = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
                "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A",
                "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
                "W", "X", "Y", "Z"
                , ".", "%", "@", "!"
        };
        // 对传入网址进行 MD5 加密
        String hex = md5ByHex((keyDefined == null ? key : keyDefined) + url);
//        System.out.println("加密之后的密文为:"+hex);//加密之后的密文为:2734ADD1F6210B34B350C87F4C3132F4
        String[] resUrl = new String[Constants.INDEX_4];
        for (int i = Constants.INDEX_0; i < Constants.INDEX_4; i++) {
            // 把加密字符按照 8 位一组 16 进制与 0x3FFFFFFF 进行位与运算
            String sTempSubString = hex.substring(i * Constants.INDEX_8, i * Constants.INDEX_8 + Constants.INDEX_8);
//            System.out.println("sTempSubString:"+sTempSubString);//sTempSubString:2734ADD1
            // 这里需要使用 long 型来转换，因为 Integer.parseInt() 只能处理 31 位 , 首位为符号位 ,如果不用long，则会越界
            long lHexLong = LONG_0X3FFFFFFF_1073741823 & Long.parseLong(sTempSubString, Constants.INDEX_16);
//            System.out.println("16进制转换后:"+lHexLong);//16进制转换后:657763793
            String outChars = "";
            for (int j = Constants.INDEX_0; j < Constants.INDEX_6; j++) {
                // 把得到的值与 0x0000003D 进行位与运算，取得字符数组 chars 索引
                long index = 61 & lHexLong;//LONG_0X0000003D_61
//                System.out.println("index:"+index); //index为:58
                // 把取得的字符相加
                outChars += chars[(int) index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> Constants.INDEX_5;
            }
            // 把字符串存入对应索引的输出数组
            resUrl[i] = outChars;
//            System.out.println("outChars:"+outChars);//outChars:Qzaa6z
        }
        return resUrl;
    }

    /**
     * MD5加密(32位大写)
     *
     * @param src 原始字符串
     * @return 加密之后的字符串
     */
    private static String md5ByHex(String src) {
        String result = null;
        try {
//            System.out.println("原始字符串:"+src);//原始字符串:trie_红杉0de40bb3-c443-4b65-8bbc-de45521b917b
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] b = src.getBytes();
            md.reset();
            md.update(b);
            byte[] hash = md.digest();
//            System.out.println("hash.length:"+hash.length);//hash.length:16
            StringBuffer buffer = new StringBuffer();
            String stmp = "";
            for (int i = Constants.INDEX_0; i < hash.length; i++) {
                stmp = Integer.toHexString(hash[i] & LONG_0XFF_255);
                if (stmp.length() == Constants.INDEX_1) {
                    buffer.append("0" + stmp);
                } else {
                    buffer.append(stmp);
                }
            }
            result = buffer.toString().toUpperCase();
//            System.out.println("加密之后的字符传为:"+result);//加密之后的字符传为:2734ADD1F6210B34B350C87F4C3132F4
            return result;
        } catch (Exception e) {
            return "";
        }
    }
}
