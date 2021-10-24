package com.interview.assignment.filter.impl;

import com.interview.assignment.filter.ShortCodeQueryRequestFilter;
import com.interview.assignment.generator.ShortCodeHandler;
import com.interview.assignment.request.ShortCodeQueryRequest;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Log4j2
@Order(10)
@Component
public class ShortCodeChecksumRequestFilter implements ShortCodeQueryRequestFilter {

  @Setter
  @Autowired
  private ShortCodeHandler shortCodeHandler;

  @Override
  public boolean filter(ShortCodeQueryRequest request) {
    boolean pass = shortCodeHandler.check(request.getShortCode());
    if (!pass) {
      log.info("check short code rejected, shortCode: {}", request.getShortCode());
    }

    return pass;
  }
}
