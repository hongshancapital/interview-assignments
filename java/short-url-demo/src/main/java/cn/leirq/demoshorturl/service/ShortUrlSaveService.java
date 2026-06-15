package cn.leirq.demoshorturl.service;

import cn.leirq.demoshorturl.model.ShortUrlModel;

/**
 * 短url的存储服务
 * @author Ricky
 */
public interface ShortUrlSaveService {


    /**
     * 持久保存短url结果model
     * @param shortUrlModel
     * @return 成功or失败
     */
    boolean saveModel(ShortUrlModel shortUrlModel);

    /**
     * 根据短url从存储中命中对象
     * @param shortUrl
     * @return
     */
    ShortUrlModel getByShortUrl(String shortUrl);
}
