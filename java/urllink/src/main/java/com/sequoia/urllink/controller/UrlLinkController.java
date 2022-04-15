package com.sequoia.urllink.controller;

import com.sequoia.urllink.base.model.ResultMessage;
import com.sequoia.urllink.bean.GenCodeParam;
import com.sequoia.urllink.constant.Global;
import com.sequoia.urllink.controller.base.AbstractController;
import com.sequoia.urllink.service.IUrlMapService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 短域名接口
 * @author liuhai
 * @date 2022.4.15
 */
@Api(value = "长短域名转换接口")
@RestController
@RequestMapping(Global.SHORT_MIDDLE_PATH)
@Validated
public class UrlLinkController extends AbstractController {

    @Autowired
    private IUrlMapService urlMapService;

    @PostMapping("/gencode")
    @ApiOperation(value = "生成短域名", notes = "longUrl为必填参数")
    public ResultMessage createShortCode(@ApiParam(value = "长链", required = true) @NotBlank(message = "长链不能为空") @RequestParam String longUrl,
                                         @ApiParam(value = "附加字串") @Length(max = 1024) @RequestParam(defaultValue = "") String attach) {
        String shortUrl = urlMapService.genCode(longUrl, attach);
        ResultMessage msg = new ResultMessage<String>(ResultMessage.ResultCode.SUCCESS.getCode(),
                ResultMessage.ResultCode.SUCCESS.getName(), shortUrl);
        return msg;
    }

    @PostMapping("/gencodes")
    @ApiOperation(value = "批量生成短域名", notes = "longUrl为必填参数")
    public ResultMessage<List<String>> createShortCodes(@RequestBody GenCodeParam param) {
        List<String> shortUrlList = urlMapService.genCodes(param);
        ResultMessage<List<String>> msg = new ResultMessage<>(ResultMessage.ResultCode.SUCCESS.getCode(),
                ResultMessage.ResultCode.SUCCESS.getName(), shortUrlList);
        return msg;
    }


    @GetMapping("/{shortCode}")
    @ApiOperation(value = "短域名跳转")
    public void transCode(HttpServletResponse res, @PathVariable String shortCode) throws IOException {
        res.sendRedirect(urlMapService.getLongUrl(shortCode));
    }

}
