package com.domainserver.ctrl;

import com.domainserver.service.IShortDomainService;
import com.domainserver.util.DomainValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 短域名服务控制器
 *
 * @author 905393944@qq.com
 * @Date 2021/9/21
 */
@RestController
@RequestMapping(value = "/api")
@Api(tags = "短域名服务", description = "接收长域名返回短域名，接收短域名返回长域名")
public class ShortDomainCtrl {
    private static final Logger LOGGER = LoggerFactory.getLogger(ShortDomainCtrl.class);

    @Autowired
    private IShortDomainService shortDomainService;

    /**
     * 功能描述: 通过长域名获取短域名
     *
     * @param longDomain 长域名
     * @return java.lang.Object
     * @author 905393944@qq.com
     * @Date 2021/9/21
     */
    @ApiOperation(value = "通过长域名获取短域名")
    @ApiImplicitParam(name = "longDomain", value = "长域名", dataType = "String", required = true)
    @ApiResponse(code = 200, message = "获取成功")
    @PostMapping(value = "getShortDomain")
    public Object getShortDomain(@RequestBody(required = true) String longDomain) {
        return shortDomainService.getShortDomain(longDomain);
    }


    /**
     * 功能描述: 通过短域名获取长域名
     *
     * @param shortDomain
     * @return java.lang.Object
     * @throws
     * @author 905393944@qq.com
     * @Date 2021/9/21
     */
    @ApiOperation(value = "通过短域名获取长域名")
    @ApiImplicitParam(name = "shortDomain", value = "短域名,最大长度为8", dataType = "String", required = true)
    @ApiResponse(code = 200, message = "获取成功")
    @PostMapping(value = "getLongDomain")
    public Object getLongDomain(@RequestBody(required = true) String shortDomain) {
        return shortDomainService.getLongDomain(shortDomain);
    }
}
