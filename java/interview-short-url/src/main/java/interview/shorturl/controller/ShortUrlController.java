package interview.shorturl.controller;

import interview.shorturl.dao.po.ShortUrlInfo;
import interview.shorturl.exception.BusException;
import interview.shorturl.response.ResponseCodeEnum;
import interview.shorturl.response.ResponseData;
import interview.shorturl.service.ShortUrlService;
import interview.shorturl.vo.ShortUrlInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * 短域名相关接口
 *
 * @author: ZOUFANQI
 **/
@Api(tags = {"短域名转换操作"})
@Log4j2
@RestController
@RequestMapping("/")
public class ShortUrlController {
    private final ShortUrlService shortUrlService;

    public ShortUrlController(ShortUrlService shortUrlService) {
        this.shortUrlService = shortUrlService;
    }

    @ApiOperation(value = "长域名转短域名")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "长域名地址", required = true),
            @ApiImplicitParam(name = "expireSecond", value = "失效时长（秒），为空则为长期")
    })
    @PostMapping("/convertUrl")
    public ResponseData convertUrl(String url, Integer expireSecond) {
        final boolean isHttp = StringUtils.isNotBlank(url) && (url = url.trim()).startsWith("http");
        if (!isHttp) {
            throw new BusException(ResponseCodeEnum.PARAM_ERROR, "【url】参数不合法");
        }

        // 第一次：快速筛选，内存不足直接断言
        this.checkMemoryEnough();

        final ShortUrlInfo resultInfo;
        synchronized (this) {
            // 第二次：并发情况下的二次检测，确保第一次并发时内存边界堆积造成的实际可用内存已经超限从而溢出
            this.checkMemoryEnough();

            // 长域名转短域名业务，每次添加数据会增加使用内存
            resultInfo = this.shortUrlService.convertUrl(url, expireSecond);
        }
        return new ResponseData(
                ResponseCodeEnum.SUCCESS,
                new HashMap<String, ShortUrlInfoVo>(2) {{
                    this.put("info", ShortUrlInfoVo.buildVo(resultInfo));
                }}
        );
    }

    /**
     * 内存溢出检测
     */
    private void checkMemoryEnough() {
        // 内存检测，防止溢出，设定可用内存 > 10M
        final long freeMemory = Runtime.getRuntime().freeMemory() / 1024 / 1024;
        final boolean isMemoryEnough = freeMemory > 10;
        if (isMemoryEnough) {
            log.info(String.format("可用内存剩余：%sM", freeMemory));
        } else {
            throw new BusException(ResponseCodeEnum.FAIL, "资源不足，暂停服务");
        }
    }

    @ApiOperation(value = "获取域名信息")
    @ApiImplicitParam(name = "shortUrl", value = "短域名地址", required = true)
    @GetMapping("/url/{shortUrl}")
    public ResponseData getRealUrl(@PathVariable("shortUrl") String shortUrl) {
        return new ResponseData(
                ResponseCodeEnum.SUCCESS,
                new HashMap<String, ShortUrlInfoVo>(2) {{
                    this.put("info", ShortUrlInfoVo.buildVo(shortUrlService.getInfoByShortUrl(shortUrl)));
                }}
        );
    }

}
