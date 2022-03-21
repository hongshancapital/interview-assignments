package com.zjm.sdct_work;

import com.zjm.sdct_work.constant.RespConst;
import com.zjm.sdct_work.controller.ShortcutController;
import com.zjm.sdct_work.controller.api.ShortcutGetResp;
import com.zjm.sdct_work.controller.api.ShortcutPostReq;
import com.zjm.sdct_work.controller.api.ShortcutPostResp;
import com.zjm.sdct_work.util.ShortcutUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 下午9:34
 * Desc:
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ControllerTest {

    @Autowired
    private ShortcutController controller;


    @Test
    public void testNormal() {

        String goodToken = "abcdefgh";

        String goodUrl = "https://www.sina.com.cn";

        ShortcutPostReq shortcutPostReq = new ShortcutPostReq(goodUrl);


        ShortcutPostResp postResp = controller.createShortcut(shortcutPostReq,goodToken);
        Assert.assertEquals(postResp.getCode(), RespConst.SuccRespCode);
        Assert.assertEquals(postResp.getShortcut().length(), ShortcutUtil.ShortcutLength + ShortcutUtil.ShortcutDomain.length());

        //same req return same shortcut
        ShortcutPostResp postResp2 = controller.createShortcut(shortcutPostReq,goodToken);
        Assert.assertEquals(postResp2.getCode(), RespConst.SuccRespCode);
        Assert.assertEquals(postResp2.getShortcut().length(), ShortcutUtil.ShortcutLength + ShortcutUtil.ShortcutDomain.length());
        Assert.assertEquals(postResp2.getShortcut(), postResp.getShortcut());


        ShortcutGetResp getResp = controller.getUrl(postResp.getShortcut().substring(20), goodToken);
        Assert.assertEquals(getResp.getCode(), RespConst.SuccRespCode);
        Assert.assertEquals(getResp.getUrl(), goodUrl);

    }

    @Test
    public void testWrongPut() {

        String wrongToken = "123456";

        String goodUrl = "http://www.baidu.com";
        String wrongUrl = "www.baidu.com";

        ShortcutPostReq req = new ShortcutPostReq(wrongUrl);
        ShortcutPostResp resp = controller.createShortcut(req,wrongToken);
        Assert.assertEquals(resp.getCode(), RespConst.ParamErrRespCode);


        ShortcutGetResp getResp = controller.getUrl("abcdefh", wrongToken);
        Assert.assertEquals(getResp.getCode(), RespConst.ParamErrRespCode);

    }

    @Test
    public void testNotExit() {

        String goodToken = "abcdefgh";
//
//        String goodUrl = "http://www.baidu.com";
//
//        ShortcutPostReq shortcutPostReq = new ShortcutPostReq(goodUrl,goodToken);


        ShortcutGetResp getResp = controller.getUrl("abcdefgh", goodToken);
        Assert.assertEquals(getResp.getCode(), RespConst.WrongDataErrRespCode);

    }


}
