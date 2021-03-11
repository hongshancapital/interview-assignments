package com.zxb.rungo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LongToShort {
    private Integer id;

    private String shortAddress;

    private String longAddress;
}