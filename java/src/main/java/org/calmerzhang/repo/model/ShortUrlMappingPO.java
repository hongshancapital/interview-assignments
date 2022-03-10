package org.calmerzhang.repo.model;

import lombok.Builder;
import lombok.Data;

/**
 * 长短域名映射PO
 *
 * @author calmerZhang
 * @create 2022/01/10 11:01 上午
 */
@Data
@Builder
public class ShortUrlMappingPO {
    private String longUrl;
    private String shortUrl;
}
