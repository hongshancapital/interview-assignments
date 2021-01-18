package com.wb.shorturl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.wb.shorturl.tools.Utils;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author bing.wang
 * @date 2021/1/8
 */

@Getter
@Setter
@NoArgsConstructor
@TableName("short_url")
public class ShortUrl {

    private long id;
    private String shortCode;
    private String originUrl;
    private int originUrlHash;
    private long createTimestamp;

    public ShortUrl(String shortCode, String originUrl, long createTimestamp) {
        this.shortCode = shortCode;
        this.originUrl = originUrl;
        this.originUrlHash = originUrl.hashCode();
        this.createTimestamp = createTimestamp;
    }



}
