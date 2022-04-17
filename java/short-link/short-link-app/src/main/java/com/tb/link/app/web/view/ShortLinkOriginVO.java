package com.tb.link.app.web.view;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author andy.lhc
 * @date 2022/4/17 10:06
 */
@Builder
@Getter
@ToString
public class ShortLinkOriginVO implements Serializable {

    /**
     * 长链接
     */
    private String originLink ;
}
