package com.interview.assignment.filter.impl;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.enums.ResponseCode;
import com.interview.assignment.exception.BusinessException;
import com.interview.assignment.filter.ShortCodeQueryResponseFilter;
import com.interview.assignment.request.ShortCodeQueryRequest;
import javafx.util.Pair;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Log4j2
@Order(10)
@Component
public class ShortCodeRecordExistCheckFilter implements ShortCodeQueryResponseFilter {
  @Override
  public boolean filter(Pair<ShortCodeQueryRequest, ShortCode> tuple) {
    ShortCode shortCode = tuple.getValue();
    if (shortCode == null) {
      log.info("cannot find short code: {}", tuple.getKey().getShortCode());
      throw new BusinessException(ResponseCode.RECORD_NOT_FOUNT);
    }

    return true;
  }
}
