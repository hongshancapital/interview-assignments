/**
 * this is a test project
 */

package com.example.interviewassgnments.controllers;

import com.example.interviewassgnments.annotation.ResponseResult;
import com.example.interviewassgnments.entitys.BaseResult;
import com.example.interviewassgnments.services.DomainNameService;
import com.example.interviewassgnments.utils.BusinessException;
import com.example.interviewassgnments.utils.ResultEnum;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 长域名转写及域名查询服务器
 *
 * @Auther: maple
 * @Date: 2022/1/19 9:45
 * @Description: com.example.interviewassgnments.controllers
 * @version: 1.0
 */
@RestController
@RequestMapping("/domain")
@Api(tags = {"域名转写服务"})
@Slf4j
@ResponseResult
public class DomainNameController {
    @Autowired
    DomainNameService service;


    @RequestMapping(value = "/toShortLink", method = RequestMethod.POST)
    @RequestBody
    public Map<String, String> toShortLink(@RequestParam(name = "fullURL") String longDomainLink) {

        String shortLink = "";
        if (StringUtils.isNotEmpty(longDomainLink)) {
            String pattern = "^(?:([A-Za-z]+):)?(\\/{0,3})([0-9.\\-A-Za-z]+)(?::(\\d+))?(?:\\/([^?#]*))?(?:\\?([^#]*))?(?:#(.*))?$";

            Pattern r = Pattern.compile(pattern);
            Matcher m = r.matcher(longDomainLink);
            if (m.matches()) {
                shortLink = service.getShortDomainLink(longDomainLink);
            }else{
                throw new BusinessException(ResultEnum.EXCEPTION_ILLEGAL_CHARACTER);
            }
        }else{
            throw new BusinessException(ResultEnum.PARAM_IS_BLANK);
        }
        Map<String, String> map = new HashMap<>();
        map.put("shortLink", shortLink);
        return map;
    }

    @RequestMapping(value = "/getFullLink", method = RequestMethod.GET)
    public Map getFullLink(@RequestParam(name = "shortLink") String shortLink) {
        if(StringUtils.isEmpty(shortLink)){
            throw new BusinessException(ResultEnum.PARAM_IS_BLANK);
        }
        String fullLink = service.getFullLink(shortLink);
        Map<String, String> map = new HashMap<>();
        map.put("fullLink", fullLink);
        return map;
    }
}
