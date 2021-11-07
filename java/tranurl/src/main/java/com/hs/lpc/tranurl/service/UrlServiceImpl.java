package com.hs.lpc.tranurl.service;

import com.hs.lpc.tranurl.util.UrlUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 获取短链服务接口实现类
 * @author Li Pengcheng
 */
@Service
public class UrlServiceImpl implements UrlService{
    private Map<String, String> map = new HashMap<>();

    /**
     * 根据长链接获取短链接
     * @param longUrl 长链接
     * @return 短链接
     */
    @Override
    public String getShortUrl(String longUrl) {
        String encryptResult = UrlUtil.encodeByMD5(longUrl, UrlUtil.KEY);
        List<String> resList = new ArrayList<>();
        for(int i = 0; i < 32; i = i + 8){
            String str = encryptResult.substring(i, i + 8);
            long bitCal = UrlUtil.bitCalWith30(str);
            String strRes = UrlUtil.getResultForLen6(bitCal, UrlUtil.STRS);
            resList.add(strRes);
        }
        String strResult = resList.get(0);
        map.put(strResult, longUrl);
        return strResult;
    }
    /**
     * 根据短链接获取长链接
     * @param shortUrl 短链接
     * @return 长链接
     */
    @Override
    public String getLongUrl(String shortUrl) {
        return map.get(shortUrl);
    }
}
