package org.xxx.controller;

import java.util.Random;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.xxx.cbfsc.document.constant.Constants;

import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Create by hetao on 2021/11/12 11:33
 * <p>
 * 短网址生成项目
 */
@Api("短网址的接口文档")
@RestController
public class UrlController {
	Logger log=LoggerFactory.getLogger(this.getClass()); 

    /**
     * 创建短链接
     *
     * @param longUrl 原地址
     * @param viewPwd 访问密码
     * @param request 请求
     * @return json
     */
    @ApiOperation("创建短链接")
    @GetMapping("/createShortUrl")
    @ResponseBody
    public String creatShortUrl(String longUrl, HttpServletRequest request) {
        JSONObject json1 = new JSONObject();
        String[] split = longUrl.split("\n|\r");
        StringBuffer msg = new StringBuffer();

        for (int i = 0; i < split.length; i++) {
            String shortUrlId = getStringRandom(8);
            String toUrl = "/";
            msg.append(toUrl + "/" + shortUrlId + "<br>");
            Constants.datas.put(shortUrlId, longUrl);
        }

        json1.put("shortUrl", msg);
        return json1.toJSONString();
    }

    /**
     * 获取长链接
     *
     * @param shortUrlId 短地址
     * @param request 请求
     * @return json
     */
    @ApiOperation("获取长链接")
    @GetMapping("/getLongUrl")
    @ResponseBody
    public String getLongUrl(String shortUrlId, HttpServletRequest request) {
    	String longUrl = Constants.datas.get(shortUrlId);
    	return longUrl;
    }

    /**
     * 生成随机数字和字母
     *
     * @param length 生成长度
     * @return shortUrlId
     */
    private String getStringRandom(int length) {
        String val = "";
        Random random = new Random();

        //参数length，表示生成几位随机数
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";
            //输出字母还是数字
            if ("char".equalsIgnoreCase(charOrNum)) {
                //输出是大写字母还是小写字母
                int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
                val += (char) (random.nextInt(26) + temp);
            } else if ("num".equalsIgnoreCase(charOrNum)) {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }

}
