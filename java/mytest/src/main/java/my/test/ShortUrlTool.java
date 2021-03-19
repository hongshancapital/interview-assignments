package my.test;

import org.apache.commons.lang.StringUtils;
import redis.clients.jedis.Jedis;

public class ShortUrlTool {
    public static final String SHORT_URL_NUM = "SHORT:URL:NUM";
    private static final String CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final int SCALE = 62;
    private static final int MIN_LENGTH = 8;
    private static String SHORT_DOMAIN = "http://short.domain/";

    //发号器
    private static Long getShortUrlNum() {
        Jedis jedis = JedisTool.getInstance().getJedis();
        try {
            Long shortUrlNum = jedis.incr(SHORT_URL_NUM);
            return shortUrlNum;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisTool.getInstance().close(jedis);
        }
        return null;
    }

    //生成短域名
    private static String getShortUrl(long num, int minLength) {
        StringBuilder sb = new StringBuilder();
        int remainder;
        while (num > SCALE - 1) {
            //对 scale 进行求余，然后将余数追加至 sb 中，由于是从末位开始追加的，因此最后需要反转字符串
            remainder = Long.valueOf(num % SCALE).intValue();
            sb.append(CHARS.charAt(remainder));
            //除以进制数，获取下一个末尾数
            num = num / SCALE;
        }
        sb.append(CHARS.charAt(Long.valueOf(num).intValue()));
        String value = sb.reverse().toString();
        return SHORT_DOMAIN + StringUtils.leftPad(value, minLength <= 0 ? MIN_LENGTH : minLength, '0');
    }

    //获取短域名
    public static String urlTransferShortUrl(String url, int minLength) {
        Long shortUrlNum=getShortUrlNum();
        if(null==shortUrlNum){
            throw new RuntimeException("获取编号异常");
        }
        String shortUrl = getShortUrl(shortUrlNum, minLength);
        //域名，短域名等信息持久化到数据库，此处略/TODO  落数据库
        //落库成功后加入redis缓存
        Jedis jedis = JedisTool.getInstance().getJedis();
        try {
            setCache(shortUrl, url);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisTool.getInstance().close(jedis);
        }
        return shortUrl;
    }

    //域名解析
    public static String ShortUrlTransferUrl(String shortUrl) {
        String url = JedisTool.getInstance().getJedis().get(shortUrl);
        if (null != url) {
            return url;
        }
        //如果null，查库，并赋值给url,库没有报异常。略//TODO  查数据库
        if (null == url) {
            throw new RuntimeException("未找到短域名对应的域名");
        }
        //写缓存
        setCache(shortUrl, url);
        return shortUrl;
    }

    private static void setCache(String key, String value) {
        Jedis jedis = JedisTool.getInstance().getJedis();
        try {
            jedis.set(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JedisTool.getInstance().close(jedis);
        }
    }

}
