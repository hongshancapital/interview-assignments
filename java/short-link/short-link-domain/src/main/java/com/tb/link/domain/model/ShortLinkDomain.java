package com.tb.link.domain.model;

import com.tb.link.domain.model.enums.ErrorCodeEnum;
import com.tb.link.infrastructure.exception.TbRuntimeException;
import com.tb.link.infrastructure.util.HttpUtil;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * 长短链的领域模型
 *
 * @author andy.lhc
 * @date 2022/4/16 23:56
 */
@Builder
@Getter
@ToString
public class ShortLinkDomain {

    /**
     * 短链 8位
     */
    @Setter
    private String shortLink;

    /**
     * 短链 完整的key
     */
    @Setter
    private String shortFullLink;

    /**
     * 长链
     */
    private String originLink;

    /**
     * 过期时间（秒）
     */
    private int expire;

    /**
     * 生效时间
     */
    private Date effectTime;

    /**
     * 失效时间
     */
    private Date expireTime;

    public void checkSchema() {
        if (!HttpUtil.checkHttpAndHttps(this.originLink)) {
            throw new TbRuntimeException(ErrorCodeEnum.SUPPORT_HTTP_OR_HTTPS.getErrorCode()
                    , ErrorCodeEnum.SUPPORT_HTTP_OR_HTTPS.getErrorMsg());
        }
    }

    public void checkLinkLength(int originLinkLength) {
        if (this.originLink.length() > originLinkLength) {
            throw new TbRuntimeException(ErrorCodeEnum.LONG_LINK_LENGTH_LESS_THAN_4096.getErrorCode()
                    , ErrorCodeEnum.LONG_LINK_LENGTH_LESS_THAN_4096.getErrorMsg());
        }
    }

    public void checkOriginLinkWhiteList(List<String> originLinkDomainWhiteList) {
        if (CollectionUtils.isEmpty(originLinkDomainWhiteList)) {
            return;
        }
        boolean flag = originLinkDomainWhiteList.stream()
                .anyMatch(whiteDomain -> this.originLink.indexOf(whiteDomain) > 0);
        if (!flag) {
            throw new TbRuntimeException(ErrorCodeEnum.ORIGIN_SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorCode()
                    , ErrorCodeEnum.ORIGIN_SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorMsg());
        }
    }

}
