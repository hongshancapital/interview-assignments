package com.link.web;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.link.common.HttpResult;
import com.link.configuration.SentinelRuleConfig;
import com.link.enums.ResultInfoEnum;
import com.link.service.LinkService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * @auth zong_hai@126.com
 * @date 2022-04-15
 * @desc
 */
@Api(tags = {"长短域名转换服务"})
@Slf4j
@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @ApiOperation(value = "生成短域名", notes = "通过一定的算法把一个长域名映射成一个短域名调用方最好使用 content-type=x-www-form-urlencoded,避免转译导致的问题")
    @PostMapping("/generateShortLinkByLongLink.action")
    @SentinelResource(value = SentinelRuleConfig.QPS_LIMIT)
    public HttpResult<String> generateShortLinkByLongLink(
            @ApiParam(value = "长域名", required = true) @RequestParam(required = true) String longLink) {
        log.info("LinkController#generateShortLinkByLongLink(),request begin,longLink={}", longLink);
        HttpResult httpResult = null;
        try {
            if (StringUtils.isEmpty(longLink)) {
                log.info("LinkController#generateShortLinkByLongLink(),param longLink is empty");
                return HttpResult.createErrResult(ResultInfoEnum.SYS_PARAM_IS_NULL);
            }
            String shortLink = linkService.generateShortLinkByLongLink(longLink);
            httpResult = HttpResult.createSuccessResult(shortLink);
        } catch (Exception e) {
            log.error("LinkController#generateShortLinkByLongLink(),request begin,longLink={}", longLink, e);
            httpResult = HttpResult.createErrResult(ResultInfoEnum.SYS_EXCEPTION);
        }
        log.info("LinkController#generateShortLinkByLongLink(),request END,longLink={}", longLink);
        return httpResult;
    }


    @ApiOperation(value = "查询长域名", notes = "从内存映射中获取长域名,调用方最好使用 content-type=x-www-form-urlencoded，避免转译导致的问题")
    @GetMapping("/queryLongLinkByShortLink.action")
    @SentinelResource(value = SentinelRuleConfig.QPS_LIMIT)
    public HttpResult<String> queryLongLinkByShortLink(
            @ApiParam(value = "短域名", required = true) @RequestParam(required = true) String shortLink) {
        log.info("LinkController#queryLongLinkByShortLink(),request begin,shortLink={}", shortLink);
        HttpResult httpResult = null;
        try {
            if (StringUtils.isEmpty(shortLink)) {
                log.info("LinkController#queryLongLinkByShortLink(),param shortLink is empty");
                return HttpResult.createErrResult(ResultInfoEnum.SYS_PARAM_IS_NULL);
            }
            String longLink = linkService.queryLongLinkByShortLink(shortLink);
            httpResult = HttpResult.createSuccessResult(longLink);
        } catch (Exception e) {
            log.error("LinkController#queryLongLinkByShortLink(),request begin,shortLink={}", shortLink, e);
            httpResult = HttpResult.createErrResult(ResultInfoEnum.SYS_EXCEPTION);

        }
        log.info("LinkController#queryLongLinkByShortLink(),request END,shortLink={}", shortLink);
        return httpResult;
    }
}
