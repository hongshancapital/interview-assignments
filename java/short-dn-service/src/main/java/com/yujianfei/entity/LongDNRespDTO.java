package com.yujianfei.entity;

import lombok.Data;

@Data
public class LongDNRespDTO {

    /**
     * 长域名
     */
    String longDN;

    public LongDNRespDTO(String longDN) {
        this.longDN = longDN;
    }

}
