package com.sequoiacap.domain.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.io.Serializable;

/**
 * 创建长短链映射请求
 */
@RequiredArgsConstructor
@Getter
public class CreateUrlMapResponse implements Serializable {

    private final String shortUrl;
}
