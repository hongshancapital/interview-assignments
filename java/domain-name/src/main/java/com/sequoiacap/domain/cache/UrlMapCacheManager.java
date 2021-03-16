package com.sequoiacap.domain.cache;

import com.sequoiacap.domain.cache.dto.UrlMapCacheDto;
import com.sequoiacap.domain.common.CacheKey;
import com.sequoiacap.domain.common.UrlMapStatus;
import com.sequoiacap.domain.dao.UrlMapDao;
import com.sequoiacap.domain.entity.UrlMap;
import com.sequoiacap.domain.util.BeanCopierUtils;
import com.sequoiacap.domain.util.JacksonUtils;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.function.Function;

@RequiredArgsConstructor
@Component
public class UrlMapCacheManager {

    private final StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UrlMapDao urlMapDao;

    /**
     * 转换工具UrlMap -> UrlMapCacheDto
     */
    private final Function<UrlMap, UrlMapCacheDto> function = urlMap -> {
        UrlMapCacheDto urlMapCacheDto = new UrlMapCacheDto();
        BeanCopierUtils.X.copy(urlMap, urlMapCacheDto);
        urlMapCacheDto.setEnable(UrlMapStatus.AVAILABLE.getValue().equals(urlMap.getUrlStatus()));
        return urlMapCacheDto;
    };

    /**
     * 刷新短链映射缓存
     *
     * @param urlMap urlMap
     */
    public void refreshUrlMapCache(UrlMap urlMap) {
        if (null != urlMap) {
            refreshUrlMapCache(function.apply(urlMap));
        }
    }

    private void refreshUrlMapCache(UrlMapCacheDto dto) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        hashOperations.put(CacheKey.ACCESS_CODE_HASH.getKey(), dto.getCompressionCode(), JacksonUtils.X.format(dto));
    }

    /**
     * 通过压缩码加载短链映射缓存
     *
     * @param compressionCode compressionCode
     * @return UrlMap
     */
    public UrlMap loadUrlMapCacheByCompressCode(String compressionCode) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        String content = hashOperations.get(CacheKey.ACCESS_CODE_HASH.getKey(), compressionCode);
        UrlMapCacheDto cacheDto = StringUtils.isNoneBlank(content) ? JacksonUtils.X.parse(content, UrlMapCacheDto.class) :
                refreshUrlMapCacheByCompressionCodeInternal(compressionCode);
        if (null != cacheDto && Objects.equals(cacheDto.getEnable(), Boolean.TRUE)) {
            UrlMap urlMap = new UrlMap();
            BeanCopierUtils.X.copy(cacheDto, urlMap);
            return urlMap;
        }
        return null;
    }

    private UrlMapCacheDto refreshUrlMapCacheByCompressionCodeInternal(String compressionCode) {
        UrlMap urlMap = urlMapDao.selectByCompressionCode(compressionCode);
        UrlMapCacheDto dto;
        if (null != urlMap) {
            dto = function.apply(urlMap);
        } else {
            // 预防缓存被击穿
            dto = new UrlMapCacheDto();
            dto.setCompressionCode(compressionCode);
            dto.setEnable(Boolean.FALSE);
        }
        refreshUrlMapCache(dto);
        return dto;
    }
}
