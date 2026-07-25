package com.my.linkapi;

import com.my.linkapi.dto.LinkShortRequestDto;
import com.my.linkapi.service.MapSaveService;
import com.my.linkapi.service.impl.LinkLengthConversionServiceImpl;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LinkApiApplication.class)
public class ShortLinkTest {

    @InjectMocks
    private LinkLengthConversionServiceImpl linkLengthConversionService;

    @Mock
    private MapSaveService mapSaveService;

    private String testLink = "https://taobao.cn/ssssss/xxxxx/gggg/eeeeeee";

    private String testShortKey = "AvQZ7n";
    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testToShort(){
        LinkShortRequestDto getLinkRequestDto = Mockito.mock(LinkShortRequestDto.class);
        Mockito.when(getLinkRequestDto.getLink()).thenReturn(testLink);
        String key = linkLengthConversionService.toShort(getLinkRequestDto);
        Assert.assertThat(key, Matchers.notNullValue());
        Assert.assertTrue("少于6位",key.length()>5 && key.length()<9);
        Assert.assertTrue("不等于预定key", key.startsWith(testShortKey));
    }

    @Test
    public void testToShort2(){
        Mockito.when(mapSaveService.getData(testShortKey+"00")).thenReturn(this.testLink+"error");

        LinkShortRequestDto getLinkRequestDto = Mockito.mock(LinkShortRequestDto.class);
        Mockito.when(getLinkRequestDto.getLink()).thenReturn(testLink);
        String key = linkLengthConversionService.toShort(getLinkRequestDto);

        Assert.assertFalse("md5相同错误", key.endsWith("00"));
    }

    @Test
    public void testToShort3(){
        Mockito.when(mapSaveService.getData(testShortKey+"00")).thenReturn(testLink);

        LinkShortRequestDto getLinkRequestDto = Mockito.mock(LinkShortRequestDto.class);
        Mockito.when(getLinkRequestDto.getLink()).thenReturn(testLink);
        String key = linkLengthConversionService.toShort(getLinkRequestDto);

        Assert.assertTrue("md5相同错误", key.endsWith("00"));
    }

    @Test
    public void testGetLink(){
        Mockito.when(mapSaveService.getData(testShortKey)).thenReturn(this.testLink);
        LinkShortRequestDto getLinkRequestDto = Mockito.mock(LinkShortRequestDto.class);
        Mockito.when(getLinkRequestDto.getLink()).thenReturn(testShortKey);
        String key = linkLengthConversionService.toLong(getLinkRequestDto.getLink());
        Assert.assertThat(key, Matchers.notNullValue());
        Assert.assertTrue("不等于原始link", key.equals(testLink));
    }
}
