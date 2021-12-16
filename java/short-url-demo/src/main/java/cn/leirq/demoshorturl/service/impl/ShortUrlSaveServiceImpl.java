package cn.leirq.demoshorturl.service.impl;

import cn.leirq.demoshorturl.model.ShortUrlModel;
import cn.leirq.demoshorturl.service.ShortUrlSaveService;
import com.google.common.collect.Maps;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @author Ricky
 */
@Service
public class ShortUrlSaveServiceImpl implements ShortUrlSaveService {

    /**
     * 存储短url对象的内存对象
     */
    Map<String, ShortUrlModel> shortUrlModelMap = Maps.newHashMap();

    @Override
    public boolean saveModel(ShortUrlModel shortUrlModel) {
        // 短url重复
        if (shortUrlModelMap.containsKey(shortUrlModel.getShortUrl())) {
            // 如果对应的longUrl存在，则说明之前已经生成过，无需保存，直接成功，否则保存失败
            ShortUrlModel tmp = shortUrlModelMap.get(shortUrlModel.getShortUrl());
            if (StringUtils.equals(tmp.getLongUrl(), shortUrlModel.getLongUrl())) {
                return true;
            } else {
                return false;
            }
        }
        shortUrlModelMap.put(shortUrlModel.getShortUrl(), shortUrlModel);
        return true;
    }

    @Override
    public ShortUrlModel getByShortUrl(String shortUrl) {
        return shortUrlModelMap.get(shortUrl);
    }
}
