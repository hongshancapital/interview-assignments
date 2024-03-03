package com.tb.link.client.domain.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.Date;

/**
 * @author andy.lhc
 * @date 2022/4/16 12:55
 */
@Builder
@Getter
@ToString
public class ShortLinkResponse implements Response {

    /**
     * 完整的短链接
     */
    private String  shortLink ;

    /**
     * 短链接值，eg:45Abcdfg
     */
    private String shortLinkKey ;

    /**
     * 生效开始时间
     */
    private Date startTime ;

    /**
     * 生效结束时间
     */
    private Date endTime ;

}
