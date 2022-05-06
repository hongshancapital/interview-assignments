package com.lenfen.short_domain.api;


import com.lenfen.short_domain.api.entity.ApiResponse;
import com.lenfen.short_domain.bean.ShortDomain;
import com.lenfen.short_domain.exception.ShortDomainException;
import com.lenfen.short_domain.service.DomainService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;


/**
 * 转换控制器
 */
@Slf4j
@RestController
@RequestMapping("transform")
public class TransformController {
    @Autowired
    private DomainService domainService;

    /**
     * 将长域名编码为短域名
     *
     * @param fullUrl 长域名
     * @return 短域名信息
     */
    @ApiOperation(value = "将长域名编码为短域名", httpMethod = "POST", response = ApiResponse.class)
    @ApiImplicitParam(paramType = "body", name = "fullUrl", dataType = "String", required = true, value = "长域名")
    @PostMapping("encode")
    public ApiResponse encode(@RequestBody String fullUrl) {
        try {
            if (StringUtils.isEmpty(fullUrl)) {
                return ApiResponse.fail("域名为空，请检查。");
            }
            return ApiResponse.ok(domainService.encode(fullUrl));
        } catch (ShortDomainException e) {
            log.error(String.format("编码失败:%s", fullUrl), e);
            return ApiResponse.err(e.getMessage());
        } catch (Throwable t) {
            log.error(String.format("编码失败:%s", fullUrl), t);
            return ApiResponse.err("系统内部异常，请联系维护人员处理。");
        }
    }

    /**
     * 根据短域名寻找对应的长域名
     *
     * @param shortUrl 短域名
     * @return 短域名信息
     */
    @ApiOperation(value = "根据短域名寻找对应的长域名", httpMethod = "GET", response = ApiResponse.class)
    @ApiImplicitParam(paramType = "query", name = "shortUrl", dataType = "String", required = true, value = "短域名")
    @GetMapping("decode")
    public ApiResponse decode(@RequestParam String shortUrl) {
        try {
            if (StringUtils.isEmpty(shortUrl)) {
                return ApiResponse.fail("短域名为空，请检查。");
            }
            ShortDomain shortDomain = domainService.decode(shortUrl);
            if (shortDomain == null) {
                return ApiResponse.fail("不存在短域名对应的信息");
            }
            return ApiResponse.ok(shortDomain);
        } catch (ShortDomainException e) {
            log.error(String.format("解码失败:%s", shortUrl), e);
            return ApiResponse.err(e.getMessage());
        } catch (Throwable t) {
            log.error(String.format("解码失败:%s", shortUrl), t);
            return ApiResponse.err("系统内部异常，请联系维护人员处理。");
        }
    }

}
