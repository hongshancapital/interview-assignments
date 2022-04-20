package com.shortlink.controller;

import com.shortlink.common.GeneralException;
import com.shortlink.common.ResponseBuilder;
import com.shortlink.common.Result;
import com.shortlink.common.Status;
import com.shortlink.entity.CreateShortLinkRequest;
import com.shortlink.entity.FetchOriginalUrlRequest;
import com.shortlink.entity.ShortLinkEntity;
import com.shortlink.handler.ShortLinkHandler;
import com.shortlink.util.ConvertRequestUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * url转换
 *
 */

@Slf4j
@RestController
@Api(description = "短链服务")
public class URLTransformController {

    @Autowired
    private ShortLinkHandler shortLinkHandler;

    /**
     * 通过正常url链接获取短链
     * @param url
     * @param appid  业务方标识
     * @return
     */
    @GetMapping("/shortlink/common/create")
    @ApiOperation("短链生成")
    public Result<ShortLinkEntity> createShortLink(@ApiParam("url") String url,
                                          @ApiParam("appid") Integer appid,
                                          @ApiParam("reqid") String reqid) {
        Result result = null;
        try {
            CreateShortLinkRequest request = ConvertRequestUtil.createShortLinkRequest(reqid, url, appid);
            ShortLinkEntity shortLink = shortLinkHandler.createShortLink(request);
            result = ResponseBuilder.buildSuccess(shortLink);

        } catch (GeneralException e) {
            // 捕获已定义的异常
            result = ResponseBuilder.bulidError(e);
        } catch (Exception e) {
            result = ResponseBuilder.bulidError(Status.SERVER_ERROR.getCode(), Status.SERVER_ERROR.getDesc());
            log.error("URLTransformController getShortLink url:{} error ", url, e);
        }
        return result;
    }

    /**
     * 通过正常url链接获取短链
     * @param
     * @return
     */
    @GetMapping("/shortlink/common/get")
    @ApiOperation("通过短链获取正常链接")
    public Result<ShortLinkEntity> getUrl(@ApiParam("shortLink") String shortLink,
                                 @ApiParam("reqId") String reqId,
                                 @ApiParam("appId") Integer appId) {

        Result result = null;
        try {
            FetchOriginalUrlRequest request = ConvertRequestUtil.fetchOriginalUrlRequest(reqId, shortLink, appId);
            ShortLinkEntity entity = shortLinkHandler.fetchUrlByShortLink(request);
            result = ResponseBuilder.buildSuccess(entity);
        } catch (GeneralException e) {
            // 捕获已定义的异常
            result = ResponseBuilder.bulidError(e);
        } catch (Exception e) {
            result = ResponseBuilder.bulidError(Status.SERVER_ERROR.getCode(), Status.SERVER_ERROR.getDesc());
            log.error("URLTransformController getUrl error. shortLink:{}", shortLink, e);
        }
        return result;
    }
}
