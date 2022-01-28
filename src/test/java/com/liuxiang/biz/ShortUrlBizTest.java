package com.liuxiang.biz;

import cn.hutool.core.lang.Assert;
import com.liuxiang.dao.ShortUrlMappingDao;
import com.liuxiang.model.po.ShortUrlMappingPo;
import com.liuxiang.model.view.CommonResult;
import com.liuxiang.service.IGenerateId;
import com.liuxiang.service.IShortIdExist;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.mockito.internal.util.reflection.FieldSetter;
import org.powermock.reflect.Whitebox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuxiang6
 * @date 2022-01-09
 **/
@Slf4j
public class ShortUrlBizTest {

    @InjectMocks
    ShortUrlBiz shortUrlBiz;

    @Mock
    private List<IGenerateId> generateIdList;
    @Mock
    private IShortIdExist stringExist;
    @Mock
    private ShortUrlMappingDao shortUrlMappingDao;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGenerateShortUrl() throws Exception{
        Whitebox.setInternalState(shortUrlBiz, "ALGORITHMS", "");
        FieldSetter.setField(shortUrlBiz, ShortUrlBiz.class.getDeclaredField("generateIdList"), new ArrayList());
//        when(generateIdList.stream().filter(s -> s.generateType().equals(anyString())).findAny().orElse(null)).thenReturn(null);

        CommonResult<String> stringCommonResult = shortUrlBiz.generateShortUrl("www.baidu.com");
        Assert.isTrue(stringCommonResult.getCode() == 1);

    }

    @Test
    public void getLongUrl() {
        Whitebox.setInternalState(shortUrlBiz, "LOCAL_HOST", "");
        when(stringExist.isExist(anyString())).thenReturn(true);
        when(shortUrlMappingDao.getByShortUrl(anyString())).thenReturn(null);
        CommonResult<String> abcdefg = shortUrlBiz.getLongUrl("abcdefg");
        Assert.isTrue(abcdefg.getCode() == 1);


        Whitebox.setInternalState(shortUrlBiz, "LOCAL_HOST", "");
        when(stringExist.isExist(anyString())).thenReturn(true);
        ShortUrlMappingPo po = new ShortUrlMappingPo();
        po.setLongUrl("aaa");
        when(shortUrlMappingDao.getByShortUrl(anyString())).thenReturn(po);
        CommonResult<String> poiuuyt = shortUrlBiz.getLongUrl("poiuuyt");
        Assert.isTrue(poiuuyt.getCode() == 0);
    }
}