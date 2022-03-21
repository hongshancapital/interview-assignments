package com.zjm.sdct_work.controller.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author:   billzzzhang
 * Date:     2022/3/19 下午6:50
 * Desc:
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "create shortcut req entity")
public class ShortcutPostReq implements Serializable {
    private static final long serialVersionUID = -1242493306307174690L;
    @JsonProperty(required = false)
    private String url;
}
