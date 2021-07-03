package com.task.controller;

import com.task.utils.ShortLinkUtils;
import com.task.utils.inter.MyMap;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@RestController
@EnableSwagger2
@RequestMapping("/api/v1/longlink")
public class ShortAddressController {


    @Autowired
    private MyMap<String,String> myHashMap;


    /**
     * 服务端接收长链接,返回短链接
     */
    @ApiOperation("生成短链接")
    @GetMapping("/transformation")
    public String linkConversion() {

        //长链接
        String longLink = "http://localhost:8080/api/v1/longlink/transformation";
        //短链接
        String shortLink = ShortLinkUtils.shortLink(longLink);

        System.err.println("长链接:"+longLink);
        System.err.println("短链接:"+shortLink);

        /**
         * 将长链接放入到map集合中保存,key:短链接 value:长链接
         * 判断长短链接的映射关系是否已存在
         * 存在    返回
         * 不存在  put
         */
        boolean contains = myHashMap.containsKey(shortLink);
        if (contains){
            System.out.println("短链接已存在");
            return "短链接地址:http://localhost:8080/"+shortLink ;

        }else {
            myHashMap.put(shortLink,longLink);
            System.out.println("长短链接映射已加入");
            return "短链接地址:http://localhost:8080/"+shortLink ;
        }
    }

}
