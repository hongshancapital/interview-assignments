package com.scdt.shortlink.controller;

import com.scdt.shortlink.common.ResultBean;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author xbhong
 * @date 2022/4/16 20:21
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class LinkControllerTest {

    @Autowired
    private LinkController controller;

    @Test
    public void longLinkEmpty() {
        ResultBean resultBean = controller.getShortLink("");
        Assert.assertEquals(400, resultBean.getCode().longValue());
    }

    @Test
    public void longLinkBlank() {
        ResultBean resultBean = controller.getShortLink(" ");
        Assert.assertEquals(400, resultBean.getCode().longValue());
    }

    @Test
    public void longLinkNull() {
        ResultBean resultBean = controller.getShortLink(null);
        Assert.assertEquals(400, resultBean.getCode().longValue());
    }

    @Test
    public void longLinkNormal() {
        ResultBean resultBean = controller.getShortLink("https://github.com/scdt-china/interview-assignments/tree/master/java");
        Assert.assertEquals(200, resultBean.getCode().longValue());
    }

    @Test
    public void longLinkHasChinese() {
        ResultBean resultBean = controller.getShortLink("http://新华网.cn/comments/2022-04/11/c_1128550629.htm?time=1650201645");
        Assert.assertEquals(200, resultBean.getCode().longValue());
    }

    @Test
    public void shortLinkEmpty() {
        ResultBean resultBean = controller.getLongLink("");
        Assert.assertEquals(400, resultBean.getCode().longValue());
    }

    @Test
    public void shortLinkBlank() {
        ResultBean resultBean = controller.getLongLink(" ");
        Assert.assertEquals(400, resultBean.getCode().longValue());
    }

    @Test
    public void shortLinkNull() {
        ResultBean resultBean = controller.getLongLink(null);
        Assert.assertEquals(400, resultBean.getCode().longValue());
    }

    @Test
    public void shortLinkNotExit() {
        ResultBean resultBean = controller.getLongLink("http://t.cn/F17SDE");
        Assert.assertEquals("短域名信息不存在", resultBean.getMsg());
    }

    @Test
    public void getLonglink() {
        String longlink = "https://github.com/scdt-china/interview-assignments/tree/master/java";
        ResultBean resultBean = controller.getShortLink(longlink);
        String shortLink = String.valueOf(resultBean.getData());
        Assert.assertNotNull(shortLink);
        Assert.assertEquals(longlink, controller.getLongLink(shortLink).getData());
    }
}
