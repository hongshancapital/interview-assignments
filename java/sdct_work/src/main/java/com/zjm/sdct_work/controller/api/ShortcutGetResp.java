package com.zjm.sdct_work.controller.api;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 下午6:46
 * Desc:
 */
@Data
@AllArgsConstructor
@ApiModel(value = "get shortcut resp entity")
public class ShortcutGetResp implements Serializable {
    private String url;
    private String code;
    private String msg;
}

