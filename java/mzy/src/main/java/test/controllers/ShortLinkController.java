package test.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import test.services.ShortLinkService;
import test.vo.ResultVo;

@RestController
@Slf4j
@Validated
@Api(tags = "短地址控制器")
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;

    @GetMapping(value = {"/getLongLink"})
    @ApiOperation(value = "短域名读取接口:通过短地址得到长地址")
    @ApiImplicitParam(value = "需要转换的短网址", name = "shortLink", paramType = "query")
    public ResultVo<String> getLongLink(@NotBlank(message = "短网址不能为空")
    @Length(max = 8, message = "短网址最长不能超过8个字符") String shortLink) {
        String longLink = shortLinkService.getLongLink(shortLink);
        return StringUtils.isBlank(longLink) ?
            ResultVo.asError("未能找到匹配的长域名", null) :
            ResultVo.asSuccess("操作成功", longLink);
    }

    @GetMapping("/getShortLink")
    @ApiOperation(value = "短域名存储接口:通过长地址得到短地址")
    @ApiImplicitParam(value = "需要转换的长网址", name = "longLink", paramType = "query")
    public ResultVo<String> getShortLink(@NotBlank @Length(max = 512, message = "长网址最长512个字符") String longLink) {
        String shortLink = shortLinkService.getShortLink(longLink);
        return ResultVo.asSuccess("短网址转换成功", shortLink);
    }
}
