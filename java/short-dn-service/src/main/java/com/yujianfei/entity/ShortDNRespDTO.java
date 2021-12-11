package com.yujianfei.entity;

import lombok.Data;

@Data
public class ShortDNRespDTO {

    public ShortDNRespDTO(String shortDN){
        this.shortDN=shortDN;
    }

    /**
     * 返回短域名
     */
    String shortDN;

}
