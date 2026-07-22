package com.liujunpei.shorturl.serviceImpl;

import com.liujunpei.shorturl.constants.CommonConstant;
import com.liujunpei.shorturl.service.ShortUrlService;
import com.liujunpei.shorturl.utils.UrlCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;
import wiremock.org.apache.commons.lang3.StringUtils;

/**
 * @author 刘俊佩
 * @date 2022/1/25 下午5:21
 */
@Slf4j
@Service
public class ShortUrlServiceImpl implements ShortUrlService {

    @Override
    public String longUrlToShortUrl(String longUrl) {
        String result = "";
        String md5Key = CommonConstant.MD5_KEYS[0];
        List<String> resultUrls = this.generateShortUrl(longUrl, md5Key);
        for (String resultUrl : resultUrls) {
            String longUrlInCache = UrlCache.get(resultUrl);
            // 如果该短链接对应的长链接已存在于缓存
            if (StringUtils.isNotEmpty(longUrlInCache)) {
                // 如果缓存中长链接与传入长链接相同，则直接返回短链接；否则说明出现冲突，继续下一次循环处理
                if (longUrlInCache.equals(longUrl)) {
                    result = resultUrl;
                    break;
                }
            } else { // 存储短域名和对应长域名
                boolean putSucceed = UrlCache.put(resultUrl, longUrl);
                if (putSucceed) {
                    result = resultUrl;
                    break;
                }

            }
        }
        return result;
    }

    @Override
    public String shortUrlToLongUrl(String shortUrl) {
        return UrlCache.get(shortUrl);
    }

    /**
     * 根据长链接地址，获取短链接数组
     * @param longUrl 长链接地址
     * @param md5Key md5加密混合key
     * @return 短链接数组，其中包含多个短链接，发生碰撞时可取数组中下一个短链接地址.
     */
    private List<String> generateShortUrl(String longUrl, String md5Key) {
        // 对传入长链接进行md5加密
        String md5EncryptResult = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update((md5Key + longUrl).getBytes());
            byte[] digest = md.digest();
            md5EncryptResult = DatatypeConverter.printHexBinary(digest).toUpperCase();
        } catch (NoSuchAlgorithmException e) {
            log.error(e.getMessage(), e);
        }

        List<String> resultUrls = new ArrayList<>();
        // 获取4组短链接字符串结果
        for (int i = 0; i < 4; i++) {
            // 把加密字符按照8位一组16进制与0x3FFFFFFF进行按位与运算
            String sTempSubString = md5EncryptResult.substring(i * 8, i * 8 + 8);
            // 这里使用Long型来转换，因为Integer.parseInt()只能处理 31 位，首位为符号位，如果不用long，则会越界
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            //循环获得每组6位的字符串
            for (int j = 0; j < 6; j++) {
                // 把得到的值与0x0000003D进行位与运算，取得字符数组chars索引(具体需要看chars数组的长度以防下标溢出，注意起点为0)
                long index = 0x0000003D & lHexLong;
                // 把取得的字符相加
                outChars += CommonConstant.CHARS[(int)index];
                // 每次循环按位右移 5 位
                lHexLong = lHexLong >> 5;
            }
            // 把字符串存入对应索引的输出数组
            resultUrls.add(outChars);
        }
        return resultUrls;
    }
}
