package com.zhangzheng.homework.service;

import com.zhangzheng.homework.ApplicationTests;
import com.zhangzheng.homework.entity.UrlMap;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @author zhangzheng
 * @version 1.0
 * @description: TODO
 * @date 2021/10/10 下午12:57
 */
@Slf4j
public class AsyncServiceTest extends ApplicationTests {
    @Autowired
    private AsyncService asyncService;

    @Test
    public void invalidUrl(){
        String longUrl = "http://www.adfa.com/werwe/wefa";
        asyncService.saveUrlMap(longUrl,"");
        asyncService.saveUrlMap("","");
    }

}
