package com.scdt.yulinfu.doamin;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Table;

/**
 * @author yulinfu
 * @description
 * @data 2021/10/15
 */
@Data
@Builder
@Table(name = "url_storage")
@NoArgsConstructor
@AllArgsConstructor
public class UrlStorage {

    /**
     * 自增id
     */
    private Long id;

    /**
     * 短链接
     */
    private String shortUrl;

    /**
     * 长链接
     */
    private String longUrl;

}
