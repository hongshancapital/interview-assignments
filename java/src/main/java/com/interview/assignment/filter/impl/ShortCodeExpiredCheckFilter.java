package com.interview.assignment.filter.impl;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.enums.DurationType;
import com.interview.assignment.enums.ResponseCode;
import com.interview.assignment.exception.BusinessException;
import com.interview.assignment.filter.ShortCodeQueryResponseFilter;
import com.interview.assignment.request.ShortCodeQueryRequest;
import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Date;

@Log4j2
@Order(20)
@Component
public class ShortCodeExpiredCheckFilter implements ShortCodeQueryResponseFilter {
  @Override
  public boolean filter(Pair<ShortCodeQueryRequest, ShortCode> request) {
    ShortCode shortCode = request.getValue();
    if (DurationType.LIMITED_TIME.getType().equals(shortCode.getDurationType())) {
      Date now = new Date();
      boolean pass = shortCode.getStartTime().before(now) && shortCode.getEndTime().after(now);
      if (!pass) {
        log.error("current short code is expired, code: {}", shortCode.getCode());
        throw new BusinessException(ResponseCode.SHORT_CODE_EXPIRED);
      }
    }

    return true;
  }
}
