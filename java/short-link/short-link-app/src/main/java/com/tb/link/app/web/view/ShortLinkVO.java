package com.tb.link.app.web.view;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author andy.lhc
 * @date 2022/4/17 9:56
 */
@Builder
@Getter
@ToString
public class ShortLinkVO implements Serializable {

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
    private String startTime ;

    /**
     * 生效结束时间
     */
    private String endTime ;
}
