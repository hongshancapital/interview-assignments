package com.cn.scdt.controller;


import cn.hutool.crypto.SecureUtil;
import com.cn.scdt.entity.ResponseDto;
import com.cn.scdt.utils.RedisUtils;
import com.cn.scdt.utils.ShortUuidUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("")
@Api(value = "域名映射", tags = {"域名映射"})
@Slf4j
public class DomainMappingController {

    @Autowired
    private RedisUtils redisUtils;


    private Map<String,String> resultMap = new HashMap<>();
    private Long incrNum = 0L;

    @GetMapping("/getShortUrl")
    @ResponseBody
    @ApiOperation(value = "获取短地址", notes = "")
    public ResponseDto<String> getShortUrl(String longUrl) {
        String urlkey = SecureUtil.md5(longUrl);
        String shortUrlKey = resultMap.get(urlkey);
        if(StringUtils.isEmpty(shortUrlKey)){

            shortUrlKey = ShortUuidUtils.generateShortUuid();
            resultMap.put(urlkey, shortUrlKey);
            resultMap.put(shortUrlKey,longUrl);

            if(incrNum == 0){
                log.info("初始化内存" + Runtime.getRuntime().freeMemory() / (1024*1024) + "MB");
            }

            incrNum++;
            log.info("运行" + incrNum
                    + "次后，可用内存剩余" + Runtime.getRuntime().freeMemory() / (1024*1024) + "MB");
       /*     if(incrNum % 10000 == 0){
                resultMap = new HashMap<>();
                System.gc();
            }*/
        }
        return new ResponseDto(200,"返回成功",shortUrlKey);
    }


    @GetMapping("/getLongUrl")
    @ResponseBody
    @ApiOperation(value = "获取长地址", notes = "")
    public ResponseDto<String> getLongUrl(String shortUrlKey) {
        return new ResponseDto(200,"返回成功",resultMap.get(shortUrlKey));
    }


}
