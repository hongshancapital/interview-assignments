package city.yyds.job.hsdemo.service.impl;

import city.yyds.job.hsdemo.mdb.DnsMemoryDB;
import city.yyds.job.hsdemo.service.ShortUrlService;
import city.yyds.job.hsdemo.utils.ConstUtil;
import org.junit.platform.commons.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Value("${short.url.prefix}")
    private String shortUrlPrefix;
    @Value("${short.url.timeout}")
    private Long shortUrlTimeout;

    /*
    * RFC3986文档规定，Url中只允许包含以下四种：
    *              1、数字（0-9）
    *              2、英文字母（a-zA-Z）
    *              3、-_.~ 4个特殊字符
    *              4、所有保留字符，RFC3986中指定了以下字符为保留字符（英文字符）：! * ' ( ) ; : @ & = + $ , / ? # [ ]
    * 本系统中采用数字、英文字母及-_作为短码的字符，共64个
    * */
    final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            '-', '_'};

    /**
     * 转短码的实现方法.
     * <p>
     *     获得请求后，先对请求的URL进行MD5进行摘要，<br>
     *     然后根据此摘要从Redis里查找是不是已经存在，如果存在就直接返回<br>
     *     如果不存在，从Redis中获取序列号再经过64进制转换生成短码并返回
     * </p>
     * @param url 需要转化的URL字符串
     * @return 没有返回值
     */
    @Override
    public String getShortUrl(String url) {
        if(StringUtils.isBlank(url)) {
            return null;
        }
        String regex = "(ht|f)tp(s?)\\:\\/\\/[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(\\/?)([a-zA-Z0-9\\-\\.\\?\\,\\'\\/\\\\&%\\+\\$#_=]*)?";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url.trim());
        if(!matcher.matches()){
            return null;
        }
        //综合考虑速度、hash冲突、又避免导致Redis的key太长等因素，采用md5算法，如果实际请求数量大但并发低的话可以考虑sha1等算法
        final String md5 = DigestUtils.md5DigestAsHex(url.getBytes());
        //为了提高反应速度，先不加锁在Redis里取一次短码
        //String shortUrl = operations.get(ConstUtil.LONG_SHORT_KEY_PREFIX + md5);
        String l2sKey = new StringBuffer(ConstUtil.LONG_SHORT_KEY_PREFIX ).append(md5).toString();
        String shortUrl = DnsMemoryDB.selectRecord(l2sKey);
        if (shortUrl == null){
            synchronized (md5.intern()){
                //如果没取到，加锁后再从Redis里取一次
                shortUrl = DnsMemoryDB.selectRecord(l2sKey);
                //如果Redis里确认没有的话，生成短码
                if (shortUrl == null){
                    Long value = DnsMemoryDB.increment();
                    String compressStr = compressNumber(value);
                    shortUrl = new StringBuffer(shortUrlPrefix).append(ConstUtil.SHORT_URL_PREFIX).append(compressStr).toString();
                    DnsMemoryDB.saveRecord(l2sKey, shortUrl, 24*3600*1000*shortUrlTimeout);
                    DnsMemoryDB.saveRecord(new StringBuffer(ConstUtil.SHORT_LONG_KEY_PREFIX).append(compressStr).toString(), url, 24*3600*1000*shortUrlTimeout);
                }
            }
        }
        return shortUrl;
    }

    private static String compressNumber(long number) {
        char[] buf = new char[64];
        int charPos = 64;
        int radix = 1 << 6;
        long mask = radix - 1;
        do {
            buf[--charPos] = digits[(int) (number & mask)];
            number >>>= 6;
        } while (number != 0);
        return new String(buf, charPos, (64 - charPos));
    }
}
