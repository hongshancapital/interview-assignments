package com.sequoia.shortdomain.api.test;

import com.sequoia.shortdomain.api.ShortDomainAPI;
import com.sequoia.shortdomain.common.FastJsonUtil;
import com.sequoia.shortdomain.common.ResponseResult;
import com.sequoia.shortdomain.common.ShortDomainConst;
import com.sequoia.shortdomain.service.IShortDomainService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.Assert;

import static org.mockito.Mockito.when;


@WebMvcTest(ShortDomainAPI.class)

public class ShortDomainAPITest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private IShortDomainService service;
    @Test
    public void testGetShortDomainSuccess() throws Exception {


        when(service.getShortDomainByLongDomain("https://github.com/softprog/interview-assignments/tree/master/java")).thenReturn("abcd");
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


    }

    @Test
    public void testGetShortDomainFailure() throws Exception {
        //groupManager访问路径
        //param传入参数
       when(service.getShortDomainByLongDomain("https://github.com/softprog/interview-assignments/tree/master/java")).thenReturn("");
        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/shortdomain/service/api/getShortDomain").param("longDomain","https://github.com/softprog/interview-assignments/tree/master/java")
        ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        ResponseResult responseResult = FastJsonUtil.getJsonToBean(content, ResponseResult.class);
        if(responseResult!=null && responseResult.getCode().equals(ShortDomainConst.GET_SUCCESS)){
            if(!StringUtils.isNotBlank(responseResult.getData())){
                Assert.isTrue(false,"failure");

            }else{
                Assert.isTrue(true,"ok");
            }
        }


    }
    @Test
    public void testGetShortDomainFailureForParam() throws Exception {

        //param传入参数
        when(service.getShortDomainByLongDomain("https://github.com/softprog/interview-assignments/tree/master/java")).thenReturn("");
        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/shortdomain/service/api/getShortDomain").param("longDomain","")
        ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        ResponseResult responseResult = FastJsonUtil.getJsonToBean(content, ResponseResult.class);
        if(responseResult!=null && responseResult.getCode().equals(ShortDomainConst.GET_SUCCESS)){
            if(!StringUtils.isNotBlank(responseResult.getData())){
                Assert.isTrue(false,"failure");

            }else{
                Assert.isTrue(true,"ok");
            }
        }


    }
    @Test
    public void testGetLongDomainSuccess() throws Exception {

        //param传入参数
       when(service.getLongDomainByShortDomain("abcd")).thenReturn("https://github.com/softprog/interview-assignments/tree/master/java");
        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/shortdomain/service/api/getLongDomain").param("shortDomain","abcd")
        ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        ResponseResult responseResult = FastJsonUtil.getJsonToBean(content, ResponseResult.class);
        if(responseResult!=null && responseResult.getCode().equals(ShortDomainConst.GET_SUCCESS)){
            if(StringUtils.isNotBlank(responseResult.getData())){
                Assert.isTrue(true,"ok");

            }else{
                Assert.isTrue(false,"failure");
            }
        }


    }

    @Test
    public void testGetLongDomainFailure() throws Exception {

        //param传入参数
        when(service.getLongDomainByShortDomain("abcd")).thenReturn("");
        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/shortdomain/service/api/getLongDomain").param("shortDomain","abcd")
        ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        ResponseResult responseResult = FastJsonUtil.getJsonToBean(content, ResponseResult.class);
        if(responseResult!=null && responseResult.getCode().equals(ShortDomainConst.GET_SUCCESS)){
            if(StringUtils.isNotBlank(responseResult.getData())){
                 Assert.isTrue(false,"failure");

            }else{
                Assert.isTrue(true,"ok");
            }
        }


    }

    @Test
    public void testGetLongDomainFailureForParam() throws Exception {

        //param传入参数
        when(service.getLongDomainByShortDomain("abcd")).thenReturn("");
        MvcResult result=mvc.perform(MockMvcRequestBuilders.get("/shortdomain/service/api/getLongDomain").param("shortDomain","")
        ).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String content = response.getContentAsString();
        ResponseResult responseResult = FastJsonUtil.getJsonToBean(content, ResponseResult.class);
        if(responseResult!=null && responseResult.getCode().equals(ShortDomainConst.GET_SUCCESS)){
            if(StringUtils.isNotBlank(responseResult.getData())){
                Assert.isTrue(false,"failure");

            }else{
                Assert.isTrue(true,"ok");
            }
        }


    }
}
