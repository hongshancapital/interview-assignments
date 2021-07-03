package com.task.controller;

import com.task.utils.inter.MyMap;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import javax.servlet.http.HttpServletResponse;

/**
 * 收到短链接请求
 * 从map中找到短码对应的长链接取出并 重定向 到长链接
 */
@RestController
@EnableSwagger2
public class LongAddressController {

    @Autowired
    private MyMap<String,String> myHashMap;


    /**
     * 根据短链接,找到对应的长链接并请求重定向
     * @param shortLink 短链接
     * @return
     */
    @ApiOperation("接收到短链接后,返回长链接并请求重定向")
    @GetMapping("/{shortLink}")
    public void getLink(@ApiParam("短链接") @PathVariable(required = false) String shortLink,
                          HttpServletResponse response) {
        try{
            //根据传入的短链接,从map中找到对应的长链接
            String longLink = myHashMap.get(shortLink);
            response.sendRedirect(longLink);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
