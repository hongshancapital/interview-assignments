package com.tb.link.client.domain.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author andy.lhc
 * @date 2022/4/16 13:00
 */
@Builder
@Getter
@ToString
public class ShortLinkOriginResponse implements Response {

    /**
     * 长链接
     */
    private String originLink ;


}
