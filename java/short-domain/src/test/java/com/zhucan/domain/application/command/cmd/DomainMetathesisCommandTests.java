package com.zhucan.domain.application.command.cmd;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author zhuCan
 * @description
 * @since 2022/4/3 19:15
 */
public class DomainMetathesisCommandTests {

  @Test
  public void metathesisCommandTest() {
    String domain = "http://localhost:8080";
    DomainMetathesisCommand command = new DomainMetathesisCommand();
    command.setDomain(domain);
    assertThat(command.getDomain(), equalTo(domain));
  }

  @Test
  public void commandTest() {
    String domain = "http://localhost:8080";
    DomainMetathesisCommand command = new DomainMetathesisCommand();
    command.setDomain(domain);

    assertThat(command.hashCode(), notNullValue());
    assertThat(command.toString(), notNullValue());


    DomainMetathesisCommand operand = new DomainMetathesisCommand();
    operand.setDomain(domain);

    assertThat("", command.canEqual(operand));
    assertThat(command, equalTo(operand));

  }
}
