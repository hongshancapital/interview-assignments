package com.yujianfei.entity;

import lombok.Data;

@Data
public class ShortDNReqDTO {

    /**
     * 请求短域名
     */
    String shortDN;

    public ShortDNReqDTO(String shortDN){
        this.shortDN=shortDN;
    }

}
