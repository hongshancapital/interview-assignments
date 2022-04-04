package com.zhucan.domain.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhucan.domain.application.command.cmd.DomainMetathesisCommand;
import com.zhucan.domain.application.query.dto.ShortDomainDTO;
import com.zhucan.domain.infrastructure.test.base.BaseTest;
import com.zhucan.domain.infrastructure.test.matcher.StringLengthMatcher;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import javax.annotation.Resource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 16:25
 */
public class DomainControllerTests extends BaseTest {

  @Resource
  private MockMvc mockMvc;

  @Resource
  private ObjectMapper objectMapper;

  @Test
  public void whenMetathesisShortDomain_thenCheckResult() throws Exception {
    String longDomain = "http://localhost:8080/news/detail?title=war";
    DomainMetathesisCommand command = new DomainMetathesisCommand();
    command.setDomain(longDomain);

    // 测试请求短域名, 并获取结果
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/domain/short")
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .content(objectMapper.writeValueAsString(command)))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().is2xxSuccessful())
        .andReturn();

    // 处理短域名响应结果
    MockHttpServletResponse response = mvcResult.getResponse();
    ShortDomainDTO domainDTO = objectMapper.readValue(response.getContentAsString(), ShortDomainDTO.class);

    // 断言 短域名是否是六位长度
    assertThat(domainDTO.getShortDomain(), StringLengthMatcher.length(6));

    // 使用短域名置换源域名, 并比是否和源域名一致
    mockMvc.perform(MockMvcRequestBuilders.get("/domain/long")
        .param("shortDomain", domainDTO.getShortDomain()))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().is2xxSuccessful())
        .andExpect(jsonPath("$.domain").value(longDomain));

    // 使用短域名获取重定向的原始长域名, 并且比对结果
    mockMvc.perform(MockMvcRequestBuilders.get("http://" + domainDTO.getFullShortDomain()))
        .andDo(MockMvcResultHandlers.print())
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl(longDomain));

  }
}
