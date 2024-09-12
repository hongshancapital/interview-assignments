package com.scdt.shortlink.controller;

import com.scdt.shortlink.exception.ErrorEnum;
import com.scdt.shortlink.entity.CommonResponse;
import com.scdt.shortlink.service.Link8Service;
import com.scdt.shortlink.util.StringUtil;
import com.scdt.shortlink.util.UrlValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Api("短链生成以及转换控制器")
@Controller
@Slf4j
public class LinkController {
    @Autowired
    private Link8Service link8Service;

    /**
     * 当前只实现根据自增id进行转换
     * 由于不同长度的短链个数不一致，可根据需求设计短链生成规则
     * @param url 需要转换的长链接
     * @return
     */
    @ApiOperation(value = "长链转换为短链")
    @ApiParam(name = "url", value = "长链接")
    @PostMapping("/transform")
    @ResponseBody
    public CommonResponse<String> transform(String url) {
        String key = null;
        if (UrlValidator.valid(url)) {
            key = link8Service.createLink(url);
        }
        CommonResponse<String> response;
        if (StringUtil.isNotEmpty(key)) {
            response = CommonResponse.success(key);
        } else {
            response = CommonResponse.fail(ErrorEnum.INVALID_URL);
        }
        // 链接转换进行记录，可用作后续统计
        log.info("Transform info:".concat(response.toString()));
        return response;
    }

    /**
     * 短连接转换成为长链接
     * @param key 短域名
     * @return
     */
    @ApiOperation("根据短链获取长链")
    @GetMapping("/getUrl/{key}")
    @ResponseBody
    public CommonResponse<String> getUrl(@PathVariable String key) {
        String url = null;
        if (StringUtil.isNotEmpty(key) && 8 == key.length()) {
            url = link8Service.getUrl(key);
        }
        CommonResponse result;
        if (StringUtil.isNotEmpty(url)) {
            result = CommonResponse.success(url);
        } else {
            result = CommonResponse.fail(ErrorEnum.INVALID_KEY);
        }
        log.info("GetUrl info:".concat(result.toString()));
        return result;
    }
}
