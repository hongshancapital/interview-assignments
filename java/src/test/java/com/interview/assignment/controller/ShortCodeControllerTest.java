package com.interview.assignment.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.interview.assignment.AssignmentApplication;
import com.interview.assignment.enums.CodeType;
import com.interview.assignment.enums.DurationType;
import com.interview.assignment.request.APIRequest;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import com.interview.assignment.request.ShortCodeQueryRequest;
import com.interview.assignment.response.ShortCodeGenerateResponse;
import com.interview.assignment.response.ShortCodeQueryResponse;
import com.interview.assignment.service.ShortCodeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AssignmentApplication.class})
public class ShortCodeControllerTest {

  @Autowired
  private WebApplicationContext context;

  @MockBean
  private ShortCodeService shortCodeService;

  private MockMvc mockMvc;

  @Before
  public void before(){
    mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void testGenerate() throws Exception {
    ShortCodeGenerateRequest request = new ShortCodeGenerateRequest();
    request.setAppId("test");
    request.setUrl("http://www.baidu.com");
    request.setType(CodeType.URL_REDIRECT);
    request.setDurationType(DurationType.PERMANENT);

    ShortCodeGenerateResponse response = new ShortCodeGenerateResponse();
    response.setShortCode("ya3");

    when(shortCodeService.generate(any())).thenReturn(response);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post("/short/code/generate")
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(JSON.toJSONString(APIRequest.instance(request)));

    MvcResult result = mockMvc.perform(builder)
      .andDo(MockMvcResultHandlers.print())
      .andReturn();

    JSONObject obj = JSON.parseObject(result.getResponse().getContentAsString());
    assertEquals("ya3", obj.getJSONObject("data").getString("shortCode"));
  }

  @Test
  public void testDetail() throws Exception {
    ShortCodeQueryResponse response = new ShortCodeQueryResponse();
    response.setCode("ya3");
    when(shortCodeService.detail(any())).thenReturn(response);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/short/code/detail")
      .param("shortCode", "ya3");

    MvcResult result = mockMvc.perform(builder)
      .andDo(MockMvcResultHandlers.print())
      .andReturn();

    JSONObject obj = JSON.parseObject(result.getResponse().getContentAsString());
    assertEquals("ya3", obj.getJSONObject("data").getString("code"));
  }

  @Test
  public void testRedirect() throws Exception {
    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode("ya3");

    ShortCodeQueryResponse response = new ShortCodeQueryResponse();
    response.setCode("ya3");
    response.setUrl("http://www.baidu.com");

    when(shortCodeService.detail(any())).thenReturn(response);

    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get("/ya3")
      .contentType(MediaType.APPLICATION_JSON_VALUE)
      .content(JSON.toJSONString(APIRequest.instance(request)));

    MvcResult result = mockMvc.perform(builder)
      .andDo(MockMvcResultHandlers.print())
      .andReturn();

    MockHttpServletResponse servletResponse = result.getResponse();
    assertEquals(302, servletResponse.getStatus());
    assertEquals("http://www.baidu.com", servletResponse.getHeader(HttpHeaders.LOCATION));
  }

}
