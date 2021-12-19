package com.my.linkapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackEntityDto {
    // 追踪短链接内容
    private String key;
    // 短链接的创建时间
    private LocalDateTime cdate;
}
