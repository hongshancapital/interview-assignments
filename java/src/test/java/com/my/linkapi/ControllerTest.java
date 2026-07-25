package com.my.linkapi;

import com.my.linkapi.controller.LinkApiController;
import com.my.linkapi.dto.LinkShortRequestDto;
import com.my.linkapi.dto.R;
import com.my.linkapi.service.LinkLengthConversionService;
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

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LinkApiApplication.class)
public class ControllerTest {
    @InjectMocks
    private LinkApiController linkApiController;

    @Mock
    private LinkLengthConversionService linkLengthConversionService;

    private String testLink = "https://taobao.cn/ssssss/xxxxx/gggg/eeeeeee";

    private String testShortKey = "AvQZ7n";

    @Before
    public void initMocks(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void reclaimMemory(){
        Mockito.when(linkLengthConversionService.toShort(testLink)).thenReturn(testShortKey);
        Mockito.when(linkLengthConversionService.toLong(testShortKey)).thenReturn(testLink);
        LinkShortRequestDto shortLinkRequestDto = new LinkShortRequestDto();
        shortLinkRequestDto.setLink(testLink);
        linkApiController.toShortApi(shortLinkRequestDto);

        LinkShortRequestDto getLinkRequestDto = new LinkShortRequestDto();
        getLinkRequestDto.setLink(testShortKey);
        R r = linkApiController.getLink(getLinkRequestDto);
        Assert.assertTrue("api调用失败", r.getData().equals(testLink));
    }
}