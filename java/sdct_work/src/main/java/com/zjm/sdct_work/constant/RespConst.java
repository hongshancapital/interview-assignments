package com.zjm.sdct_work.constant;

import com.zjm.sdct_work.controller.api.ShortcutGetResp;
import com.zjm.sdct_work.controller.api.ShortcutPostResp;

/**
 * Author:   billzzzhang
 * Date:     2022/3/19 下午5:23
 * Desc:
 */
public class RespConst {

    public static final String SuccRespCode = "000000";
    public static final String ParamErrRespCode = "000001";
    public static final String RateLimitErrRespCode = "000002";
    public static final String SystemErrRespCode = "000003";
    public static final String WrongDataErrRespCode = "000004";


    public static final String SuccRespMsg = "SUCCESS";
    public static final String ParamErrRespMsg = "PARAM ERR";
    public static final String RateLimitErrRespMsg = "RATELIMIT ERR";
    public static final String SystemErrRespMsg = "SYSTEM ERR";
    public static final String WrongDataErrRespMsg = "WRONG DATA";


    public static ShortcutGetResp NewShortcutGetSuccResp(String url) {
        return new ShortcutGetResp(url, SuccRespCode, SuccRespMsg);
    }

    public static ShortcutGetResp NewShortcutGetSysErrResp() {
        return new ShortcutGetResp("", SystemErrRespCode, SystemErrRespMsg);
    }

    public static ShortcutGetResp NewShortcutGetDataErrResp() {
        return new ShortcutGetResp("", WrongDataErrRespCode, WrongDataErrRespMsg);
    }

    public static ShortcutGetResp NewShortcutGetParamErrResp() {
        return new ShortcutGetResp("", ParamErrRespCode, ParamErrRespMsg);
    }


    public static ShortcutPostResp NewShortcutPostSuccResp(String shortcut) {
        return new ShortcutPostResp(shortcut, SuccRespCode, SuccRespMsg);
    }

    public static ShortcutPostResp NewShortcutPostSysErrResp() {
        return new ShortcutPostResp("", SystemErrRespCode, SystemErrRespCode);
    }

    public static ShortcutPostResp NewShortcutPostParamErrResp() {
        return new ShortcutPostResp("", ParamErrRespCode, ParamErrRespMsg);
    }

}
