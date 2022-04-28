package demo.curURL.controller;

import demo.common.result.Result;
import demo.common.result.ResultUtil;
import demo.common.utils.URLHelper;
import demo.curURL.dto.CurURLRequest;
import demo.curURL.service.CurURLService;
import demo.entity.URLEntity;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 短域名解析请求接收器
 */
@RestController
@RequestMapping("/api/url/cur/v1")
public class CurURLController {
    private final CurURLService curURLService;

    @Autowired
    public CurURLController(CurURLService curURLService) {
        this.curURLService = curURLService;
    }

    @ApiOperation("将短域名转换为原 URL")
    @PostMapping("/")
    public Result<String> findURL(@RequestBody CurURLRequest curURLRequest) {
        if (!URLHelper.validURL(curURLRequest.getCurURL())) {
            return ResultUtil.success("入参为非法 URL！", null);
        }

        if (!URLHelper.validTinyURLLength(curURLRequest.getCurURL(), 8, true)) {
            return ResultUtil.success("入参为非法 URL！", null);
        }

        URLEntity urlEntity = this.curURLService.find(curURLRequest.getCurURL());

        if (Objects.isNull(urlEntity)) {
            return ResultUtil.success("");
        } else {
            return ResultUtil.success(urlEntity.getOriURL());
        }
    }
}
