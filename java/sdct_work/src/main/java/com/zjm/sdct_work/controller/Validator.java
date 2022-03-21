package com.zjm.sdct_work.controller;

import com.zjm.sdct_work.controller.api.ShortcutPostReq;
import com.zjm.sdct_work.service.TokenService;
import com.zjm.sdct_work.util.StringUtil;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 下午8:55
 * Desc:
 */
@Component
public class Validator {

    @Autowired
    private TokenService tokenService;


    private UrlValidator urlValidator = new UrlValidator();


    public boolean validateShortcutPath(String shortcutPath) {
        if (StringUtil.isEmpty(shortcutPath)) {
            return false;
        }
        if (shortcutPath.length() != 8) {
            return false;
        }
        return true;
    }

    public boolean validateShortcutPostReq(ShortcutPostReq req) {
        return validateUrl(req.getUrl());
    }


    public boolean validateUrl(String url) {
        if (StringUtil.isEmpty(url)) {
            return false;
        }
        return urlValidator.isValid(url);
    }

    public boolean validateToken(String token) {
        if (StringUtil.isEmpty(token)) {
            return false;
        }
        return tokenService.checkToken(token);
    }

}
