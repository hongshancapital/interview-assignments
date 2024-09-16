package com.tb.link.client.domain.request;

import lombok.Builder;
import lombok.Getter;

/**
 * @author andy.lhc
 * @date 2022/4/16 12:57
 */
@Builder
@Getter
public class AppName implements Request {

    private String appKey ;


}
