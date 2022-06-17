package com.mhy.controller;

import com.mhy.constant.DomainConstant;
import com.mhy.utils.ShortUrlUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

import static com.mhy.db.LocalDatabase.DOMAIN_WAREHOUSE;

@Api(tags = {"域名相关接口"})
@Slf4j
@RestController
@RequestMapping("/domain")
public class DomainController {

    @PostMapping("/longUrl/storage")
    @ApiOperation(value = "存储长链接", notes = "建立长短链接映射，并返回与之映射的短链接")
    @ApiImplicitParam(name = "longUrl", value = "长链接", required = true)
    public ResponseEntity<String> storageLongUrl(@RequestParam String longUrl) throws NoSuchAlgorithmException {
        if (StringUtils.isBlank(longUrl)) {
            return ResponseEntity.badRequest().body("长链接不能为空或空字符串");
        }
        //获取根据长链接生成的6位字符串
        String shortStr = ShortUrlUtil.getShortStr(longUrl);
        //拼接短链接，其中加上/visit是因为需要通过/visit/{shortUrl}接口测试短链接是否可用
        String shortUrl = "http://" + DomainConstant.DOMAIN_NAME + "/visit/" + shortStr;
        log.info("长链接：{}，转换为短链接：{}", longUrl, shortUrl);
        //将短链接与长链接做个映射
        DOMAIN_WAREHOUSE.put(shortUrl, longUrl);
        return ResponseEntity.ok(shortUrl);
    }

    @GetMapping("/longUrl/get")
    @ApiOperation(value = "获取长链接", notes = "根据短链接获取长链接")
    @ApiImplicitParam(name = "shortUrl", value = "短链接", required = true)
    public ResponseEntity<String> getLongUrl(@RequestParam String shortUrl) {
        //获取长链接
        String longUrl = DOMAIN_WAREHOUSE.get(shortUrl);
        log.info("通过短链接：{}，获取长链接：{}", shortUrl, longUrl);
        return ResponseEntity.ok(longUrl);
    }

}
