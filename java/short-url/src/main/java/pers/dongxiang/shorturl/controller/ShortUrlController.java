package pers.dongxiang.shorturl.controller;

import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.dongxiang.shorturl.dto.OriginUrlDTO;
import pers.dongxiang.shorturl.dto.ShortUrlDTO;
import pers.dongxiang.shorturl.exception.CheckCodeExecption;
import pers.dongxiang.shorturl.exception.UrlExecption;
import pers.dongxiang.shorturl.service.ShortUrlService;
import pers.dongxiang.shorturl.util.R;
import pers.dongxiang.shorturl.util.ShortUrlUtil;
import pers.dongxiang.shorturl.util.ValidationUtil;

/**
 * @ProjectName short-url
 * @Package pers.dongxiang.shorturl.controller
 * @ClassName ShortUrlController
 * @Description 短地址服务接口
 * @Company lab
 * @Author dongxiang
 * @Date 10/31/2021 1:08 PM
 * @UpdateUser
 * @UpdateDate
 * @UpdateRemark
 * @Version 1.0.0
 */
@Api(value = "短地址服务接口")
@RestController
@RequestMapping("/url")
public class ShortUrlController {

    @Autowired
    private ShortUrlService shortUrlService;


    /**
     * @Title createShortUrl
     * @Description 根据长地址生成短地址
     * @param originUrlDTO
     * @return pers.dongxiang.shorturl.util.R
     * @author dongxiang
     * @date 10/31/2021 1:16 PM
     * @UpdateUser
     * @UpdateDate
     * @UpdateRemark
     */
    @ApiOperation(value = "根据长地址生成短地址", notes = "")
    @ApiResponses({
            @ApiResponse(code=200,message="请求正确"),
            @ApiResponse(code=500,message="请求错误")
    })
    @ApiImplicitParam(name = "originUrl",value = "源地址",required = true,dataType = "string")
    @RequestMapping(path = "/create",method = RequestMethod.POST)
    public R createShortUrl(OriginUrlDTO originUrlDTO){

        boolean isUrl =  ValidationUtil.isUrl(originUrlDTO.getOriginUrl());
        if(!isUrl){
            throw new UrlExecption();
        }

        String shortUrl = shortUrlService.createShortUrl(originUrlDTO.getOriginUrl());
        return R.ok(shortUrl);
    }


    /**
     * @Title getOriginUrl
     * @Description 根据短地址返回长地址
     * @param shortUrlDTO
     * @return pers.dongxiang.shorturl.util.R
     * @author dongxiang
     * @date 10/31/2021 1:17 PM
     * @UpdateUser
     * @UpdateDate
     * @UpdateRemark
     */
    @ApiOperation(value = "根据短地址返回长地址", notes = "")
    @ApiResponses({
            @ApiResponse(code=200,message="请求正确"),
            @ApiResponse(code=500,message="请求错误")
    })
    @ApiImplicitParam(name = "shortUrl",value = "短地址",required = true,dataType = "string")
    @RequestMapping(path = "/search",method = RequestMethod.GET)
    public R getOriginUrl(ShortUrlDTO shortUrlDTO){

        boolean isUrl =  ValidationUtil.isUrl(shortUrlDTO.getShortUrl());
        if(!isUrl){
            throw new UrlExecption();
        }

        String[] urlParam = shortUrlDTO.getShortUrl().split("/");
        boolean checkCodeStatus = ShortUrlUtil.checkShortUrl(urlParam[urlParam.length - 1]);
        if (!checkCodeStatus){
            throw new CheckCodeExecption();
        }

        String originUrl = shortUrlService.getOriginUrl(shortUrlDTO.getShortUrl());
        return R.ok(originUrl);
    }

}
