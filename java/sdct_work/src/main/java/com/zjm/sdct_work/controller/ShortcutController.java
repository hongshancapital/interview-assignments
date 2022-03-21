package com.zjm.sdct_work.controller;

import com.zjm.sdct_work.constant.RespConst;
import com.zjm.sdct_work.controller.api.ShortcutGetResp;
import com.zjm.sdct_work.controller.api.ShortcutPostReq;
import com.zjm.sdct_work.controller.api.ShortcutPostResp;
import com.zjm.sdct_work.service.ShortcutService;
import com.zjm.sdct_work.util.ShortcutUtil;
import com.zjm.sdct_work.util.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Author:   billzzzhang
 * Date:     2022/3/19 下午4:04
 * Desc:
 */


@Api(value = "ShortcutController")
@RestController
@Slf4j
public class ShortcutController {


    @Autowired
    private ShortcutService shortcutService;

    @Autowired
    private Validator validator;

    @Autowired
    private ShortcutUtil shortcutUtil;


    //读接口，根据短链获取url
    @ApiOperation(value = "get origin url by shortcut", notes = "retrun errcode when shortcut not exist")
    @GetMapping("/api/v1/shortcut/{shortcutPath}")
    public ShortcutGetResp getUrl(@ApiParam("shortcutPath") @PathVariable String shortcutPath, @RequestHeader("Shortcut-Token") String token) {
        try {
            if (!validator.validateShortcutPath(shortcutPath) || !validator.validateToken(token)) {
                return RespConst.NewShortcutGetParamErrResp();
            }
            log.info("[getUrl] input {}", shortcutPath);
            String url = shortcutService.getUrlByShortCut(shortcutPath);
            if (StringUtil.isEmpty(url)) {
                log.info("[getUrl] not find {}", shortcutPath);
                return RespConst.NewShortcutGetDataErrResp();
            }
            log.info("[getUrl] find result {}", url);
            return RespConst.NewShortcutGetSuccResp(url);
        } catch (Exception e) {
            log.error("[[getUrl]] err {}", shortcutPath, e);
            return RespConst.NewShortcutGetSysErrResp();
        }
    }


    //写接口，根据url生成短链
    @ApiOperation(value = "create shortcut by input url", notes = "return same shortcut where input same url")
    @PostMapping(path = "/api/v1/shortcut")
    public ShortcutPostResp createShortcut(@RequestBody ShortcutPostReq postReq,@RequestHeader("Shortcut-Token") String token) {
        try {
            if (!validator.validateShortcutPostReq(postReq) || !validator.validateToken(token)) {
                return RespConst.NewShortcutPostParamErrResp();
            }
            log.info("[createShortcut] input {}", postReq.getUrl());
            String existShortcut = shortcutService.getShortcutByUrl(postReq.getUrl());
            if (!StringUtil.isEmpty(existShortcut)) {
                log.info("[createShortcut] find exist shortcut {}", existShortcut);
                return RespConst.NewShortcutPostSuccResp(shortcutUtil.getRealShortcutUrl(existShortcut));
            }
            String shortcut = shortcutService.createShortcut(postReq.getUrl());
            log.info("[createShortcut] create new shortcut {}", shortcut);
            return RespConst.NewShortcutPostSuccResp(shortcutUtil.getRealShortcutUrl(shortcut));
        } catch (Exception e) {
            log.error("[createShortcut] err {}", postReq.getUrl(), e);
            return RespConst.NewShortcutPostSysErrResp();
        }
    }


}
