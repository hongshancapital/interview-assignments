package com.interview.assignment.service.impl;

import com.interview.assignment.domain.ShortCode;
import com.interview.assignment.enums.CodeType;
import com.interview.assignment.enums.DurationType;
import com.interview.assignment.exception.BusinessException;
import com.interview.assignment.filter.ShortCodeGenerateFilter;
import com.interview.assignment.filter.ShortCodeQueryRequestFilter;
import com.interview.assignment.filter.ShortCodeQueryResponseFilter;
import com.interview.assignment.generator.IdGenerator;
import com.interview.assignment.generator.ShortCodeHandler;
import com.interview.assignment.generator.impl.VersionZeroShortCodeHandler;
import com.interview.assignment.model.GeneratedId;
import com.interview.assignment.repository.ShortCodeRepository;
import com.interview.assignment.request.ShortCodeGenerateRequest;
import com.interview.assignment.request.ShortCodeQueryRequest;
import com.interview.assignment.response.ShortCodeGenerateResponse;
import com.interview.assignment.response.ShortCodeQueryResponse;
import com.interview.assignment.service.ShortCodeClickService;
import com.interview.assignment.util.ShortCodeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ShortCodeServiceTest {

  private final ShortCodeGenerateFilter shortCodeGenerateFilter = Mockito.mock(ShortCodeGenerateFilter.class);
  private final ShortCodeQueryRequestFilter shortCodeQueryRequestFilter = Mockito.mock(ShortCodeQueryRequestFilter.class);
  private final ShortCodeQueryResponseFilter shortCodeQueryResponseFilter = Mockito.mock(ShortCodeQueryResponseFilter.class);
  private final IdGenerator idGenerator = Mockito.mock(IdGenerator.class);
  private final ShortCodeHandler shortCodeHandler = new VersionZeroShortCodeHandler();
  private final ShortCodeRepository shortCodeRepository = Mockito.mock(ShortCodeRepository.class);
  private final ShortCodeClickService clickService = Mockito.mock(ShortCodeClickService.class);

  private ShortCodeServiceImpl shortCodeService = new ShortCodeServiceImpl();

  @BeforeEach
  public void before() {
    shortCodeService.setGenerateFilters(Collections.singletonList(shortCodeGenerateFilter));
    shortCodeService.setQueryRequestFilters(Collections.singletonList(shortCodeQueryRequestFilter));
    shortCodeService.setQueryResponseFilters(Collections.singletonList(shortCodeQueryResponseFilter));
    shortCodeService.setIdGenerator(idGenerator);
    shortCodeService.setShortCodeHandler(shortCodeHandler);
    shortCodeService.setShortCodeRepository(shortCodeRepository);
    shortCodeService.setClickService(clickService);
  }

  @Test
  public void testGenerateFilterNotPass() {
    ShortCodeGenerateRequest generateRequest = getGenerateRequest();
    when(shortCodeGenerateFilter.filter(any())).thenReturn(false);
    assertThrows(BusinessException.class, () -> shortCodeService.generate(generateRequest));
  }

  @Test
  public void testGenerateShortCode() {
    ShortCodeGenerateRequest generateRequest = getGenerateRequest();
    GeneratedId generatedId = new GeneratedId();
    generatedId.setId(100L);
    when(shortCodeGenerateFilter.filter(generateRequest)).thenReturn(true);
    when(idGenerator.generate(generateRequest)).thenReturn(generatedId);

    ShortCodeGenerateResponse result = shortCodeService.generate(generateRequest);
    assertEquals(result.getShortCode(), "ya3");
  }

  @Test
  public void testShortCodeDetailRequestFilterNotPass() {
    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode("ya3");
    when(shortCodeQueryRequestFilter.filter(request)).thenReturn(false);

    assertThrows(BusinessException.class, () -> shortCodeService.detail(request));
  }

  @Test
  public void testShortCodeDetailThrowException() {
    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode("ya3");
    when(shortCodeQueryRequestFilter.filter(request)).thenReturn(true);
    when(shortCodeRepository.findByCode(request.getShortCode())).thenReturn(null);
    when(shortCodeQueryResponseFilter.filter(any())).thenThrow(BusinessException.class);

    assertThrows(BusinessException.class, () -> shortCodeService.detail(request));
  }

  @Test
  public void testShortCodeDetailIsNotExist() {
    ShortCodeQueryRequest request = new ShortCodeQueryRequest();
    request.setShortCode("ya3");
    ShortCode shortCode = ShortCodeUtil.getShortCode();
    when(shortCodeQueryRequestFilter.filter(request)).thenReturn(true);
    when(shortCodeRepository.findByCode(request.getShortCode())).thenReturn(shortCode);
    when(shortCodeQueryResponseFilter.filter(any())).thenReturn(true);

    ShortCodeQueryResponse result = shortCodeService.detail(request);
    assertEquals(result.getCode(), request.getShortCode());
  }

  @Test
  public void testSaveIdIsNull() {
    ShortCode shortCode = Mockito.mock(ShortCode.class);
    when(shortCode.getId()).thenReturn(null);
    shortCodeService.save(shortCode);
    verify(shortCode, times(1)).setId(any());
    verify(shortCode, times(1)).setCreateTime(any());
    verify(shortCode, times(1)).setUpdateTime(any());
  }

  @Test
  public void testSaveIdIsLowerOrEqualToZero() {
    ShortCode shortCode = Mockito.mock(ShortCode.class);
    when(shortCode.getId()).thenReturn(-1L);
    shortCodeService.save(shortCode);
    verify(shortCode, times(1)).setId(any());
    verify(shortCode, times(1)).setCreateTime(any());
    verify(shortCode, times(1)).setUpdateTime(any());
  }

  @Test
  public void testSaveIdIsGreaterThanZero() {
    ShortCode shortCode = Mockito.mock(ShortCode.class);
    when(shortCode.getId()).thenReturn(2L);
    shortCodeService.save(shortCode);
    verify(shortCode, times(1)).setUpdateTime(any());
  }

  private ShortCodeGenerateRequest getGenerateRequest() {
    ShortCodeGenerateRequest generateRequest = new ShortCodeGenerateRequest();
    generateRequest.setAppId("test");
    generateRequest.setDurationType(DurationType.PERMANENT);
    generateRequest.setType(CodeType.URL_REDIRECT);
    generateRequest.setUrl("http://www.baidu.com");
    return generateRequest;
  }

}
