package com.lynnhom.sctdurlshortservice.model.po;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @description: Url映射表实体
 * @author: Lynnhom
 * @create: 2021-11-02 16:39
 **/

@Data
@Builder
public class ShortKey {
    private String shortKey;
    private String appId;
    private LocalDateTime expireTime;
}
