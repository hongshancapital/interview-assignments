package com.scdt.service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "短路径服务")
public interface ShortPathService {
    /**
     * 短域名存储接口：接受长域名信息，返回短域名信息
     * @param longPath 长路径,使用时需要加密,
     *                 例如aaa/bbb/ccc?abc=123
     *                 使用base64加密后为
     *                 YWFhL2JiYi9jY2MlM0ZhYmMlM0QxMjM=
     * @return 短路径
     */
    @ApiOperation(value = "接受长域名信息，返回短域名信息", notes = "有保存作用, 长路径需要加密（base64）后传入")
    String getShortPath(String longPath);

    /**
     * 短域名读取接口：接受短域名信息，返回长域名信息
     * @param shortPath 短路径
     * @return 长路径， 使用时需要解密
     */
    @ApiOperation(value = "接受短域名信息，返回长域名信息", notes = "如果没有对应长路径就返回空, 长路径需要解密（base64）后使用")
    String getLongPath(String shortPath);
}


