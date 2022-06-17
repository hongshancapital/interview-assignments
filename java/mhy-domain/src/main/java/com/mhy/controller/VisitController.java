package com.mhy.controller;

import com.mhy.constant.DomainConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.mhy.db.LocalDatabase.DOMAIN_WAREHOUSE;

@Api(tags = {"短链接访问"})
@RestController
public class VisitController {

    @GetMapping("/visit/{shortStr}")
    @ApiOperation(value = "通过短链接访问长链接", notes = "该方法需要重定向")
    @ApiImplicitParam(name = "shortUrl", value = "短链接字符串", required = true)
    public void getLongUrl(@PathVariable String shortStr, HttpServletResponse response) throws IOException {
        //短链接拼接
        String shortUrl = "http://" + DomainConstant.DOMAIN_NAME + "/visit/" + shortStr;
        //获取长链接
        String longUrl = DOMAIN_WAREHOUSE.get(shortUrl);
        if (StringUtils.isNotBlank(longUrl)) {
            response.sendRedirect(longUrl);
        }
    }
}
