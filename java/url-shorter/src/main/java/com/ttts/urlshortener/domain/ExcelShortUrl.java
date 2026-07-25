package com.ttts.urlshortener.domain;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.ttts.urlshortener.base.util.NumberRadixUtils;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class ExcelShortUrl {
    @ExcelProperty("ID")
    @ColumnWidth(10)
    private Long id;

    @ExcelProperty("短链")
    @ColumnWidth(10)
    private Long surl;

    @ExcelProperty("短链字符串")
    @ColumnWidth(10)
    private String surlStr;

    @ExcelProperty("长链")
    @ColumnWidth(10)
    private String lurl;

    @ExcelProperty("长链MD5")
    @ColumnWidth(10)
    private String lmd5;

    @ExcelProperty("创建时间")
    @ColumnWidth(10)
    private LocalDateTime crateTime;

    @ExcelProperty("过期时间")
    @ColumnWidth(10)
    private LocalDateTime expiresTime;

    public static ExcelShortUrl from(ShortUrl shortUrl) {
        ExcelShortUrl record = new ExcelShortUrl();
        BeanUtil.copyProperties(shortUrl, record);
        String surlStr = NumberRadixUtils.decimalToSixtyTwo(shortUrl.getSurl());
        record.setSurlStr(surlStr);
        return record;
    }
}
