package com.zjm.sdct_work.controller.api;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Author:   billzzzhang
 * Date:     2022/3/20 下午6:49
 * Desc:
 */

@Data
@AllArgsConstructor
@ApiModel(value = "create shortcut resp entity")
public class ShortcutPostResp implements Serializable {
    private String shortcut;
    private String code;
    private String msg;
}
