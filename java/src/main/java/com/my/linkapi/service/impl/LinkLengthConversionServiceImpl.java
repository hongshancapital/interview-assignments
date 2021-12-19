package com.my.linkapi.service.impl;

import com.my.linkapi.service.MapSaveService;
import lombok.AllArgsConstructor;
import org.springframework.util.DigestUtils;
import com.my.linkapi.dto.LinkShortRequestDto;
import org.springframework.stereotype.Component;
import com.my.linkapi.service.LinkLengthConversionService;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

@Component
public class LinkLengthConversionServiceImpl implements LinkLengthConversionService {
    @Resource
    private MapSaveService mapSaveService;

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

    public String toLong(String link){
        return mapSaveService.getData(link);
    }
}
