package com.liukun.shortdomain.controller;

import com.liukun.shortdomain.model.ResponseResult;
import com.liukun.shortdomain.service.DomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * <p>
 * <b>Class name</b>:
 * </p>
 * <p>
 * <b>Class description</b>: Class description goes here.
 * </p>
 * <p>
 * <b>Author</b>: kunliu
 * </p>
 * <b>Change History</b>:<br/>
 * <p>
 *
 * <pre>
 * Date          Author       Revision     Comments
 * ----------    ----------   --------     ------------------
 * 2021/10/6       kunliu        1.0          Initial Creation
 *
 * </pre>
 *
 * @author kunliu
 * @date 2021/10/6
 * </p>
 */
@RestController
@RequestMapping("api")
@Slf4j
@Api(value = "长短域名转换API", tags = "长短域名转换接口")
@AllArgsConstructor
public class ShortDomainController {

    private DomainService domainService;

    @ApiOperation(value = "长域名转换短域名", notes = "长域名转换短域名")
    @PostMapping("/createShortUrl")
    public ResponseResult createShortUrl(@RequestBody Map<String, String> params) {
        try {
            String url = params.get("url");
            String shortUrl = domainService.createShortUrl(url);
            return ResponseResult.success(shortUrl);
        } catch (Exception e) {
            log.error("", e);
        }
        return ResponseResult.failure("长域名转换短域名失败");
    }

    @ApiOperation(value = "", notes = "短域名转换长域名")
    @GetMapping("/getLongUrl")
    public ResponseResult getLongUrl(@RequestParam("url") String url) {
        try {
            return ResponseResult.success(domainService.getLongUrl(url));
        } catch (Exception e) {
            log.error("", e);
        }
        return ResponseResult.failure("短域名转换长域名失败");
    }
}
