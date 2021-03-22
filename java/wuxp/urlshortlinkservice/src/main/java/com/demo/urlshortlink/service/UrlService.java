package com.demo.urlshortlink.service;


import com.demo.urlshortlink.domain.UrlLink;
import com.demo.urlshortlink.dto.UrlLongRequest;
import com.demo.urlshortlink.repository.UrlLinkRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
/*
  service
 */
@Service
public class UrlService {

    private final UrlLinkRepository urlLinkRepository;
    //重启服务后可从数据库中初始化
    private static volatile Long startNums=100000000000L;
    /**
     * 字符集合，0-Z
     */
    private static final char[] chars = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

    public UrlService(UrlLinkRepository urlLinkRepository) {
        this.urlLinkRepository = urlLinkRepository;
    }

    public String createAndSaveLink(UrlLongRequest request) {

        startNums++;
        CompletableFuture.runAsync(()->{
            UrlLink url = new UrlLink();
            url.setId(startNums);
            url.setLongUrl(request.getLongUrl());
            url.setExpiresDate(request.getExpiresDate());
            url.setCreatedDate(new Date());
            UrlLink urlLink=urlLinkRepository.save(url);
        });

        return transferNumbertoStr(startNums);
    }
    public String getLongUrl(String shortUrl) throws Exception {
        Long id = fromShortUrltoNumber(shortUrl);
        Optional<UrlLink> entity = urlLinkRepository.findById(id);
         if(entity.isPresent()){
            UrlLink link=entity.get();
             if (link.getExpiresDate() != null && link.getExpiresDate().before(new Date())){
                 urlLinkRepository.delete(link);
                 throw new Exception("链接到期!");
             }else{
                 return link.getLongUrl();
             }
         }else {
             throw new Exception("没有匹配数据行");
         }
    }
    /**
     * 数字转换为62进制的字符串
     *
     * @param number 十进制的数字
     * @return 字符串
     */
    public  String transferNumbertoStr(long number) {

        char[] buf = new char[32];
        int charPos = 32;
        while ((number / 62) > 0) {
            buf[--charPos] = chars[(int) (number % 62)];
            number /= 62;
        }
        buf[--charPos] = chars[(int) (number % 62)];
        return new String(buf, charPos, (32 - charPos));
    }

    /**
     * 将62进制字符串形式转换为十进制的数字
     *
     * @param number 其它进制字符串
     * @return 十进制的数字
     */
    public  long fromShortUrltoNumber(String number) {
        char[] charBuf = number.toCharArray();

        long result = 0, base = 1;

        for (int i = charBuf.length - 1; i >= 0; i--) {
            int index = 0;
            for (int j = 0, length = chars.length; j < length; j++) {
                if (chars[j] == charBuf[i]) {
                    index = j;
                }
            }
            result += index * base;
            base *= 62;
        }
        return result;
    }
}
