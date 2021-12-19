package com.my.linkapi.controller;

import com.my.linkapi.dto.R;
import lombok.AllArgsConstructor;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.my.linkapi.dto.LinkShortRequestDto;
import org.springframework.web.bind.annotation.*;
import com.my.linkapi.service.LinkLengthConversionService;

@RestController
@AllArgsConstructor
@RequestMapping("/")
@Api(value = "test", tags = "控制器")
public class LinkApiController {
    private LinkLengthConversionService linkLengthConversionService;

    /**
     * 转换长链接至短连接
     * @param LinkShortRequestDto link 长链接参数
     * @return R
     */
    @PostMapping("/toShort")
    @ApiOperation(value = "toShortApi", notes = "toShortApi")
    public R toShortApi(@RequestBody LinkShortRequestDto linkShortRequestDto){
        String key = linkLengthConversionService.toShort(linkShortRequestDto);
        return R.ok(key);
    }

    /**
     * 根据端链接获取长链接
     * @param LinkShortRequestDto link 短链接参数
     * @return R
     */
    @PostMapping("/get/link")
    @ApiOperation(value = "getLinkApi", notes = "getLinkApi")
    public R getLink(@RequestBody LinkShortRequestDto linkShortRequestDto){
        String key = linkLengthConversionService.toLong(linkShortRequestDto.getLink());
        return R.ok(key);
    }
}
