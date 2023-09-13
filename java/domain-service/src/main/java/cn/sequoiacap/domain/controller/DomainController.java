package cn.sequoiacap.domain.controller;

import cn.sequoiacap.domain.entity.ResponseUtils;
import cn.sequoiacap.domain.entity.ResponseVO;
import cn.sequoiacap.domain.service.DomainService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 域名转换服务API
 *
 * @author liuhao
 * @date 2021/12/10
 */
@Api(tags={"域名转换服务API"})
@RestController
@RequestMapping("/domain")
public class DomainController {

    @Autowired
    private DomainService domainService;

    /**
     * 根据一个长连接地址获取对应的短连接地址
     * @param longLink
     * @return
     */
    @ApiOperation(value="根据一个长连接地址获取对应的短连接地址",
            notes="根据一个长连接地址获取对应的短连接地址")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseVO.class) })
    @PostMapping
    public ResponseVO transformToShortLink(HttpServletRequest request, @RequestBody @ApiParam(value = "长连接地址", required = true) String longLink){
        String shortLink = domainService.transformToShortLink(request, longLink);
        return ResponseUtils.success(shortLink);
    }

    /**
     * 根据一个短连接地址返回对应的长连接地址
     * @param shortLink
     * @return
     */
    @ApiOperation(value="根据一个短连接地址返回对应的长连接地址",
            notes="根据一个短连接地址返回对应的长连接地址")
    @ApiResponses({ @ApiResponse(code = 200, message = "OK", response = ResponseVO.class) })
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "短连接地址", dataTypeClass = String.class, paramType = "query", required = true) })
    @GetMapping
    public ResponseVO getLongLinkByShortLink(@RequestParam("url") String shortLink){
        String longLink = domainService.getLongLinkByShortLink(shortLink);
        return ResponseUtils.success(longLink);
    }
}
