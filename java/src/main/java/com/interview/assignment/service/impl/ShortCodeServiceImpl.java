package com.interview.assignment.service.impl;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.enums.CodeType;
import com.interview.assignment.enums.DurationType;
import com.interview.assignment.enums.ResponseCode;
import com.interview.assignment.exception.BusinessException;
import com.interview.assignment.factory.NamedThreadFactory;
import com.interview.assignment.filter.ShortCodeQueryRequestFilter;
import com.interview.assignment.filter.ShortCodeGenerateFilter;
import com.interview.assignment.filter.ShortCodeQueryResponseFilter;
import com.interview.assignment.generator.IdGenerator;
import com.interview.assignment.generator.ShortCodeHandler;
import com.interview.assignment.model.GeneratedId;
import com.interview.assignment.repository.ShortCodeRepository;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import com.interview.assignment.request.ShortCodeQueryRequest;
import com.interview.assignment.response.ShortCodeGenerateResponse;
import com.interview.assignment.response.ShortCodeQueryResponse;
import com.interview.assignment.service.ShortCodeClickService;
import com.interview.assignment.service.ShortCodeService;
import javafx.util.Pair;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Log4j2
@Service
public class ShortCodeServiceImpl implements ShortCodeService {
  private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 100,
    60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(5000),
    new NamedThreadFactory("generate-short-code"), new ThreadPoolExecutor.CallerRunsPolicy());

  @Setter
  @Autowired
  private List<ShortCodeGenerateFilter> generateFilters;
  @Setter
  @Autowired
  private List<ShortCodeQueryRequestFilter> queryRequestFilters;
  @Setter
  @Autowired
  private List<ShortCodeQueryResponseFilter> queryResponseFilters;
  @Setter
  @Autowired
  private IdGenerator idGenerator;
  @Setter
  @Autowired
  private ShortCodeHandler shortCodeHandler;
  @Setter
  @Autowired
  private ShortCodeRepository shortCodeRepository;
  @Setter
  @Autowired
  private ShortCodeClickService clickService;

  /**
   * 生成短码
   *
   * @param request 请求参数
   * @return 生成的短码数据
   */
  @Override
  public ShortCodeGenerateResponse generate(ShortCodeGenerateRequest request) {
    for (ShortCodeGenerateFilter filter : generateFilters) {
      boolean pass = filter.filter(request);
      if (!pass) {
        log.info("short code generate filter fail, appId: {}, url: {}, class: {}", request.getAppId(),
          request.getUrl(), filter.getClass().getSimpleName());
        throw new BusinessException(ResponseCode.ILLEGAL_PARAMS);
      }
    }

    log.info("receive generate short code request, appId: {}, url: {}, durationType: {}, startTime: {}, endTime: {}",
      request.getAppId(), request.getUrl(), request.getDurationType(), request.getStartTime(), request.getEndTime());
    GeneratedId generatedId = idGenerator.generate(request);
    String code = shortCodeHandler.generate(generatedId.getId());
    log.info("generated id: {}, code: {}", generatedId.getId(), code);
    ShortCode shortCode = createShortCode(request, code);
    executor.execute(() -> save(shortCode));  // 持久化数据，生产中可能是redis和数据库，因而使用异步方式
    ShortCodeGenerateResponse response = new ShortCodeGenerateResponse();
    response.setShortCode(code);
    return response;
  }

  @Override
  public ShortCodeQueryResponse detail(ShortCodeQueryRequest request) {
    for (ShortCodeQueryRequestFilter filter : queryRequestFilters) {
      boolean pass = filter.filter(request);
      if (!pass) {
        log.info("short code query filter fail, shortCode: {}", request.getShortCode());
        throw new BusinessException(ResponseCode.ILLEGAL_PARAMS);
      }
    }

    ShortCode shortCode = shortCodeRepository.findByCode(request.getShortCode());
    Pair<ShortCodeQueryRequest, ShortCode> tuple = new Pair<>(request, shortCode);
    for (ShortCodeQueryResponseFilter filter : queryResponseFilters) {
      filter.filter(tuple);
    }

    executor.execute(() -> clickService.record(shortCode));
    ShortCodeQueryResponse result = new ShortCodeQueryResponse();
    BeanUtils.copyProperties(shortCode, result, "type", "durationType");
    result.setType(CodeType.get(shortCode.getType()));
    result.setDurationType(DurationType.get(shortCode.getDurationType()));
    return result;
  }

  void save(ShortCode shortCode) {
    if (null == shortCode.getId() || shortCode.getId() <= 0) {
      shortCode.setId(null);
      shortCode.setCreateTime(new Date());
    }

    shortCode.setUpdateTime(new Date());
    shortCodeRepository.save(shortCode);
  }

  private ShortCode createShortCode(ShortCodeGenerateRequest request, String code) {
    ShortCode shortCode = new ShortCode();
    shortCode.setAppId(request.getAppId());
    shortCode.setCode(code);
    shortCode.setUrl(request.getUrl());
    shortCode.setType(request.getType().getType());
    shortCode.setDurationType(request.getDurationType().getType());
    shortCode.setStartTime(request.getStartTime());
    shortCode.setEndTime(request.getEndTime());
    shortCode.setCreateTime(new Date());
    shortCode.setUpdateTime(new Date());
    return shortCode;
  }

}
