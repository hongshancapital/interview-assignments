package net.iapploft.springboot.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.iapploft.springboot.entity.vo.Result;
import net.iapploft.springboot.service.IShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags="短连接")
@RestController()
@RequestMapping("/link")
public class ShortLinkController {
    @Autowired
    private IShortLinkService shortLinkService;

    @ApiOperation(value="短连接生成器", notes="短连接生成器")
    @GetMapping("/short")
    public Result<String> getShortLink(@RequestParam(name="orgLink") String orgLink) {
        Result<String> result = new Result<>();
        result.setResult(shortLinkService.getShortLinkByLink(orgLink));
        return result;
    }

    @ApiOperation(value="返回源连接", notes="返回源连接")
    @GetMapping("/org")
    public Result<String> getOrgLink(@RequestParam(name="shortLink") String shortLink) {
        Result<String> result = new Result<>();
        try {
            result.setResult(shortLinkService.getLinkByShortLink(shortLink));
        } catch (Exception e) {
            result.error500(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
}
