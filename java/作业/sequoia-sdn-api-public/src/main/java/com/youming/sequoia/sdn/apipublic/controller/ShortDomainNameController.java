package com.youming.sequoia.sdn.apipublic.controller;


import com.youming.sequoia.sdn.apipublic.constant.ResponseResultMsgEnum;
import com.youming.sequoia.sdn.apipublic.constant.StoreConstant;
import com.youming.sequoia.sdn.apipublic.manager.IdGeneratorManager;
import com.youming.sequoia.sdn.apipublic.manager.NumberConvertManager;
import com.youming.sequoia.sdn.apipublic.utils.ResponseResultUtils;
import com.youming.sequoia.sdn.apipublic.vo.response.ResponseResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.constraints.NotBlank;

@Api(tags = {"短域名"})
@Controller
@RequestMapping("/sdn")
public class ShortDomainNameController {

    private static final Logger logger = LoggerFactory.getLogger(ShortDomainNameController.class);


    @Autowired
    private IdGeneratorManager idGeneratorManager;
    @Autowired
    private NumberConvertManager numberConvertManager;

    @Validated
    @ApiOperation(value = "存储接口", notes = "输入原URL得到短码", httpMethod = "POST")
    @RequestMapping("/save")
    @ResponseBody
    public ResponseResultVO<String> save(@ApiParam(value = "原始Url", required = true) @RequestParam(value = "url", required = true) @NotBlank String url) {

        String shortUrl = "";
        synchronized (this) {
            shortUrl = StoreConstant.lruCache.get(url);      //检查短url是否已经存在
            if (StringUtils.isNotBlank(shortUrl))
                return ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.SUCCESS, shortUrl, "");
            long id = idGeneratorManager.getId();
            shortUrl = numberConvertManager.toOtherNumberSystem(id, 62);
            StoreConstant.lruCache.put(url, shortUrl);
            StoreConstant.shortUrlMap.put(shortUrl, url);
        }

        return ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.SUCCESS, shortUrl, "");
    }


    @Validated
    @ApiOperation(value = "读取接口", notes = "输入短码得到原URL", httpMethod = "GET")
    @RequestMapping("/get")
    @ResponseBody
    public ResponseResultVO<String> get(@ApiParam(value = "短码", required = true) @RequestParam(value = "shortUrl", required = true) @NotBlank String shortUrl) {

        String url = StoreConstant.shortUrlMap.get(shortUrl);

        return ResponseResultUtils.getResponseResult(ResponseResultMsgEnum.SUCCESS, url, "");
    }

}
