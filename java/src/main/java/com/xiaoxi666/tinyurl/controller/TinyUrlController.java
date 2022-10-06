package com.xiaoxi666.tinyurl.controller;

import com.google.common.annotations.VisibleForTesting;
import com.xiaoxi666.tinyurl.config.TinyUrlConfig;
import com.xiaoxi666.tinyurl.domain.Response;
import com.xiaoxi666.tinyurl.domain.StoreParam;
import com.xiaoxi666.tinyurl.service.generator.Generator;
import com.xiaoxi666.tinyurl.service.store.HotCache;
import com.xiaoxi666.tinyurl.service.store.PermanentStore;
import com.xiaoxi666.tinyurl.service.store.TemporaryStore;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import static com.xiaoxi666.tinyurl.domain.Response.CodeMsg.ID_EXHAUSTED;
import static com.xiaoxi666.tinyurl.domain.Response.CodeMsg.INNER_ERROR;
import static com.xiaoxi666.tinyurl.domain.Response.CodeMsg.INVALID_PARAM;
import static com.xiaoxi666.tinyurl.domain.Response.CodeMsg.OP_NOT_ALLOWED;
import static com.xiaoxi666.tinyurl.domain.Response.CodeMsg.UN_FOUND;


/**
 * @Author: xiaoxi666
 * @Date: 2022/4/10
 * @Version: 1.0
 * @Description: 短域名存储和读取接口
 */
@RestController
public class TinyUrlController {

    // 短链接前缀，后面拼接号码即可
    @VisibleForTesting
    protected static final String TINY_URL_PREFIX = "https://xiaoxi666/tinyurl/";

    @Autowired
    private TinyUrlConfig config;

    @Autowired
    private Generator generator;

    @Resource
    private PermanentStore permanentStore;

    @Resource
    private TemporaryStore temporaryStore;

    @Resource
    private HotCache hotCache;

    /**
     * 短链接存储接口：接受长链接信息，返回短链接信息
     *
     * @param storeParam
     * @return
     */
    @PostMapping("/store")
    @ApiOperation("短链接存储接口：接受长链接信息，返回短链接信息。")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Response.class),
            @ApiResponse(code = 10000, message = "服务器内部错误", response = Response.class),
            @ApiResponse(code = 10001, message = "无效入参", response = Response.class),
            @ApiResponse(code = 10002, message = "发号器用尽", response = Response.class),
            @ApiResponse(code = 10003, message = "不允许的操作", response = Response.class)
    })
    public Response store(@RequestBody StoreParam storeParam) {
        if (storeParam == null) {
            return Response.error(INVALID_PARAM, "未指定参数");
        }
        String longUrl = storeParam.getLongUrl();
        if (longUrl == null || longUrl.isEmpty()) {
            return Response.error(INVALID_PARAM, "长连接不能为空");
        }
        if (longUrl.startsWith(TINY_URL_PREFIX)) {
            return Response.error(OP_NOT_ALLOWED, "禁止为本域名网址生成短链接");
        }
        String tinyPath = hotCache.get(longUrl);
        if (tinyPath != null && !tinyPath.isEmpty()) {
            return Response.success(TINY_URL_PREFIX + tinyPath);
        }
        String generateCodeAndId = generator.generate();
        if (generateCodeAndId.isEmpty()) {
            return Response.error(ID_EXHAUSTED);
        }
        tinyPath = config.getMachineCode() + generateCodeAndId;
        if (storeParam.getLevel() == 0) {
            permanentStore.put(tinyPath, longUrl);
        } else if (storeParam.getLevel() == 1) {
            temporaryStore.put(tinyPath, longUrl);
        }
        hotCache.put(longUrl, tinyPath);
        return Response.success(TINY_URL_PREFIX + tinyPath);
    }

    /**
     * - 短链接读取接口：接受短链接信息，返回长链接信息。
     *
     * @param tinyurl
     * @return
     */
    @GetMapping("/fetch")
    @ApiOperation("短链接读取接口：接受短链接信息，返回长链接信息。")
    @ApiImplicitParam(value = "短链接", name = "tinyurl", required = true, example = "https://xiaoxi666/tinyurl/ABC")
    @ApiResponses({
            @ApiResponse(code = 200, message = "成功", response = Response.class),
            @ApiResponse(code = 10000, message = "服务器内部错误", response = Response.class),
            @ApiResponse(code = 10001, message = "无效入参", response = Response.class),
            @ApiResponse(code = 10004, message = "未找到", response = Response.class)
    })
    public Response fetch(@RequestParam String tinyurl) {
        if (tinyurl == null || tinyurl.isEmpty()) {
            return Response.error(INVALID_PARAM, "短连接不能为空");
        }
        if (!tinyurl.startsWith(TINY_URL_PREFIX)) {
            return Response.error(INVALID_PARAM, "非本平台的短链接");
        }
        String tinyPath = tinyurl.substring(TINY_URL_PREFIX.length());
        System.out.println(tinyPath);
        String longUrl = temporaryStore.get(tinyPath);
        if (longUrl == null || longUrl.isEmpty()) {
            longUrl = permanentStore.get(tinyPath);
        }
        if (longUrl == null || longUrl.isEmpty()) {
            return Response.error(UN_FOUND, "可能的原因：未生成/已过期/因内存限制被清理");
        }
        return Response.success(longUrl);
    }

    @ExceptionHandler(Exception.class)
    public Response handleException(Exception e) {
        return Response.error(INNER_ERROR, e.getMessage());
    }
}
