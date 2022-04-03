package com.zhucan.domain.application.query.dto;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 18:53
 */
public class LongDomainDTOTests {

  @Test
  public void domainDTOTest() {
    String domain = "http://localhost:8080";
    assertThat(new LongDomainDTO(domain).getDomain(), equalTo(domain));
  }

  @Test
  public void domainTest() {
    String domain = "http://localhost:8080";
    LongDomainDTO dto = new LongDomainDTO(domain);

    assertThat(dto.hashCode(), notNullValue());
    assertThat(dto.toString(), notNullValue());
    assertThat("", dto.canEqual(new LongDomainDTO(domain)));

    assertThat(dto, equalTo(new LongDomainDTO(domain)));

  }
}
