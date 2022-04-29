package com.interview.wph.shorturl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.interview.wph.shorturl.common.utils.BloomUtil;
import com.interview.wph.shorturl.common.utils.EncryptionUtil;
import com.interview.wph.shorturl.common.utils.LRUCacheUtil;
import com.interview.wph.shorturl.common.utils.LogUtil;
import com.interview.wph.shorturl.entity.ShortUrlEntity;
import com.interview.wph.shorturl.mapper.ShortUrlMapper;
import com.interview.wph.shorturl.service.IShortUrlService;
import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ShortUrlServiceImpl extends ServiceImpl<ShortUrlMapper, ShortUrlEntity> implements IShortUrlService {

    @Override
    public String postLongUrl(String longUrl) {
        //1: 计算hash,看是否存在
        Long shortId = null;
        int hashCode = longUrl.hashCode();
        //对string对象上锁
        longUrl.intern();
        int time = 1;
        //重试三次
        while (time < 3) {
            try {
                //对短时间内同样的长链接请求加锁
                synchronized (longUrl) {
                    Map<String, Long> dataMap = getRecordByHash(hashCode).stream().collect(Collectors.toMap(ShortUrlEntity::getLongUrl, ShortUrlEntity::getShortUrlId, (k1, k2) -> k1));
                    if (dataMap.containsKey(longUrl)) {
                        LogUtil.info(this.getClass(), "长链接{}已存储", longUrl);
                        shortId = dataMap.get(longUrl);
                    } else {
                        //生成id
                        shortId = EncryptionUtil.generateId();
//                        while (this.getById(shortId) != null) {
//                            shortId = EncryptionUtil.generateId();
//                        }
                        this.save(new ShortUrlEntity(shortId, hashCode, longUrl));
                    }
                }
                break;
            } catch (Exception e) {
                //高并发场景下,导致锁表问题
                LogUtil.error(this.getClass(), "查询表异常,休眠100ms");
//                try {
//                    TimeUnit.MILLISECONDS.sleep(100);
//                } catch (InterruptedException ex) {
//                    LogUtil.error(this.getClass(), "休眠失败");
//                    ex.printStackTrace();
//                }
                time++;
            }
        }
        //更新缓存
        LRUCacheUtil.removeInvalid(shortId);
        LRUCacheUtil.putValid(shortId, longUrl);
        BloomUtil.addId(shortId);
        String ret = EncryptionUtil.to62RadixString(shortId);
        LogUtil.info(this.getClass(), "长连接[{}]对应的短链接为[{}]", longUrl, ret);
        // 返回
        return ret;
    }


    @Override
    public String getLongUrl(Long shortUrlId) {
        // 突破了布隆过滤，几率很小
        Boolean invalid = LRUCacheUtil.isInvalid(shortUrlId);
        if (invalid) return null;
        // 查找LRU缓存
        String cacheRet = LRUCacheUtil.getValid(shortUrlId);
        if (!Strings.isEmpty(cacheRet)) return cacheRet;
        // 查找库
        String ret = Optional.ofNullable(this.getById(shortUrlId)).map(ShortUrlEntity::getLongUrl).orElse(null);
        if (!Strings.isEmpty(ret)) {
            LogUtil.info(this.getClass(), "短域名{}对应的长域名为:{}", EncryptionUtil.to62RadixString(shortUrlId), ret);
            // 为空的话, 加入缓存. 设计一个防止无效url多次查表的风险.
            LRUCacheUtil.putValid(shortUrlId, ret);
            return ret;
        }
        LRUCacheUtil.putInvalid(shortUrlId);
        return null;
    }

    public List<ShortUrlEntity> getRecordByHash(Integer hash) {
        LambdaQueryWrapper<ShortUrlEntity> queryWrapper = new QueryWrapper<ShortUrlEntity>().lambda().eq(ShortUrlEntity::getLongUrlHash, hash);
        return this.list(queryWrapper);
    }
}
