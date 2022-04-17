package com.tb.link.client.domain.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

/**
 * 短链参数
 * @author andy.lhc
 * @date 2022/4/16 13:04
 */
@Builder
@Getter
@ToString
public class ShortLinkParamsRequest implements Request {

    private Map<String,String> params ;


}
