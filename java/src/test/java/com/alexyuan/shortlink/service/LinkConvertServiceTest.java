package com.alexyuan.shortlink.service;

import com.alexyuan.shortlink.common.variant.CacheVariant;
import com.alexyuan.shortlink.config.CaffeineCacheConfig;
import com.alexyuan.shortlink.exceptions.ImproperValueException;
import com.alexyuan.shortlink.service.impl.CaffeineCacheServiceImpl;
import com.alexyuan.shortlink.service.impl.LinkConvertServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LinkConvertServiceTest {

    @Resource
    LinkConvertServiceImpl linkConvertService;

    private String long_link = "http://www.alexkxyuan.com/test/shortlinkconverter";

    @Test
    public void testNormalCases() {
        CacheVariant cacheVariant = linkConvertService.shortLinkGenerator(long_link, true);
        assertThat(cacheVariant.getLong_url()).isEqualTo(long_link);
        assertThat(cacheVariant.getShort_url()).isNotNull();
        assertThat(cacheVariant.getShort_url()).isNotEmpty();
        assertThat(linkConvertService.longLinkSearcher(cacheVariant.getShort_url()).getLong_url()).isEqualTo(long_link);
    }

    @Test(expected = ImproperValueException.class)
    public void testUnNormalCaseFir() {
        CacheVariant cacheVariant = linkConvertService.shortLinkGenerator(null, true);
    }

    public void testUnNormalCaseSec() {
        CacheVariant cacheVariant = linkConvertService.shortLinkGenerator(long_link, true);
        assertThat(linkConvertService.longLinkSearcher(cacheVariant.getShort_url().substring(0, 1)).getLong_url())
                .isNotEqualTo(long_link);
    }

    @Test(expected = ImproperValueException.class)
    public void testUnNormalCaseThi() {
        CacheVariant cacheVariant = linkConvertService.longLinkSearcher(null);
    }

}
