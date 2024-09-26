package com.scdt.shortlink.service.impl;

import com.scdt.shortlink.client.service.LinksService;
import com.scdt.shortlink.client.domain.BaseErrorCode;
import com.scdt.shortlink.client.domain.ShortLinkErrorCode;
import com.scdt.shortlink.client.dto.Result;
import com.scdt.shortlink.contants.ShortLinkContants;
import com.scdt.shortlink.core.utils.LinkUtils;
import com.scdt.shortlink.manager.ShortLinkManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 链接服务
 *
 * @Author tzf
 * @Date 2022/4/30
 */
@Slf4j
@Service
public class LinksServiceImpl implements LinksService {
    /**
     * 短链处理业务层接口
     */
    @Resource
    private ShortLinkManager shortLinkManager;

    /**
     * 长链转短链
     *
     * @param link 长链
     * @return 短链
     */
    @Override
    public Result<String> getShortLink(String link) {
        try {
            //校验入参
            Result<String> result = checkOriginalLink(link);
            if (!result.getResult()) {
                return result;
            }

            String shortLink = shortLinkManager.createShortLink(link);
            result.setModel(shortLink);
            return result;

        } catch (Exception e) {
            log.error("生成短链异常", e);
            return Result.error(BaseErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 短链兑换长链
     *
     * @param shortLink
     * @return
     */
    @Override
    public Result<String> getOriginalLink(String shortLink) {
        try {
            //校验入参
            Result<String> result = checkShortLink(shortLink);
            if (!result.getResult()) {
                return result;
            }

            String originalLink = shortLinkManager.getOriginalLink(shortLink);

            if (StringUtils.isEmpty(originalLink)) {
                return Result.error(ShortLinkErrorCode.ORIGINAL_LINK_NOT_EXIST);
            }

            result.setModel(originalLink);
            return result;

        } catch (Exception e) {
            log.error("获取短链异常", e);
            return Result.error(BaseErrorCode.SYSTEM_ERROR);
        }
    }

    /**
     * 校验长链
     *
     * @param link 长链
     * @return
     */
    private Result<String> checkOriginalLink(String link) {
        if (StringUtils.isEmpty(link)) {
            return Result.error(ShortLinkErrorCode.ORIGINAL_LINK_EMPTY);
        }

        if (!LinkUtils.checkLinkRule(link)) {
            return Result.error(ShortLinkErrorCode.ORIGINAL_LINK_RULE_ERROR);
        }

        return Result.success();
    }

    /**
     * 校验短链
     *
     * @param shortLink
     * @return
     */
    private Result<String> checkShortLink(String shortLink) {
        if (StringUtils.isEmpty(shortLink)) {
            return Result.error(ShortLinkErrorCode.SHORT_LINK_EMPTY);
        }

        if (StringUtils.length(shortLink) != ShortLinkContants.SHORT_LINK_COUNT) {
            return Result.error(ShortLinkErrorCode.SHORT_LINK_RULE_ERROR);
        }

        return Result.success();
    }
}
