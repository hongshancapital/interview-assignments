package com.my.linkapi.service.impl;

import com.my.linkapi.service.MapSaveService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import com.my.linkapi.dto.LinkShortRequestDto;
import org.springframework.stereotype.Component;
import com.my.linkapi.service.LinkLengthConversionService;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * 长短链转换短链接 和 获取长短链接匹配关系
 *
 * @author ricky
 */
@Service
public class LinkLengthConversionServiceImpl implements LinkLengthConversionService {
    @Resource
    private MapSaveService mapSaveService;

    /**
     * 转换长链接至短连接 并保存至数据至map  调用过程
     * @param LinkShortRequestDto link 长链接参数
     * @return String 短链接
     */
    public String toShort(LinkShortRequestDto linkShortRequestDto){
        String defaultShortUrl = toShort(linkShortRequestDto.getLink());
        int i = 0;
        String firstShortUrl = defaultShortUrl+"00";
        while (mapSaveService.getData(firstShortUrl) != null
                && !mapSaveService.getData(firstShortUrl).equals(linkShortRequestDto.getLink())){
            firstShortUrl = defaultShortUrl+(i<10?"0"+i:i);
            i++;
        }
        mapSaveService.save(firstShortUrl, linkShortRequestDto.getLink());
        return firstShortUrl;
    }

    /**
     * 转换长链接至短连接 md5计算过程
     * @param String 长链接参数
     * @return String 短链接
     */
    public String toShort(String link){
        String key = "key";
        String[] chars = new String[] { "a", "b", "c", "d", "e", "f", "g", "h",
                "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t",
                "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
                "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H",
                "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T",
                "U", "V", "W", "X", "Y", "Z"};
        String md5Str = String.format("%s%s", key.toString(), link);
        String sMD5EncryptResult = DigestUtils.md5DigestAsHex(md5Str.getBytes(StandardCharsets.UTF_8));
        String hex = sMD5EncryptResult;
        String[] resUrl = new String[4];
        for(int i=0; i < 4; i++){
            String sTempSubString = hex.substring(i*8, i*8+8);
            long lHexLong = 0x3FFFFFFF & Long.parseLong(sTempSubString, 16);
            String outChars = "";
            for(int j=0; j<6; j++){
                long index = 0x0000003D & lHexLong;
                outChars += chars[(int) index];
                lHexLong = lHexLong >> 5;
            }
            resUrl[i] = outChars;
        }
        String firstShortUrl = resUrl[0];
        return firstShortUrl;
    }

    /**
     * 通过短链接获取 map中保存长链接
     * @param String 短链接参数
     * @return String 长链接
     */
    public String toLong(String link){
        return mapSaveService.getData(link);
    }
}
