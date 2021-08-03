package com.sequoia.shortdomain.api.test;

import com.sequoia.shortdomain.api.ShortDomainAPI;
import com.sequoia.shortdomain.common.FastJsonUtil;
import com.sequoia.shortdomain.common.ResponseResult;
import com.sequoia.shortdomain.common.ShortDomainConst;
import com.sequoia.shortdomain.service.IShortDomainService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;


@WebMvcTest(ShortDomainAPI.class)

public class ShortDomainAPITest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private IShortDomainService service;
    @Test
    public void testExample() throws Exception {
        //groupManager访问路径
        //param传入参数
        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/shortdomain/service/api/getShortDomain").param("longDomain","https://github.com/softprog/interview-assignments/tree/master/java")
                ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        ResponseResult responseResult = FastJsonUtil.getJsonToBean(content, ResponseResult.class);
        if(responseResult!=null && responseResult.getCode().equals(ShortDomainConst.GET_SUCCESS)){
            if(StringUtils.isNotBlank(responseResult.getData())){
               Assert.isTrue(true,"ok");
            }
        }

        Assert.isTrue(false,"failure");
    }
}
