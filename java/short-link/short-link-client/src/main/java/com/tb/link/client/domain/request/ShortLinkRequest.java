package com.tb.link.client.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author andy.lhc
 * @date 2022/4/16 12:54
 */
@Builder
@Getter
@Setter
@ToString
public class ShortLinkRequest implements Request {

    /**
     * appName
     * 【必选】
     */
    private AppName  appName ;

    /**
     * 完整的短链接
     * 【必选】
     */
    private String  shortLink ;


    /**
     * 短链传长链参数
     * 【可选】
     */
    private ShortLinkParamsRequest paramsRequest ;
}
