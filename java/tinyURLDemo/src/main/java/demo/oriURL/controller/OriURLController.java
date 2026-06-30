package demo.oriURL.controller;

import demo.common.result.Result;
import demo.common.result.ResultUtil;
import demo.common.utils.URLHelper;
import demo.entity.URLEntity;
import demo.oriURL.dto.OriURLRequest;
import demo.oriURL.service.OriURLService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 短域名生成请求接收器
 */
@RestController
@RequestMapping("/api/url/ori/v1")
public class OriURLController {
    private final OriURLService oriURLService;

    @Autowired
    public OriURLController(OriURLService oriURLService) {
        this.oriURLService = oriURLService;
    }

    @ApiOperation("将 URL 转换为短域名")
    @PostMapping("/")
    public Result<String> transUrl(@RequestBody OriURLRequest oriURLRequest) {
        if (!URLHelper.validURL(oriURLRequest.getOriURL())) {
            return ResultUtil.success("入参为非法 URL！", null);
        }

        URLEntity urlEntity = this.oriURLService.trans(oriURLRequest.getOriURL());

        if (Objects.isNull(urlEntity)) {
            return ResultUtil.fail("获取短域名失败，请联系管理员！");
        } else {
            return ResultUtil.success(urlEntity.getCurURL());
        }
    }
}
