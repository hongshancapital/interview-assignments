package com.layne.interview.controller;

import com.layne.interview.controller.vo.CommonResult;
import com.layne.interview.model.ErrorCodeEnum;
import com.layne.interview.model.ManageException;
import com.layne.interview.service.UrlManageService;
import io.swagger.annotations.Api;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Url管理向外提供的controller服务
 *
 * @author layne
 * @version UrlManage.java, v 0.1 2022/1/16 21:40 下午
 */
@RestController
@Api(value = "urlManageController")
@RequestMapping("/api/v1/url")
public class UrlManageController {

    /**
     * logger
     */
    private Logger LOGGER = LoggerFactory.getLogger(UrlManageController.class);

    /**
     * url管理服务
     */
    @Autowired
    private UrlManageService urlManageService;

    /**
     * 向外提供的存储url接口，返回短url
     *
     * @param longUrl 长url
     * @return 短url
     */
    @GetMapping("store/{longUrl}")
    public CommonResult<String> store(@PathVariable String longUrl) {

        // 这里还可以给每个请求加一个唯一的sequenceNo以区分请求
        LOGGER.info("收到长域名存储请求, longUrl = {}", longUrl);

        // 构建结果
        CommonResult<String> result = new CommonResult<>();

        try {
            // 检查入参
            checkRequest(longUrl);

            // 调用service进行业务逻辑
            result.setObj(urlManageService.storeUrl(longUrl));

            result.setSuccess(true);

        } catch (Exception e) {

            dealWithException(result, e);

        }

        return result;
    }

    /**
     * 向外提供的获取url接口，返回长url
     *
     * @param shortUrl 短url
     * @return 长url
     */
    @GetMapping("get/{shortUrl}")
    public CommonResult<String> get(@PathVariable String shortUrl) {

        // 这里还可以给每个请求加一个唯一的sequenceNo以在日志里区分请求
        LOGGER.info("收到长域名查询请求,输入 shortUrl = {}", shortUrl);

        // 构建结果
        CommonResult<String> result = new CommonResult<>();

        try {
            // 检查入参
            checkRequest(shortUrl);

            // 调用service进行业务逻辑
            result.setObj(urlManageService.getUrl(shortUrl));

            result.setSuccess(true);

        } catch (Exception e) {

            dealWithException(result, e);

        }

        return result;
    }

    /**
     * 处理异常
     *
     * @param result 結果
     */
    private void dealWithException(CommonResult<String> result, Exception e) {
        LOGGER.error("请求处理中出现异常");

        // 设置为失败
        result.setSuccess(false);
        result.setException(e);

        if (e instanceof ManageException) {

            result.setErrorCode(((ManageException) e).getErrorCode().getCode());

        } else {

            result.setErrorCode(ErrorCodeEnum.INTERNAL_ERROR.getCode());

        }
    }

    /**
     * 检查入参
     *
     * @param urlString url
     */
    private void checkRequest(String urlString) {
        // 对请求字段进行校验
        if (StringUtils.isBlank(urlString)) {

            LOGGER.info("请求参数有误, longUrl = {}", urlString);

            throw new ManageException(ErrorCodeEnum.BAD_REQUEST, "请求参数有误");

        }
    }

}
