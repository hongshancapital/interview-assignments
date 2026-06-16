package com.tb.link.service.shortlink;

import com.tb.link.client.ShortLinkService;
import com.tb.link.client.domain.Result;
import com.tb.link.client.domain.reponse.ShortLinkOriginResponse;
import com.tb.link.client.domain.reponse.ShortLinkResponse;
import com.tb.link.client.domain.request.LongLinkRequest;
import com.tb.link.client.domain.request.ShortLinkRequest;
import com.tb.link.domain.model.ShortLinkDomain;
import com.tb.link.domain.model.enums.ErrorCodeEnum;
import com.tb.link.domain.service.ShortLinkDataDomainService;
import com.tb.link.domain.service.ShortLinkDomainService;
import com.tb.link.infrastructure.config.ShortLinkConfig;
import com.tb.link.infrastructure.exception.TbRuntimeException;
import com.tb.link.infrastructure.util.Base62String;
import com.tb.link.service.util.ExecuteFunUtil;
import com.tb.link.infrastructure.util.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @author andy.lhc
 * @date 2022/4/16 13:13
 */
@Service
public class ShortLinkServiceImpl implements ShortLinkService {


    @Resource
    private ShortLinkConfig shortLinkConfig;

    @Resource
    private ShortLinkDomainService shortLinkDomainService;

    @Resource
    private ShortLinkDataDomainService shortLinkDataDomainService;

    @Override
    public Result<ShortLinkResponse> generalShortLink(LongLinkRequest request) {
        return ExecuteFunUtil.execute(request, "ShortLinkService.generalShortLink", () -> {
            checkParam(request);

            ShortLinkDomain linkDomain = buildShortLinkDomain(request);
            generateShortWithRequest(linkDomain);

            shortLinkDataDomainService.saveShort2LongLink(linkDomain);
            return Result.success(buildShortLinkResponse(linkDomain));
        });
    }

    private void generateShortWithRequest(ShortLinkDomain linkDomain) {
        // 带有一定随机性，小于8位，有一个是承机产生
        String shortLink = shortLinkDomainService.generateShorLink(linkDomain.getOriginLink());

        /*
        boolean exist = Objects.nonNull(shortLinkDataDomainService.getOriginLink(shortLink));
        if (exist) {
            shortLink = generateRetryShortLink(linkDomain.getOriginLink());
            if (StringUtils.isBlank(shortLink)) {
                throw new TbRuntimeException("generate.short.link.error", "产生短链失败,请重试");
            }
        }
         */
        linkDomain.setShortLink(shortLink);
        linkDomain.setShortFullLink(shortLinkConfig.getShortLinkPrefix() + linkDomain.getShortLink());
        ;
    }

    @Override
    public Result<ShortLinkOriginResponse> recoverShortLink(ShortLinkRequest request) {
        return ExecuteFunUtil.execute(request, "ShortLinkService.recoverShortLink", () -> {
            checkParamWithShortLink(request);

            String originLink = shortLinkDataDomainService.getLongLink(
                    StringUtils.substringAfterLast(request.getShortLink(), "/")
            );
            //取出后，可再设置param替换,后续替代实现
            ShortLinkOriginResponse response = ShortLinkOriginResponse.builder()
                    .originLink(originLink)
                    .build();
            return Result.success(response);
        });
    }

    private void checkParam(LongLinkRequest request) {
        if (Objects.isNull(request)
                || Objects.isNull(request.getAppName())
                || StringUtils.isBlank(request.getAppName().getAppKey())
                || StringUtils.isBlank(request.getOriginLink())
                || request.getExpireTime() < 1) {
            throw new TbRuntimeException(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode()
                    , ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg());
        }
        checkAppKeyWhiteList(request.getAppName().getAppKey());
    }


    private void checkParamWithShortLink(ShortLinkRequest request) {
        if (Objects.isNull(request)
                || Objects.isNull(request.getAppName())
                || StringUtils.isBlank(request.getShortLink())) {
            throw new TbRuntimeException(ErrorCodeEnum.PARAM_IS_INVALID.getErrorCode()
                    , ErrorCodeEnum.PARAM_IS_INVALID.getErrorMsg());
        }
        checkSchema(request.getShortLink());
        checkShortLinkPrefix(request.getShortLink());
        checkShortLinkLength(request.getShortLink());
        checkShortLinkValid(request.getShortLink());
        checkAppKeyWhiteList(request.getAppName().getAppKey());
    }




    private boolean checkSchema(String url) {
        if (!HttpUtil.checkHttpAndHttps(url)) {
            throw new TbRuntimeException(ErrorCodeEnum.SUPPORT_HTTP_OR_HTTPS.getErrorCode()
                    , ErrorCodeEnum.SUPPORT_HTTP_OR_HTTPS.getErrorMsg());
        }
        return true;
    }

    private void checkShortLinkPrefix(String shortLink) {
        if(shortLink.indexOf(shortLinkConfig.getShortLinkPrefix()) < 0 ){
            throw new TbRuntimeException(ErrorCodeEnum.SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorCode()
                    , ErrorCodeEnum.SHORT_LINK_IS_NOT_IN_WHITELIST.getErrorMsg());
        }
    }

    private void checkShortLinkLength(String shortLink) {
        String linkKey = StringUtils.substringAfterLast(shortLink, "/");
        if (linkKey.length() > shortLinkConfig.getShortLinkLength()) {
            throw new TbRuntimeException(ErrorCodeEnum.SHORT_LINK_LENGTH_LESS_THAN_8.getErrorCode()
                    , ErrorCodeEnum.SHORT_LINK_LENGTH_LESS_THAN_8.getErrorMsg());
        }
    }

    private boolean checkShortLinkValid(String shortLinkKey) {
        String linkKey = StringUtils.substringAfterLast(shortLinkKey, "/");
        boolean flag = false;
        char[] chars = linkKey.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Base62String.BASE_62.indexOf(String.valueOf(chars[i])) < 0) {
                flag = true;
                break;
            }
        }
        if (flag) {
            throw new TbRuntimeException(ErrorCodeEnum.SHORT_LINK_IN_VALID.getErrorCode()
                    , ErrorCodeEnum.SHORT_LINK_IN_VALID.getErrorMsg());
        }

        return true;
    }


    private ShortLinkResponse buildShortLinkResponse(ShortLinkDomain linkDomain) {
        return ShortLinkResponse.builder()
                .endTime(linkDomain.getExpireTime())
                .shortLink(linkDomain.getShortFullLink())
                .startTime(linkDomain.getEffectTime())
                .shortLinkKey(linkDomain.getShortLink())
                .build();
    }

    private ShortLinkDomain buildShortLinkDomain(LongLinkRequest request) {
        Date effectTime = Objects.isNull(request.getStartTime())
                ? new Date()
                : request.getStartTime();
        ShortLinkDomain linkDomain = ShortLinkDomain.builder()
                .originLink(request.getOriginLink())
                .effectTime(effectTime)
                .expire(request.getExpireTime())
                .expireTime(
                        Objects.isNull(request.getEndTime())
                                ? DateUtils.addSeconds(effectTime, request.getExpireTime())
                                : request.getEndTime()
                )
                .build();
        linkDomain.checkSchema();
        linkDomain.checkLinkLength(shortLinkConfig.getOriginLinkLength());
        linkDomain.checkOriginLinkWhiteList(shortLinkConfig.getOriginLinkDomainWhiteList());

        return linkDomain;
    }

    private void checkAppKeyWhiteList(String appKey) {
        if (CollectionUtils.isEmpty(shortLinkConfig.getAppKeyWhiteList())) {
            return;
        }
        boolean flag = shortLinkConfig.getAppKeyWhiteList().stream()
                .anyMatch(whiteAppKey -> StringUtils.equals(whiteAppKey, appKey));

        if (!flag) {
            throw new TbRuntimeException(ErrorCodeEnum.APP_KEY_IS_NOT_IN_WHITELIST.getErrorCode()
                    , ErrorCodeEnum.APP_KEY_IS_NOT_IN_WHITELIST.getErrorMsg());
        }
    }

    /*
    private String generateRetryShortLink(String originLink) {
        int i = shortLinkConfig.getShortLinkRetryCount();
        String shortLink = "";
        while (i > 0) {
            //重新产生，增加4位随机数
            shortLink = shortLinkDomainService.generateShortLinkWithRandom(originLink, 4);

            if (Objects.isNull(shortLinkDataDomainService.getOriginLink(shortLink))) {
                break;
            }
            i--;
        }

        return shortLink;
    }

     */


}
