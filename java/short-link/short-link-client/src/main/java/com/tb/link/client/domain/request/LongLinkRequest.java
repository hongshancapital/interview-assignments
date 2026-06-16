package com.tb.link.client.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

/**
 * @author andy.lhc
 * @date 2022/4/16 13:02
 */
@Builder
@Getter
@Setter
@ToString
public class LongLinkRequest  implements Request {

    /**
     * 长链接
     * 【必选】
     */
    private String originLink ;

    /**
     * appName
     * 【必选】
     */
    private AppName appName ;

    /**
     * 过期时间（秒）
     * 【必选】
     */
    private int  expireTime ;

    /**
     * 生效开始时间
     * default=now
     * 【可选】
     */
    private Date startTime ;

    /**
     * 生效结束时间
     * 【可选】
     */
    private Date endTime ;

}
