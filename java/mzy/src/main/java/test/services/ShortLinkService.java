package test.services;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.cache.Cache;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import test.entity.ShortLinkConfigPO;
import test.mapper.ShortLinkConfigMapper;
import test.utils.LocalCacheBuilder;
import test.utils.NumberConvertUtil;

@Service
@Slf4j
public class ShortLinkService {

    @Resource
    private ShortLinkConfigMapper shortLinkConfigMapper;
    private final Cache<String, Optional<String>> shortLinkWithLongLinkCache = LocalCacheBuilder
        .build(4, 2, TimeUnit.SECONDS, 16, 128);

    private final Cache<String, Optional<String>> longLinkWithShortLinkCache = LocalCacheBuilder
        .build(4, 2, TimeUnit.SECONDS, 16, 128);


    private String GetLongLinkLocalCacheKey(String shortLink) {
        return "CacheGetLongLink:" + shortLink;
    }

    private String GetShortLinkLocalCacheKey(String longLink) {
        return "CacheGetShortLink:" + longLink;
    }

    /**
     * 传入短网址，返回对应长网址，没有返回空字符串（有缓存）
     *
     * @param shortLink 短网址
     * @return 对应的长网址
     */
    public String getLongLink(String shortLink) {
        String localCacheKey = GetLongLinkLocalCacheKey(shortLink);
        Optional<String> cacheObj = shortLinkWithLongLinkCache.getIfPresent(localCacheKey);
        // 1. 先从本地缓存读取
        if (cacheObj != null && cacheObj.isPresent()) {
            // 本地缓存有直接返回
            return cacheObj.get();
        }
        // 2.执行数据查询
        String longLink = doGetLongLink(shortLink);
        // 3.回写本地缓存
        shortLinkWithLongLinkCache.put(localCacheKey, Optional.ofNullable(longLink));
        return longLink;
    }

    /**
     * 没有缓存，直接对数据库执行操作
     *
     * @param shortLink 短网址
     * @return 对应的长网址
     */
    private String doGetLongLink(String shortLink) {
        log.info("从DB获取存量短网址对应的长网址 shortLink:{}", shortLink);
        LambdaQueryWrapper<ShortLinkConfigPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ShortLinkConfigPO::getShortLink, shortLink);
        ShortLinkConfigPO shortLinkConfigPo = shortLinkConfigMapper.selectOne(wrapper);
        if (shortLinkConfigPo != null) {
            return shortLinkConfigPo.getLongLink();
        }
        // 没有查询到返回空字符串
        return StringUtils.EMPTY;
    }

    public String getShortLink(String longLink) {
        String localCacheKey = GetShortLinkLocalCacheKey(longLink);
        Optional<String> cacheObj = longLinkWithShortLinkCache.getIfPresent(localCacheKey);
        // 1. 先从本地缓存读取
        if (cacheObj != null && cacheObj.isPresent()) {
            // 本地缓存有直接返回
            return cacheObj.get();
        }
        // 2.执行数据查询
        String shortLink = doGetShortLink(longLink);
        // 3.回写本地缓存
        longLinkWithShortLinkCache.put(localCacheKey, Optional.of(shortLink));
        return shortLink;
    }

    /**
     * 直接进行数据库操作，传入长连接，得到对应的短链接，有存量的短链接直接转换，否则生成后转换
     *
     * @param longLink 长连接
     * @return 得到对应的短链接
     */
    private String doGetShortLink(String longLink) {
        // 第一步，先得到长连接的hash值,便于sql查找是走llh索引
        int llh = longLink.hashCode();
        String llhStr = String.valueOf(llh);

        // 第二步，执行数据库查询
        LambdaQueryWrapper<ShortLinkConfigPO> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ShortLinkConfigPO::getLongLinkHash, llhStr).eq(ShortLinkConfigPO::getLongLink, longLink);
        List<ShortLinkConfigPO> shortLinkConfigPos = shortLinkConfigMapper.selectList(wrapper);

        // 第三步，根据是否有存量的短链接映射关系，来决定是1.直接返回；还是2.生成一个新的映射关系再返回
        if (!CollectionUtils.isEmpty(shortLinkConfigPos)) {
            //1.直接返回
            log.info("从DB获取存量短网址 llh:{}", llh);
            return shortLinkConfigPos.get(0).getShortLink();
        }

        //2.生成一个新的映射关系再返回
        ShortLinkConfigPO newShortLinkPo = new ShortLinkConfigPO();
        newShortLinkPo.setLongLink(longLink);
        newShortLinkPo.setLongLinkHash(llhStr);
        shortLinkConfigMapper.insert(newShortLinkPo);
        log.info("短域名配置新增 shortLinkPo:{}", newShortLinkPo);

        String genShortLink = NumberConvertUtil.convert(newShortLinkPo.getId());
        newShortLinkPo.setShortLink(genShortLink);
        shortLinkConfigMapper.updateById(newShortLinkPo);
        log.info("新增短域名设置shortLink shortLinkPo:{}", newShortLinkPo);
        String localCacheKey = GetLongLinkLocalCacheKey(genShortLink);
        // 3.更新本地缓存
        shortLinkWithLongLinkCache.put(localCacheKey, Optional.of(longLink));
        return genShortLink;
    }
}
