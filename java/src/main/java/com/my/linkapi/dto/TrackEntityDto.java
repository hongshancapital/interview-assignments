package com.my.linkapi.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TrackEntityDto {
    private String key;
    private LocalDateTime cdate;
}
