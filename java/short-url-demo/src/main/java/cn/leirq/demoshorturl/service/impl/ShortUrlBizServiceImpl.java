package cn.leirq.demoshorturl.service.impl;

import cn.leirq.demoshorturl.model.ShortUrlModel;
import cn.leirq.demoshorturl.service.ShortUrlBizService;
import cn.leirq.demoshorturl.service.ShortUrlSaveService;
import cn.leirq.demoshorturl.utils.ConversionUtil;
import com.google.common.hash.Hashing;
import java.nio.charset.StandardCharsets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Ricky
 */
@Service
public class ShortUrlBizServiceImpl implements ShortUrlBizService {

    @Autowired
    private ShortUrlSaveService shortUrlSaveService;

    @Override
    public String createAndSaveByLongUrl(String longUrl) {

        // 创建短url
        String shortUrl = createShortUrl(longUrl);

        // 保存长短url
        ShortUrlModel shortUrlModel = new ShortUrlModel();
        shortUrlModel.setShortUrl(shortUrl);
        shortUrlModel.setLongUrl(longUrl);
        // 默认无后缀
        shortUrlModel.setRandomSuffix(null);
        // 持久保存
        saveShortUrl(shortUrlModel);
        return shortUrlModel.getShortUrl();
    }

    @Override
    public String getLongUrlByShortUrl(String shortUrl) {
        ShortUrlModel shortUrlModel = shortUrlSaveService.getByShortUrl(shortUrl);
        if (shortUrlModel == null) {
            return "";
        }
        return shortUrlModel.getLongUrl();
    }


    /**
     * 创建短url的算法逻辑
     *
     * @param str
     * @return
     */
    private String createShortUrl(String str) {
        // 生成hash
        int hash = Hashing.murmur3_32_fixed().hashString(str, StandardCharsets.UTF_8).hashCode();
        // 十进制转62进制，最大8位，不足8位补0
        String shortCode = ConversionUtil.to62HEX(hash, 8);
        return shortCode;
    }

    /**
     * 保存长短url对象
     *
     * @param shortUrlModel
     * @return
     */
    private void saveShortUrl(ShortUrlModel shortUrlModel) {
        boolean bool = shortUrlSaveService.saveModel(shortUrlModel);
        // 保存失败，可能是hash算法小概率重复，此时补充后缀重新计算shortUrl
        while (!bool) {
            // 随机字符串后缀，2位
            String randomStr = ConversionUtil.getRandomStr(2);

            // 补充后缀重新生成短url
            String shortUrl = createShortUrl(shortUrlModel.getLongUrl() + randomStr);

            // 重新保存
            shortUrlModel.setShortUrl(shortUrl);
            shortUrlModel.setRandomSuffix(randomStr);
            bool = shortUrlSaveService.saveModel(shortUrlModel);
        }
    }
}
